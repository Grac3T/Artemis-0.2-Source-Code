/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInAbilitiesPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInChatPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInCloseWindowPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInCustomPayload;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInEntityActionPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInHeldItemSlotPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInKeepAlivePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInSteerVehiclePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInTabComplete;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInTransactionPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.out.WrappedOutVelocityPacket;
import org.bukkit.entity.Player;

public class PacketUtil {
    public static NMSObject findInboundPacket(Player player, String type, Object packet) {
        switch (type) {
            case "PacketPlayInAbilities": {
                return new WrappedInAbilitiesPacket(packet, player);
            }
            case "PacketPlayInArmAnimation": {
                return new WrappedInArmAnimationPacket(packet, player);
            }
            case "PacketPlayInBlockDig": {
                return new WrappedInBlockDigPacket(packet, player);
            }
            case "PacketPlayInBlockPlace": {
                return new WrappedInBlockPlacePacket(packet, player);
            }
            case "PacketPlayInChat": {
                return new WrappedInChatPacket(packet, player);
            }
            case "PacketPlayInCloseWindow": {
                return new WrappedInCloseWindowPacket(packet, player);
            }
            case "PacketPlayInCustomPayload": {
                return new WrappedInCustomPayload(packet, player);
            }
            case "PacketPlayInEntityAction": {
                return new WrappedInEntityActionPacket(packet, player);
            }
            case "PacketPlayInFlying": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInPosition": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInPositionLook": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInFlying$PacketPlayInPosition": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInFlying$PacketPlayInPositionLook": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInFlying$PacketPlayInLook": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInLook": {
                return new WrappedInFlyingPacket(packet, player);
            }
            case "PacketPlayInHeldItemSlot": {
                return new WrappedInHeldItemSlotPacket(packet, player);
            }
            case "PacketPlayInKeepAlive": {
                return new WrappedInKeepAlivePacket(packet, player);
            }
            case "PacketPlayInSteerVehicle": {
                return new WrappedInSteerVehiclePacket(packet, player);
            }
            case "PacketPlayInTabComplete": {
                return new WrappedInTabComplete(packet, player);
            }
            case "PacketPlayInTransaction": {
                return new WrappedInTransactionPacket(packet, player);
            }
            case "PacketPlayInUseEntity": {
                return new WrappedInUseEntityPacket(packet, player);
            }
        }
        return null;
    }

    public static NMSObject findOutboundPacket(Player player, String type, Object packet) {
        switch (type) {
            case "PacketPlayOutEntityVelocity": {
                return new WrappedOutVelocityPacket(packet, player);
            }
        }
        return null;
    }

    public static String findPacketName(Object packet) {
        return packet.getClass().getSimpleName();
    }
}

