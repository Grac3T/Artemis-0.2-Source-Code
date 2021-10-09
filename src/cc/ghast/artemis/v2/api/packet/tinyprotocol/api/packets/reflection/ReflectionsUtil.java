/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.TinyProtocolHandler;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.utils.boundingbox.BoundingBox;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class ReflectionsUtil {
    public static Class<?> blockPosition = null;
    private static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    public static Class<?> EntityPlayer = ReflectionsUtil.getNMSClass("EntityPlayer");
    public static Class<?> Entity = ReflectionsUtil.getNMSClass("Entity");
    public static Class<?> CraftPlayer = ReflectionsUtil.getCBClass("entity.CraftPlayer");
    public static Class<?> CraftEntity = ReflectionsUtil.getCBClass("entity.CraftEntity");
    public static Class<?> CraftWorld = ReflectionsUtil.getCBClass("CraftWorld");
    private static Class<?> craftServer = ReflectionsUtil.getCBClass("CraftServer");
    public static Class<?> World = ReflectionsUtil.getNMSClass("World");
    public static Class<?> worldServer = ReflectionsUtil.getNMSClass("WorldServer");
    public static Class<?> playerConnection = ReflectionsUtil.getNMSClass("PlayerConnection");
    public static Class<?> networkManager = ReflectionsUtil.getNMSClass("NetworkManager");
    public static Class<?> minecraftServer = ReflectionsUtil.getNMSClass("MinecraftServer");
    public static Class<?> packet = ReflectionsUtil.getNMSClass("Packet");
    public static Class<?> iBlockData = null;
    public static Class<?> iBlockAccess = null;
    private static Class<?> vanillaBlock = ReflectionsUtil.getNMSClass("Block");
    private static Method getCubes = ReflectionsUtil.getMethod(World, "a", ReflectionsUtil.getNMSClass("AxisAlignedBB"));
    private static Method getCubes1_12 = ReflectionsUtil.getMethod(World, "getCubes", ReflectionsUtil.getNMSClass("Entity"), ReflectionsUtil.getNMSClass("AxisAlignedBB"));

    public ReflectionsUtil() {
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            iBlockData = ReflectionsUtil.getNMSClass("IBlockData");
            blockPosition = ReflectionsUtil.getNMSClass("BlockPosition");
            iBlockAccess = ReflectionsUtil.getNMSClass("IBlockAccess");
        }
    }

    public static Object getEntityPlayer(Player player) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(CraftPlayer, "getHandle", new Class[0]), (Object)player, new Object[0]);
    }

    public static Object getEntity(Entity entity) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(CraftEntity, "getHandle", new Class[0]), (Object)entity, new Object[0]);
    }

    public static Object getExpandedBoundingBox(Object box, double x, double y, double z) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(box.getClass(), "grow", Double.TYPE, Double.TYPE, Double.TYPE), box, x, y, z);
    }

    public static Object modifyBoundingBox(Object box, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        double newminX = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "a"), box) - minX;
        double newminY = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "b"), box) - minY;
        double newminZ = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "c"), box) - minZ;
        double newmaxX = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "d"), box) + maxX;
        double newmaxY = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "e"), box) + maxY;
        double newmaxZ = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "f"), box) + maxZ;
        return ReflectionsUtil.newInstance(ReflectionsUtil.getNMSClass("AxisAlignedBB"), newminX, newminY, newminZ, newmaxX, newmaxY, newmaxZ);
    }

    private static Vector getBoxMin(Object box) {
        if (ReflectionsUtil.hasField(box.getClass(), "a")) {
            double x = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "a"), box);
            double y = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "b"), box);
            double z = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "c"), box);
            return new Vector(x, y, z);
        }
        double x = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "minX"), box);
        double y = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "minY"), box);
        double z = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "minZ"), box);
        return new Vector(x, y, z);
    }

    public static Object getMinecraftServer() {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(craftServer, "getServer", new Class[0]), (Object)Bukkit.getServer(), new Object[0]);
    }

    private static Vector getBoxMax(Object box) {
        if (ReflectionsUtil.hasField(box.getClass(), "d")) {
            double x = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "d"), box);
            double y = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "e"), box);
            double z = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "f"), box);
            return new Vector(x, y, z);
        }
        double x = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "maxX"), box);
        double y = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "maxY"), box);
        double z = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "maxZ"), box);
        return new Vector(x, y, z);
    }

    public static BoundingBox toBoundingBox(Object aaBB) {
        Vector min = ReflectionsUtil.getBoxMin(aaBB);
        Vector max = ReflectionsUtil.getBoxMax(aaBB);
        return new BoundingBox((float)min.getX(), (float)min.getY(), (float)min.getZ(), (float)max.getX(), (float)max.getY(), (float)max.getZ(), System.currentTimeMillis());
    }

    public static float getBlockDurability(Block block) {
        Object vanillaBlock = ReflectionsUtil.getVanillaBlock(block);
        return ((Float)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(ReflectionsUtil.getNMSClass("Block"), "strength"), vanillaBlock)).floatValue();
    }

    public static boolean canDestroyBlock(Player player, Block block) {
        Object inventory = ReflectionsUtil.getVanillaInventory(player);
        return (Boolean)ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("PlayerInventory"), "b", ReflectionsUtil.getNMSClass("Block")), inventory, ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_8_9) ? ReflectionsUtil.getBlockData(block) : ReflectionsUtil.getVanillaBlock(block));
    }

    public static Object getVanillaInventory(Player player) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getCBClass("inventory.CraftInventoryPlayer"), "getInventory", new Class[0]), (Object)player.getInventory(), new Object[0]);
    }

    public static float getFriction(Block block) {
        Object blockNMS = ReflectionsUtil.getVanillaBlock(block);
        return ((Float)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(vanillaBlock, "frictionFactor"), blockNMS)).floatValue();
    }

    public static Object getBlockPosition(Location location) {
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            return ReflectionsUtil.newInstance(blockPosition, location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
        return null;
    }

    public static List<Entity> getEntitiesInWorld(World world) {
        Object worldHandle = ReflectionsUtil.getWorldHandle(world);
        ArrayList<Entity> toReturn = new ArrayList<Entity>();
        ArrayList entityList = new ArrayList((List)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(ReflectionsUtil.getNMSClass("World"), "entityList"), worldHandle));
        Class<?> entity = ReflectionsUtil.getNMSClass("Entity");
        entityList.forEach(object -> {
            Object bEntity = ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(entity, "getBukkitEntity", new Class[0]), object, new Object[0]);
            if (bEntity != null) {
                toReturn.add((Entity)bEntity);
            }
        });
        return toReturn;
    }

    public static double getTPS(Server server) {
        Object handle = ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getCBClass("CraftServer"), "getHandle", new Class[0]), (Object)server, new Object[0]);
        return ((Integer)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(ReflectionsUtil.getNMSClass("MinecraftServer"), "TPS"), handle)).intValue();
    }

    public static float getBlockHardness(Material material) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!material.isBlock()) {
            return 0.0f;
        }
        int blockId = material.getId();
        Object nmsBlock = ReflectionsUtil.getNMSClass("Block").getMethod("getById", Integer.TYPE).invoke(null, blockId);
        try {
            Field field = nmsBlock.getClass().getDeclaredField("strength");
            field.setAccessible(true);
            return ((Float)field.get(nmsBlock)).floatValue();
        }
        catch (NoSuchFieldException e) {
            return 0.0f;
        }
    }

    public static float getDestroySpeed(Block block, Player player) {
        if (ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_8_9)) {
            Object item = ReflectionsUtil.getVanillaItem(player.getItemInHand());
            return ((Float)ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("Item"), "getDestroySpeed", ReflectionsUtil.getNMSClass("ItemStack"), ReflectionsUtil.getNMSClass("IBlockData")), item, ReflectionsUtil.getVanillaItemStack(player.getItemInHand()), ReflectionsUtil.getBlockData(block))).floatValue();
        }
        Object item = ReflectionsUtil.getVanillaItem(player.getInventory().getItemInHand());
        return ((Float)ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("Item"), "getDestroySpeed", ReflectionsUtil.getNMSClass("ItemStack"), ReflectionsUtil.getNMSClass("Block")), item, ReflectionsUtil.getVanillaItemStack(player.getInventory().getItemInHand()), ReflectionsUtil.getVanillaBlock(block))).floatValue();
    }

    public static Object getVanillaItem(ItemStack itemStack) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("ItemStack"), "getItem", new Class[0]), ReflectionsUtil.getVanillaItemStack(itemStack), new Object[0]);
    }

    public static Object getVanillaItemStack(ItemStack itemStack) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getCBClass("inventory.CraftItemStack"), "asNMSCopy", ReflectionsUtil.getClass("org.bukkit.inventory.ItemStack")), (Object)itemStack, new Object[]{itemStack});
    }

    public static Object getVanillaBlock(Block block) {
        if (!ReflectionsUtil.isBukkitVerison("1_7")) {
            Object getType = ReflectionsUtil.getBlockData(block);
            return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(iBlockData, "getBlock", new Class[0]), getType, new Object[0]);
        }
        Object world = ReflectionsUtil.getWorldHandle(block.getWorld());
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(worldServer, "getType", Integer.TYPE, Integer.TYPE, Integer.TYPE), world, block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
    }

    public static Object getBlockData(Block block) {
        try {
            if (!ReflectionsUtil.isBukkitVerison("1_7")) {
                Object bPos = blockPosition.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
                Object world = ReflectionsUtil.getWorldHandle(block.getWorld());
                return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(worldServer, "getType", blockPosition), world, bPos);
            }
            Object world = ReflectionsUtil.getWorldHandle(block.getWorld());
            return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(worldServer, "getType", Integer.TYPE, Integer.TYPE, Integer.TYPE), world, block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getBelowBox(Player player, double below) {
        Object box = ReflectionsUtil.getBoundingBox(player);
        double minX = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "a"), box);
        double minY = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "b"), box) - below;
        double minZ = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "c"), box);
        double maxX = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "d"), box);
        double maxY = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "e"), box);
        double maxZ = (Double)ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(box.getClass(), "f"), box);
        try {
            return ReflectionsUtil.getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(minX, minY, minZ, maxX, maxY, maxZ);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getBoundingBox(Player player) {
        return ReflectionsUtil.getBoundingBox((Entity)player);
    }

    public static Object getBoundingBox(Entity entity) {
        return ReflectionsUtil.isBukkitVerison("1_7") ? ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(Entity, "boundingBox"), ReflectionsUtil.getEntity(entity)) : ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(Entity, "getBoundingBox", new Class[0]), ReflectionsUtil.getEntity(entity), new Object[0]);
    }

    public static boolean isBukkitVerison(String version) {
        return ProtocolVersion.getGameVersion().getServerVersion().contains(version);
    }

    public static File getPluginFolder() {
        Object console = ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getCBClass("CraftServer"), "console", new Class[0]), (Object)TinyProtocolHandler.getPlugin().getServer(), new Object[0]);
        Object options = ReflectionsUtil.getFieldValue(ReflectionsUtil.getFieldByName(ReflectionsUtil.getNMSClass("MinecraftServer"), "options"), console);
        return (File)ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("OptionSet"), "valueOf", String.class), options, "plugins");
    }

    public static boolean isNewVersion() {
        return ReflectionsUtil.isBukkitVerison("1_9") || ReflectionsUtil.isBukkitVerison("1_1");
    }

    public static Class<?> getCBClass(String string) {
        return ReflectionsUtil.getClass("org.bukkit.craftbukkit." + version + "." + string);
    }

    public static Object newAxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        try {
            return ReflectionsUtil.isBukkitVerison("1_7") ? ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(ReflectionsUtil.getNMSClass("AxisAlignedBB"), "a", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE), null, minX, minY, minZ, maxX, maxY, maxZ) : ReflectionsUtil.getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(minX, minY, minZ, maxX, maxY, maxZ);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newVoxelShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        try {
            return ReflectionsUtil.getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(minX, minY, minZ, maxX, maxY, maxZ);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getMotionY(Player player) {
        double motionY = 0.0;
        try {
            motionY = (Double)ReflectionsUtil.getEntityPlayer(player).getClass().getField("motY").get(ReflectionsUtil.getEntityPlayer(player));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return motionY;
    }

    public static Object newAxisAlignedBB(Location from, Location to) {
        double minX = Math.min(from.getX(), to.getX());
        double minY = Math.min(from.getY(), to.getY());
        double minZ = Math.min(from.getZ(), to.getZ());
        double maxX = Math.max(from.getX(), to.getX());
        double maxY = Math.max(from.getY(), to.getY());
        double maxZ = Math.max(from.getZ(), to.getZ());
        try {
            return ReflectionsUtil.getNMSClass("AxisAlignedBB").getConstructor(Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).newInstance(minX, minY, minZ, maxX, maxY, maxZ);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getClass(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // removed useless ass get enum method that is skidded out of like fucking atlas

    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName) != null ? clazz.getDeclaredField(fieldName) : clazz.getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object setFieldValue(Object object, Field field, Object value) {
        try {
            field.set(object, value);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return field.getDeclaringClass();
    }

    public static boolean inBlock(Player player, Object axisAlignedBB) {
        return ReflectionsUtil.getCollidingBlocks(player, axisAlignedBB).size() > 0;
    }

    public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
        Object world = ReflectionsUtil.getWorldHandle(player.getWorld());
        return (Collection)(ReflectionsUtil.isNewVersion() ? ReflectionsUtil.getMethodValue(getCubes1_12, world, null, axisAlignedBB) : ReflectionsUtil.getMethodValue(getCubes, world, axisAlignedBB));
    }

    public static Object getWorldHandle(World world) {
        return ReflectionsUtil.getMethodValue(ReflectionsUtil.getMethod(CraftWorld, "getHandle", new Class[0]), (Object)world, new Object[0]);
    }

    public static Field getFirstFieldByType(Class<?> clazz, Class<?> type) {
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getType().equals(type)) continue;
                field.setAccessible(true);
                return field;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?> ... args) {
        try {
            Method method = clazz.getMethod(methodName, args);
            method.setAccessible(true);
            return method;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Method getMethodNoST(Class<?> clazz, String methodName, Class<?> ... args) {
        try {
            Method method = clazz.getMethod(methodName, args);
            method.setAccessible(true);
            return method;
        }
        catch (Exception method) {
            return null;
        }
    }

    public static boolean hasMethod(Class clazz, Method method) {
        return Arrays.stream(clazz.getMethods()).anyMatch(methodLoop -> methodLoop.getName().equals(method.getName()));
    }

    public static boolean hasMethod(Class clazz, String methodName) {
        return Arrays.stream(clazz.getMethods()).anyMatch(methodLoop -> methodLoop.getName().equals(methodName));
    }

    public static Object getMethodValue(Method method, Object object, Object ... args) {
        try {
            return method.invoke(object, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasField(Class<?> object, String fieldName) {
        return Arrays.stream(object.getFields()).anyMatch(field -> field.getName().equalsIgnoreCase(fieldName));
    }

    public static Object getMethodValueNoST(Method method, Object object, Object ... args) {
        try {
            return method.invoke(object, args);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getFieldValueNoST(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Field getFieldByNameNoST(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName) != null ? clazz.getDeclaredField(fieldName) : clazz.getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Object newInstance(Class<?> objectClass, Object ... args) {
        try {
            return objectClass.getConstructor(args.getClass()).newInstance(args);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String string) {
        return ReflectionsUtil.getClass("net.minecraft.server." + version + "." + string);
    }
}

