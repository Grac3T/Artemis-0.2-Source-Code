/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.Validate
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil {
    private static Logger log = Logger.getLogger("Artemis");
    private static HashMap<String, Class<?>> classCache = new HashMap(128);
    private static HashMap<String, Field> fieldCache = new HashMap(128);
    private static HashMap<String, Method> methodCache = new HashMap(128);
    private static HashMap<String, Constructor> constructorCache = new HashMap(128);
    private static String obcPrefix = null;
    private static String nmsPrefix = null;

    private ReflectionUtil() {
    }

    public static Class<?> getCraftBukkitClass(String className) {
        return ReflectionUtil.getClass(obcPrefix + className);
    }

    public static Class<?> getNMSClass(String className) {
        return ReflectionUtil.getClass(nmsPrefix + className);
    }

    public static Class<?> getClass(String className) {
        Validate.notNull((Object)className);
        if (classCache.containsKey(className)) {
            return classCache.get(className);
        }
        Class<?> classLookup = null;
        try {
            classLookup = Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the class " + className);
        }
        if (classLookup != null) {
            classCache.put(className, classLookup);
        }
        return classLookup;
    }

    public static Field getField(String fieldName, Class<?> searchClass) {
        Validate.notNull((Object)fieldName);
        Validate.notNull(searchClass);
        String cacheName = searchClass.getCanonicalName() + "@" + fieldName;
        if (fieldCache.containsKey(cacheName)) {
            return fieldCache.get(cacheName);
        }
        Field fieldLookup = null;
        try {
            fieldLookup = searchClass.getField(fieldName);
        }
        catch (NoSuchFieldException ex) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the field " + fieldName + " in class " + searchClass.getSimpleName());
        }
        if (fieldLookup != null) {
            fieldCache.put(cacheName, fieldLookup);
        }
        return fieldLookup;
    }

    public static Method getMethod(Class<?> searchClass, String methodName, Class<?>[] args) {
        Validate.notNull((Object)methodName);
        Validate.notNull(searchClass);
        String cacheName = searchClass.getCanonicalName() + "@" + methodName;
        if (methodCache.containsKey(cacheName)) {
            return methodCache.get(cacheName);
        }
        Method methodLookup = null;
        try {
            methodLookup = searchClass.getMethod(methodName, args);
        }
        catch (NoSuchMethodException ex) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the method " + methodName + " in class " + searchClass.getSimpleName());
        }
        if (methodLookup != null) {
            methodCache.put(cacheName, methodLookup);
        }
        return methodLookup;
    }

    public static Method getMethod(String methodName, Class<?> searchClass, Class<?>[] params) {
        Validate.notNull((Object)methodName);
        Validate.notNull(searchClass);
        String cacheName = searchClass.getCanonicalName() + "@" + methodName;
        if (methodCache.containsKey(cacheName)) {
            return methodCache.get(cacheName);
        }
        Method methodLookup = null;
        try {
            methodLookup = searchClass.getMethod(methodName, params);
        }
        catch (NoSuchMethodException ex) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the method " + methodName + " in class " + searchClass.getSimpleName());
        }
        if (methodLookup != null) {
            methodCache.put(cacheName, methodLookup);
        }
        return methodLookup;
    }

    public static Method getMethod(String methodName, Class<?> searchClass) {
        Validate.notNull((Object)methodName);
        Validate.notNull(searchClass);
        String cacheName = searchClass.getCanonicalName() + "@" + methodName;
        if (methodCache.containsKey(cacheName)) {
            return methodCache.get(cacheName);
        }
        Method methodLookup = null;
        try {
            methodLookup = searchClass.getMethod(methodName, new Class[0]);
        }
        catch (NoSuchMethodException ex) {
            log.log(Level.SEVERE, "[Reflection] Unable to find the method " + methodName + " in class " + searchClass.getSimpleName());
        }
        if (methodLookup != null) {
            methodCache.put(cacheName, methodLookup);
        }
        return methodLookup;
    }

    public static Constructor<?> getConstructor(Class<?> searchClass, Class<?>[] params) {
        Validate.notNull(searchClass);
        Validate.notNull(params);
        String cacheName = searchClass.getSimpleName() + "#";
        for (Class<?> cacheBuilder : params) {
            cacheName = cacheName + cacheBuilder.getSimpleName() + "_";
        }
        if (constructorCache.containsKey(cacheName = cacheName.substring(0, cacheName.length() - 1))) {
            return constructorCache.get(cacheName);
        }
        Constructor<?> constructorLookup = null;
        try {
            constructorLookup = searchClass.getConstructor(params);
        }
        catch (NoSuchMethodException ex) {
            StringBuilder toPrintParams = new StringBuilder();
            for (Class<?> paramBuilder : params) {
                toPrintParams.append(paramBuilder.getCanonicalName()).append(", ");
            }
            toPrintParams.setLength(toPrintParams.length() - 2);
            log.log(Level.SEVERE, "[Reflection] Unable to find the constructor  with the params [" + toPrintParams.toString() + "] in class " + searchClass.getSimpleName());
        }
        if (constructorLookup != null) {
            constructorCache.put(cacheName, constructorLookup);
        }
        return constructorLookup;
    }

    public static Object getPlayerHandle(Player player) {
        try {
            Method handleMethod = player.getClass().getMethod("getHandle", new Class[0]);
            return handleMethod.invoke((Object)player, new Object[0]);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "[Reflection] Unable to getHandle of " + player.getName());
            return null;
        }
    }

    static {
        try {
            nmsPrefix = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
            obcPrefix = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        }
        catch (Exception e) {
            nmsPrefix = "net.minecraft.server.";
            obcPrefix = "org.bukkit.craftbukkit.";
        }
    }
}

