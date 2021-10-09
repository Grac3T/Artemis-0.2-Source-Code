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
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package cc.ghast.artemis.v2.listeners;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.PacketUtil;
import cc.ghast.artemis.v2.api.packet.event.PacketReceiveEvent;
import cc.ghast.artemis.v2.api.packet.event.PacketSendEvent;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.handshake.WrappedHandshakeClientProtocol;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInCustomPayload;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInEntityActionPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInKeepAlivePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.out.WrappedOutVelocityPacket;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.location.BlockUtil;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.location.Rotation;
import cc.ghast.artemis.v2.utils.location.Velocity;
import cc.ghast.artemis.v2.utils.maths.EvictingDeque;
import cc.ghast.artemis.v2.utils.misc.CompatUtil;
import java.io.PrintStream;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class PacketListener {
    public void onPacket(PacketReceiveEvent event) {
        try {
            Player player = event.getPlayer();
            PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
            if (player == null || data == null || event.getPacket() == null) {
                return;
            }
            Object packet = event.getPacket();
            long now = System.currentTimeMillis();
            NMSObject payload = PacketUtil.findInboundPacket(player, event.getType(), event.getPacket());
            data.getCheckManager().getAbstractChecks().forEach(check -> check.handle(data, payload));
            switch (event.getType()) {
                case "PacketPlayInCustomPayload": {
                    WrappedInCustomPayload p = new WrappedInCustomPayload(packet, player);
                    Chat.sendConsoleMessage("&7[&a&lIN&7] &f-> &e" + p.getMessage());
                    break;
                }
                case "PacketHandshakingInSetProtocol": {
                    WrappedHandshakeClientProtocol p = new WrappedHandshakeClientProtocol(packet, player);
                    System.out.println("VL=" + p.getProtocolVersion());
                    break;
                }
                case "PacketPlayInLook": 
                case "PacketPlayInPositionLook": 
                case "PacketPlayInPosition": 
                case "PacketPlayInFlying": {
                    WrappedInFlyingPacket p = new WrappedInFlyingPacket(packet, player);
                    Position position = new Position(player, p.getX(), p.getY(), p.getZ(), p.getPitch(), p.getYaw(), player.getWorld(), System.currentTimeMillis());
                    Rotation rotation = new Rotation(player, p.getPitch(), p.getYaw());
                    data.user.setPlaced(false);
                    data.user.setTpUnknown(false);
                    if (data.movement.getRespawnTicks() > 0) {
                        data.movement.setRespawnTicks(data.movement.getRespawnTicks() - 1);
                    }
                    if (data.movement.getRespawnTicks() < 0) {
                        data.movement.setRespawnTicks(0);
                    }
                    if (data.movement.getLastLocation() != null && System.currentTimeMillis() - data.movement.getLastLocation().getTimestamp() > 110L) {
                        data.movement.setLastDelayedMovePacket(now);
                    }
                    if (data.movement.getTeleportTicks() > 0) {
                        data.movement.setTeleportTicks(data.movement.getTeleportTicks() - 1);
                    }
                    if (p.isPos()) {
                        data.movement.setLastLocation(data.movement.getLocation());
                        data.movement.setLocation(position);
                        data.movement.getPreviousPositions().add(position);
                        data.movement.setLastMovePacket(now);
                        if (now - data.movement.getLocation().getTimestamp() > 110L) {
                            data.movement.setLastDelayedMovePacket(now);
                        }
                    }
                    if (p.isLook()) {
                        data.movement.setLastRotation(data.movement.getRotation());
                        data.movement.setRotation(rotation);
                    }
                    Position to = data.getMovement().getLocation();
                    Position from = data.getMovement().getLastLocation();
                    World world = player.getWorld();
                    if (from == null || to == null || from.getBukkitWorld() != to.getBukkitWorld()) {
                        return;
                    }
                    int startX = Location.locToBlock(to.getX() - 0.3);
                    int endX = Location.locToBlock(to.getX() + 0.3);
                    int startZ = Location.locToBlock(to.getZ() - 0.3);
                    int endZ = Location.locToBlock(to.getZ() + 0.3);
                    Bukkit.getScheduler().runTask(Artemis.INSTANCE.getPlugin(), () -> {
                        if (p.isPos()) {
                            data.movement.getPositions().add(position);
                        }
                        data.user.setInLiquid(world.getBlockAt(to.getBukkitLocation().add(0.0, -1.0, 0.0)).getType() == Material.WATER || world.getBlockAt(to.getBukkitLocation().add(0.0, -1.0, 0.0)).getType() == Material.STATIONARY_WATER);
                        Block block = to.getBukkitLocation().getBlock().getRelative(BlockFace.UP);
                        data.user.setUnderBlock(BlockUtil.isOnGround(to.getBukkitLocation(), -2));
                        if (block != null && block.getType() != Material.AIR) {
                            data.user.setLastUnderBlock(System.currentTimeMillis());
                        }
                        if (from.getBukkitWorld().equals((Object) to.getBukkitWorld()) && to.getBukkitLocation().distance(from.getBukkitLocation()) != 0.0) {
                            data.movement.setWasOnGround(player.isOnGround());
                            data.movement.setOnIce(BlockUtil.isOnIce(to.getBukkitLocation(), 1) || BlockUtil.isOnIce(to.getBukkitLocation(), 2));
                        }
                        try {
                            if (!CompatUtil.is17() && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK) {
                                data.movement.setLastSlime(System.currentTimeMillis());
                            }
                        }
                        catch (Exception ex) {
                            return;
                        }
                    });
                    if (!(from.getY() < to.getY())) break;
                    data.movement.setLastJump(System.currentTimeMillis());
                    break;
                }
                case "PacketPlayInUseEntity": {
                    data.combat.setLastAttack(System.currentTimeMillis());
                    break;
                }
                case "PacketPlayInArmAnimation":
                case "PacketPlayInHeldItemSlot":
                case "PacketPlayInSteerVehicle": {
                    break;
                }
                case "PacketPlayInEntityAction": {
                    WrappedInEntityActionPacket p = new WrappedInEntityActionPacket(packet, player);
                    break;
                }
                case "PacketPlayInBlockDig": {
                    WrappedInBlockDigPacket p = new WrappedInBlockDigPacket(packet, player);
                    WrappedInBlockDigPacket.EnumPlayerDigType type = p.getAction();
                    if (type.equals((Object)WrappedInBlockDigPacket.EnumPlayerDigType.START_DESTROY_BLOCK)) {
                        data.user.setDigging(true);
                        break;
                    }
                    if (!type.equals((Object)WrappedInBlockDigPacket.EnumPlayerDigType.ABORT_DESTROY_BLOCK) && !type.equals((Object)WrappedInBlockDigPacket.EnumPlayerDigType.STOP_DESTROY_BLOCK)) break;
                    data.user.setDigging(false);
                    data.user.setLastDig(now);
                    break;
                }
                case "PacketPlayInBlockPlace": {
                    data.user.setPlaced(true);
                    data.user.setLastPlace(System.currentTimeMillis());
                    break;
                }

                case "PacketPlayInKeepAlive": {
                    WrappedInKeepAlivePacket wrappedInKeepAlivePacket = new WrappedInKeepAlivePacket(packet, player);
                    data.user.setLastKeepAlive(System.currentTimeMillis());
                    long id = wrappedInKeepAlivePacket.getTime();
                    if (data.user.keepAliveExists(id)) {
                        data.user.setPing(System.currentTimeMillis() - data.user.getKeepAliveTime(id));
                        break;
                    }
                    data.user.addKeepAliveTime(id);
                    break;
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void onSend(PacketSendEvent e) {
        Player player = e.getPlayer();
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        Object packet = e.getPacket();
        if (player == null || data == null || e.getPacket() == null) {
            return;
        }
        try {
            switch (e.getType()) {
                case "PacketHandshakingOutSetProtocol": {
                    WrappedHandshakeClientProtocol p = new WrappedHandshakeClientProtocol(packet, player);
                    System.out.println("VL=" + p.getProtocolVersion());
                    return;
                }
                case "PacketPlayOutKeepAlive": {
                    data.user.setLastSentKeepAlive(System.currentTimeMillis());
                    return;
                }
                case "PacketPlayOutEntityVelocity": {
                    WrappedOutVelocityPacket wrappedOutVelocityPacket = new WrappedOutVelocityPacket(packet, player);
                    if (wrappedOutVelocityPacket.getId() == player.getEntityId()) {
                        double x = Math.abs(wrappedOutVelocityPacket.getX() / 8000.0);
                        double y = wrappedOutVelocityPacket.getY() / 8000.0;
                        double z = Math.abs(wrappedOutVelocityPacket.getZ());
                        Velocity velocity = new Velocity(x, y, z);
                        if (x > 0.0 || z > 0.0) {
                            velocity.setHorizontal((int)Math.sqrt(x * x + z * z));
                        }
                        if (y > 0.0) {
                            velocity.setVertical((int)y);
                            if (data.getPlayer().isOnGround() && player.getLocation().getY() % 1.0 == 0.0) {
                                velocity.setX(x);
                                velocity.setY(y);
                                velocity.setZ(z);
                            }
                        }
                        data.movement.setVelocity(velocity);
                    }
                    return;
                }
                case "PacketPlayOutPosition": {
                    data.movement.setTeleportTicks(50);
                    return;
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
}

