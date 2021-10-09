/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.listeners;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.misc.CompatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class EventAdapterListener
implements Listener {
    private List<Player> invalidPlayers = new ArrayList<Player>();

    public EventAdapterListener(Artemis artemis) {
        Chat.sendConsoleMessage("&6Initialized EventAdapterListener");
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void move(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(e.getPlayer());
        data.getCheckManager().getAbstractChecks().forEach(check -> check.handle(data, e));
        World world = player.getWorld();
        Location to = e.getTo();
        if (e.isCancelled()) {
            data.movement.setLastMoveCancel(System.currentTimeMillis());
        }
        if (!player.isOnGround()) {
            data.movement.setFlyTicks(data.movement.getFlyTicks() + 1);
        } else {
            data.movement.setFlyTicks(0);
        }
        try {
            if (!CompatUtil.is17() && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
                data.movement.setLastSlime(System.currentTimeMillis());
            }
        }
        catch (Exception ex) {
            return;
        }
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)event.getWhoClicked());
        data.getCheckManager().getAbstractChecks().forEach(check -> check.handle(data, event));
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)((Player)event.getEntity())).combat.setLastDamage(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void damageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)event.getDamager());
            Player player = (Player)event.getEntity();
            try {
                data.combat.setLastOpponent(player);
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
            return;
        }
    }

    @EventHandler
    public void die(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerData playerData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        playerData.movement.setDeathTicks(20);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        data.user.setLongInTimePassed(System.currentTimeMillis());
    }

    @EventHandler
    public void onExplo(EntityDamageEvent e) {
        if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || e.getCause().equals((Object)EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || e.getCause().equals((Object)EntityDamageEvent.DamageCause.WITHER)) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player)e.getEntity();
                PlayerData playerData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
                playerData.combat.setLastExplosion(System.currentTimeMillis());
            }
        } else if (e.getCause().equals((Object)EntityDamageEvent.DamageCause.PROJECTILE) && e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            PlayerData playerData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
            playerData.combat.setLastBowDamage(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            try {
                if (this.invalidPlayers.contains((Object)player)) {
                    return;
                }
                PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
                if (event.getCause().equals((Object)PlayerTeleportEvent.TeleportCause.UNKNOWN)) {
                    data.movement.setTeleportTicks(0);
                    data.user.setTpUnknown(true);
                }
            }
            catch (Exception e) {
                this.invalidPlayers.add(player);
            }
        }
    }
}

