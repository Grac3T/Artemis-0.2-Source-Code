/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public abstract class Packet {
    private static Map<String, Class<?>> constructors = new HashMap();
    private Object packet;
    private boolean cancelled;

    public Packet(Object packet) {
        this.packet = packet;
    }

    public static Object construct(String packet, Object ... args) {
        try {
            Class<?> c = constructors.get(packet);
            if (c == null) {
                c = Reflection.getMinecraftClass(packet);
                constructors.put(packet, c);
            }
            Object p = c.newInstance();
            Field[] fields = c.getDeclaredFields();
            int failed = 0;
            for (int i = 0; i < args.length; ++i) {
                Object o = args[i];
                if (o == null) continue;
                fields[i - failed].setAccessible(true);
                try {
                    fields[i - failed].set(p, o);
                    continue;
                }
                catch (Exception e) {
                    ++failed;
                }
            }
            return p;
        }
        catch (Exception e) {
            System.out.println("The plugin cannot work as protocol incompatibilities were detected... Disabling...");
            e.printStackTrace();
            return null;
        }
    }

    public static <T> FieldAccessor<T> fetchField(String className, Class<T> fieldType, int index) {
        return Reflection.getFieldSafe(Reflection.NMS_PREFIX + "." + className, fieldType, index);
    }

    public static boolean isPositionLook(String type) {
        switch (type) {
            case "PacketPlayInPositionLook": 
            case "PacketPlayInFlying$PacketPlayInPositionLook": {
                return true;
            }
        }
        return false;
    }

    public static boolean isPosition(String type) {
        switch (type) {
            case "PacketPlayInPosition": 
            case "PacketPlayInFlying$PacketPlayInPosition": {
                return true;
            }
        }
        return false;
    }

    public static boolean isLook(String type) {
        switch (type) {
            case "PacketPlayInLook": 
            case "PacketPlayInFlying$PacketPlayInLook": {
                return true;
            }
        }
        return false;
    }

    public String getPacketName() {
        String name = this.packet.getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public void process(Player player, ProtocolVersion version) {
    }

    public void setPacket(String packet, Object ... args) {
        this.packet = Packet.construct(packet, args);
    }

    public <T> T fetch(FieldAccessor<T> field) {
        return field.get(this.packet);
    }

    public Packet() {
    }

    public Object getPacket() {
        return this.packet;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static class Server {
        private static final String SERVER = "PacketPlayOut";
        public static final String KEEP_ALIVE = "PacketPlayOutKeepAlive";
        public static final String CHAT = "PacketPlayOutChat";
        public static final String POSITION = "PacketPlayOutPosition";
        public static final String TRANSACTION = "PacketPlayOutTransaction";
        public static final String NAMED_ENTITY_SPAWN = "PacketPlayOutNamedEntitySpawn";
        public static final String SPAWN_ENTITY_LIVING = "PacketPlayOutSpawnEntityLiving";
        public static final String SPAWN_ENTITY = "PacketPlayOutSpawnEntity";
        public static final String CUSTOM_PAYLOAD = "PacketPlayOutCustomPayload";
        public static final String ABILITIES = "PacketPlayOutAbilities";
        public static final String ENTITY_METADATA = "PacketPlayOutEntityMetadata";
        public static final String ENTITY_VELOCITY = "PacketPlayOutEntityVelocity";
        public static final String ENTITY_DESTROY = "PacketPlayOutEntityDestroy";
        public static final String BLOCK_CHANGE = "PacketPlayOutBlockChange";
        public static final String CLOSE_WINDOW = "PacketPlayOutCloseWindow";
        public static final String HELD_ITEM = "PacketPlayOutHeldItemSlot";
        public static final String TAB_COMPLETE = "PacketPlayOutTabComplete";
    }

    public static class Client {
        private static final String CLIENT = "PacketPlayIn";
        public static final String KEEP_ALIVE = "PacketPlayInKeepAlive";
        public static final String FLYING = "PacketPlayInFlying";
        public static final String POSITION = "PacketPlayInPosition";
        public static final String POSITION_LOOK = "PacketPlayInPositionLook";
        public static final String LOOK = "PacketPlayInLook";
        @Deprecated
        public static final String LEGACY_POSITION = "PacketPlayInFlying$PacketPlayInPosition";
        @Deprecated
        public static final String LEGACY_POSITION_LOOK = "PacketPlayInFlying$PacketPlayInPositionLook";
        @Deprecated
        public static final String LEGACY_LOOK = "PacketPlayInFlying$PacketPlayInLook";
        public static final String STEER_VEHICLE = "PacketPlayInSteerVehicle";
        public static final String TRANSACTION = "PacketPlayInTransaction";
        public static final String BLOCK_DIG = "PacketPlayInBlockDig";
        public static final String ENTITY_ACTION = "PacketPlayInEntityAction";
        public static final String USE_ENTITY = "PacketPlayInUseEntity";
        public static final String WINDOW_CLICK = "PacketPlayInWindowClick";
        public static final String CUSTOM_PAYLOAD = "PacketPlayInCustomPayload";
        public static final String ARM_ANIMATION = "PacketPlayInArmAnimation";
        public static final String BLOCK_PLACE = "PacketPlayInBlockPlace";
        public static final String ABILITIES = "PacketPlayInAbilities";
        public static final String HELD_ITEM_SLOT = "PacketPlayInHeldItemSlot";
        public static final String CLOSE_WINDOW = "PacketPlayInCloseWindow";
        public static final String TAB_COMPLETE = "PacketPlayInTabComplete";
        public static final String CHAT = "PacketPlayInChat";
    }

    public static class Type {
        public static final String WATCHABLE_OBJECT = Reflection.VERSION.startsWith("v1_7") || Reflection.VERSION.startsWith("v1_8_R1") ? "WatchableObject" : "DataWatcher$WatchableObject";
        public static final String BASEBLOCKPOSITION = "BaseBlockPosition";
        public static final String ITEMSTACK = Reflection.NMS_PREFIX + ".ItemStack";
        public static final String ENTITY = Reflection.NMS_PREFIX + ".Entity";
        public static final String DATAWATCHER = Reflection.NMS_PREFIX + ".DataWatcher";
        public static final String DATAWATCHEROBJECT = Reflection.NMS_PREFIX + ".DataWatcherObject";
        public static final String CRAFTITEMSTACK = Reflection.OBC_PREFIX + ".inventory.CraftItemStack";
        public static final String GAMEPROFILE = "com.mojang.authlib.GameProfile";
        public static final String PROPERTYMAP = "com.mojang.authlib.PropertyMap";
    }

}

