/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.checks.movement.fly;

import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.misc.BoundingUtil;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

@Check
@Experimental
public class FlyA
extends AbstractCheck
implements Listener {
    private Material slime;
    private Map<UUID, Long> slimeTicks = new HashMap<UUID, Long>();
    private Map<UUID, Double> slimeHeight = new HashMap<UUID, Double>();
    private Map<UUID, Integer> jumpBoostLevel = new HashMap<UUID, Integer>();
    private Map<UUID, Long> jumpBoostTicks = new HashMap<UUID, Long>();
    private Map<UUID, Map<Long, Double>> ascensionTicks = new HashMap<UUID, Map<Long, Double>>();
    private Map<UUID, Location> ground = new HashMap<UUID, Location>();
    private Map<UUID, Vector> playerVelocity = new HashMap<UUID, Vector>();
    private Map<UUID, Long> velocityUpdate = new HashMap<UUID, Long>();
    private Map<UUID, Long> lastAttack = new HashMap<UUID, Long>();
    private long velocityDeacyTime = 2500L;
    private double maxVelocityBeforeDeacy = 0.03;
    private double baseHeight = 3.0;

    public FlyA(ArtemisPlugin artemis) {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)artemis);
        try {
            this.slime = Material.SLIME_BLOCK;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        new BukkitRunnable(){

            public void run() {
                for (Player op : Bukkit.getOnlinePlayers()) {
                    long lastUpdate;
                    Vector v = op.getVelocity();
                    UUID uuid = op.getUniqueId();
                    if (!FlyA.this.velocityUpdate.containsKey(op.getUniqueId()) || (lastUpdate = System.currentTimeMillis() - (Long)FlyA.this.velocityUpdate.get(op.getUniqueId())) <= FlyA.this.velocityDeacyTime || !(v.getX() < FlyA.this.maxVelocityBeforeDeacy && v.getY() < FlyA.this.maxVelocityBeforeDeacy && v.getZ() < FlyA.this.maxVelocityBeforeDeacy) && (!(v.getX() > -FlyA.this.maxVelocityBeforeDeacy) || !(v.getY() > -FlyA.this.maxVelocityBeforeDeacy) || !(v.getZ() > -FlyA.this.maxVelocityBeforeDeacy))) continue;
                    FlyA.this.playerVelocity.remove(uuid);
                }
            }
        }.runTaskTimer((Plugin)artemis, 0L, 1L);
    }

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!player.getAllowFlight()) {
            if (player.getVehicle() == null) {
                long jumpBoostTicksOffset;
                double groundDiff;
                if (player.isOnGround()) {
                    return;
                }
                long lastAttackOffset = Long.MAX_VALUE;
                if (this.lastAttack.containsKey(uuid)) {
                    lastAttackOffset = System.currentTimeMillis() - this.lastAttack.get(uuid);
                }
                if (BoundingUtil.boundingBoxOnGround(player)) {
                    if (lastAttackOffset > 1000L) {
                        this.ground.put(uuid, player.getLocation());
                    }
                } else if (!this.ground.containsKey(uuid)) {
                    this.ground.put(uuid, player.getLocation());
                }
                Location lastGround = this.ground.get(uuid);
                if (lastAttackOffset < 5000L) {
                    lastGround = lastGround.clone().add(0.0, 1.0, 0.0);
                }
                double maxHeight = this.baseHeight + this.calcSlimeOffset(player);
                if (this.playerVelocity.containsKey(uuid)) {
                    maxHeight += this.playerVelocity.get(uuid).getY() + 1.0;
                }
                if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        if (!effect.getType().equals((Object)PotionEffectType.JUMP)) continue;
                        int level = effect.getAmplifier() + 1;
                        maxHeight += Math.pow((double)level + 4.2, 2.0) / 16.0;
                        this.jumpBoostLevel.put(uuid, level);
                        this.jumpBoostTicks.put(uuid, System.currentTimeMillis());
                        break;
                    }
                } else if (this.jumpBoostTicks.containsKey(uuid) && (jumpBoostTicksOffset = System.currentTimeMillis() - this.jumpBoostTicks.get(uuid)) < 5000L) {
                    int level = this.jumpBoostLevel.get(uuid);
                    maxHeight += Math.pow((double)level + 4.2, 2.0) / 16.0;
                }
                if ((groundDiff = e.getTo().getY() - lastGround.getY()) > maxHeight + 0.005 && TimeUtil.hasExpired(data.combat.getLastExplosion(), 5L)) {
                    this.log(data, new String[0]);
                }
            } else {
                this.ground.put(uuid, player.getLocation());
            }
        } else {
            this.ground.put(uuid, player.getLocation());
        }
    }

    private double calcSlimeOffset(Player player) {
        if (this.slime != null) {
            UUID uuid = player.getUniqueId();
            if (!this.slimeTicks.containsKey(uuid)) {
                this.slimeTicks.put(uuid, 0L);
                this.slimeHeight.put(uuid, 0.0);
            } else {
                long timeOffset = System.currentTimeMillis() - this.slimeTicks.get(uuid);
                if (timeOffset < 15000L) {
                    return this.slimeHeight.get(uuid) + 2.0;
                }
            }
            Location playerLoc = player.getLocation();
            for (int yOffset = 0; yOffset <= 4; ++yOffset) {
                playerLoc.setY(playerLoc.getY() - 1.0);
                Block block = playerLoc.getBlock();
                if (block.getType() != this.slime) continue;
                double fallDistance = Math.max((double)(player.getFallDistance() + (float)yOffset), 4.0);
                if (fallDistance <= 128.0) {
                    double estBounceHeight = -0.0011 * Math.pow(fallDistance, 2.0) + 0.43529 * fallDistance + 1.8;
                    this.slimeTicks.put(uuid, System.currentTimeMillis());
                    this.slimeHeight.put(uuid, estBounceHeight);
                    return estBounceHeight + 2.0;
                }
                return 17.625;
            }
            return 0.0;
        }
        return 0.0;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && !(e.getDamager() instanceof Arrow)) {
            Player player = (Player)e.getDamager();
            this.ground.put(player.getUniqueId(), player.getLocation().add(0.0, 0.5, 0.0));
            this.lastAttack.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        this.ground.put(e.getPlayer().getUniqueId(), e.getTo());
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e) {
        this.playerVelocity.put(e.getPlayer().getUniqueId(), e.getVelocity());
        if (!this.velocityUpdate.containsKey(e.getPlayer().getUniqueId())) {
            this.velocityUpdate.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        } else {
            this.velocityUpdate.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

}

