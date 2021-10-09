/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.MethodInvoker;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class NMSObject {
    private static final MethodInvoker asCraftMirror = Reflection.getMethod(Type.CRAFTITEMSTACK, "asCraftMirror", Reflection.getClass(Type.ITEMSTACK));
    private static Map<String, Class<?>> constructors = new HashMap();
    private Object object;
    private boolean cancelled;
    private Player player;

    public NMSObject(Object object) {
        this.object = object;
        this.process(this.player, ProtocolVersion.getGameVersion());
    }

    public NMSObject() {
    }

    public NMSObject(Object object, Player player) {
        this.object = object;
        this.player = player;
        this.process(player, ProtocolVersion.getGameVersion());
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

    public static Object construct(String packet, Object arg) {
        try {
            Class<?> c = constructors.get(packet);
            if (c == null) {
                c = Reflection.getMinecraftClass(packet);
                constructors.put(packet, c);
            }
            Object p = c.newInstance();
            Field[] fields = c.getDeclaredFields();
            if (arg != null) {
                fields[0].setAccessible(true);
                fields[0].set(p, arg);
            }
            return p;
        }
        catch (Exception e) {
            System.out.println("The plugin cannot work as protocol incompatibilities were detected... Disabling...");
            e.printStackTrace();
            return null;
        }
    }

    public static Object construct(Object p, String packet, Object ... args) {
        try {
            Class<?> c = constructors.get(packet);
            if (c == null) {
                c = Reflection.getMinecraftClass(packet);
                constructors.put(packet, c);
            }
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

    public static ItemStack toBukkitStack(Object nmsStack) {
        return (ItemStack)asCraftMirror.invoke(null, nmsStack);
    }

    public static <T> FieldAccessor<T> fetchField(String className, Class<T> fieldType, int index) {
        return Reflection.getFieldSafe(Reflection.NMS_PREFIX + "." + className, fieldType, index);
    }

    public static <T> FieldAccessor<T> fetchFieldByName(String className, String name, Class<T> fieldType) {
        return Reflection.getField(Reflection.NMS_PREFIX + "." + className, name, fieldType);
    }

    public static <T> FieldAccessor<T> fetchField(String className, String fieldType, int index) {
        return (FieldAccessor<T>) Reflection.getFieldSafe(Reflection.NMS_PREFIX + "." + className, Reflection.getClass(fieldType), index);
    }

    public String getPacketName() {
        String name = this.object.getClass().getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public void process(Player player, ProtocolVersion version) {
    }

    public void setPacket(String packet, Object ... args) {
        this.object = NMSObject.construct(packet, args);
    }

    public void setPacketArg(String packet, Object arg) {
        this.object = NMSObject.construct(packet, arg);
    }

    public void setPacket(String packet, Object arg) {
        this.setPacketArg(packet, arg);
    }

    public <T> T fetch(FieldAccessor<T> field) {
        return field.get(this.object);
    }

    public <T> T fetch(FieldAccessor<T> field, Object obj) {
        return field.get(obj);
    }

    public Object getObject() {
        return this.object;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setObject(Object object) {
        this.object = object;
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
        public static final String ENTITY_METADATA = "PacketPlayOutEntityMetadata";
        public static final String ENTITY_VELOCITY = "PacketPlayOutEntityVelocity";
        public static final String ENTITY_DESTROY = "PacketPlayOutEntityDestroy";
        public static final String ENTITY = "PacketPlayOutEntity";
        public static final String REL_POSITION = "PacketPlayOutEntity$PacketPlayOutEntityMove";
        public static final String REL_POSITION_LOOK = "PacketPlayOutEntity$PacketPlayOutEntityMoveLook";
        public static final String REL_LOOK = "PacketPlayOutEntity$PacketPlayOutEntityLook";
        public static final String LEGACY_REL_POSITION = "PacketPlayOutEntityMove";
        public static final String LEGACY_REL_POSITION_LOOK = "PacketPlayOutEntityMoveLook";
        public static final String LEGACY_REL_LOOK = "PacketPlayOutEntityLook";
        public static final String ABILITIES = "PacketPlayOutAbilities";
        public static final String OPEN_WINDOW = "PacketPlayOutOpenWindow";
        public static final String HELD_ITEM = "PacketPlayOutHeldItemSlot";
        public static final String PLAYER_INFO = "PacketPlayOutPlayerInfo";
        public static final String TAB_COMPLETE = "PacketPlayOutTabComplete";
    }

    public static class Client {
        private static final String CLIENT = "PacketPlayIn";
        public static final String KEEP_ALIVE = "PacketPlayInKeepAlive";
        public static final String FLYING = "PacketPlayInFlying";
        public static final String POSITION = "PacketPlayInFlying$PacketPlayInPosition";
        public static final String POSITION_LOOK = "PacketPlayInFlying$PacketPlayInPositionLook";
        public static final String LOOK = "PacketPlayInFlying$PacketPlayInLook";
        public static final String LEGACY_POSITION = "PacketPlayInPosition";
        public static final String LEGACY_POSITION_LOOK = "PacketPlayInPositionLook";
        public static final String LEGACY_LOOK = "PacketPlayInLook";
        public static final String TRANSACTION = "PacketPlayInTransaction";
        public static final String BLOCK_DIG = "PacketPlayInBlockDig";
        public static final String ENTITY_ACTION = "PacketPlayInEntityAction";
        public static final String USE_ENTITY = "PacketPlayInUseEntity";
        public static final String WINDOW_CLICK = "PacketPlayInWindowClick";
        public static final String CUSTOM_PAYLOAD = "PacketPlayInCustomPayload";
        public static final String ARM_ANIMATION = "PacketPlayInArmAnimation";
        public static final String BLOCK_PLACE = "PacketPlayInBlockPlace";
        public static final String STEER_VEHICLE = "PacketPlayInSteerVehicle";
        public static final String HELD_ITEM = "PacketPlayInHeldItemSlot";
        public static final String CLIENT_COMMAND = "PacketPlayInClientCommand";
        public static final String CLOSE_WINDOW = "PacketPlayInCloseWindow";
        public static final String ABILITIES = "PacketPlayInAbilities";
        public static final String TAB_COMPLETE = "PacketPlayInTabComplete";
        public static final String CHAT = "PacketPlayInChat";
    }

    public static class Type {
        public static final String WATCHABLE_OBJECT = ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8_5) ? "WatchableObject" : "DataWatcher$WatchableObject";
        public static final String BASEBLOCKPOSITION = "BaseBlockPosition";
        public static final String BLOCKPOSITION = "BlockPosition";
        public static final String ITEMSTACK = Reflection.NMS_PREFIX + ".ItemStack";
        public static final String ENTITY = Reflection.NMS_PREFIX + ".Entity";
        public static final String DATAWATCHER = Reflection.NMS_PREFIX + ".DataWatcher";
        public static final String DATAWATCHEROBJECT = Reflection.NMS_PREFIX + ".DataWatcherObject";
        public static final String CHATMESSAGE = Reflection.NMS_PREFIX + ".ChatMessage";
        public static final String CRAFTITEMSTACK = Reflection.OBC_PREFIX + ".inventory.CraftItemStack";
        public static final String GAMEPROFILE = (Reflection.VERSION.startsWith("v1_7") ? "net.minecraft.util." : "") + "com.mojang.authlib.GameProfile";
        public static final String PROPERTYMAP = (Reflection.VERSION.startsWith("v1_7") ? "net.minecraft.util." : "") + "com.mojang.authlib.PropertyMap";
        public static final String VEC3D = Reflection.NMS_PREFIX + ".Vec3D";
        public static final String PLAYERINFODATA = Reflection.NMS_PREFIX + "PacketPlayOutPlayerInfo" + ".PlayerInfoData";
    }

    public static class Handshake {
        private static final String CLIENT = "PacketHandshakingIn";
        private static final String SERVER = "PacketHandshakingOut";
        public static final String HANDSHAKE_PROTOCOL = "PacketHandshakingInSetProtocol";
        public static final String HANDSHAKE_PROTOCOL_OUT = "PacketHandshakingOutSetProtocol";
    }

}

