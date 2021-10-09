/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ConstructorInvoker;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.MethodInvoker;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public final class Reflection {
    public static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    public static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    public static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
    private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");

    private Reflection() {
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
        return Reflection.getField(target, name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
        return Reflection.getField(Reflection.getClass(className), name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
        return Reflection.getField(target, null, fieldType, index);
    }

    public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
        return Reflection.getField(Reflection.getClass(className), fieldType, index);
    }

    public static <T> FieldAccessor<T> getFieldSafe(String className, Class<T> fieldType, int index) {
        try {
            return Reflection.getField(Reflection.getClass(className), fieldType, index);
        }
        catch (Exception e) {
            System.out.println("[WARN] Failed to find field at " + index + " in " + className + " with type " + fieldType.getSimpleName());
            return null;
        }
    }

    private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
        Field[][] arrarrfield = new Field[][]{target.getDeclaredFields(), target.getFields()};
        int n = arrarrfield.length;
        for (int i = 0; i < n; ++i) {
            Field[] fields;
            for (final Field field : fields = arrarrfield[i]) {
                if (name != null && !field.getName().equals(name) || !fieldType.isAssignableFrom(field.getType()) || index-- > 0) continue;
                field.setAccessible(true);
                return new FieldAccessor<T>(){

                    @Override
                    public T get(Object target) {
                        try {
                            return (T)field.get(target);
                        }
                        catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        }
                        catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }
        if (target.getSuperclass() != null) {
            return Reflection.getField(target.getSuperclass(), name, fieldType, index);
        }
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, String name, int index) {
        Field[][] arrarrfield = new Field[][]{target.getDeclaredFields(), target.getFields()};
        int n = arrarrfield.length;
        for (int i = 0; i < n; ++i) {
            Field[] fields;
            for (final Field field : fields = arrarrfield[i]) {
                if (name != null && !field.getName().equals(name) || index-- > 0) continue;
                field.setAccessible(true);
                return new FieldAccessor<T>(){

                    @Override
                    public T get(Object target) {
                        try {
                            return (T)field.get(target);
                        }
                        catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        }
                        catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }
        if (target.getSuperclass() != null) {
            return Reflection.getField(target.getSuperclass(), name, index);
        }
        throw new IllegalArgumentException("Cannot find field");
    }

    public static MethodInvoker getMethod(String className, String methodName, Class<?> ... params) {
        return Reflection.getTypedMethod(Reflection.getClass(className), methodName, null, params);
    }

    public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?> ... params) {
        return Reflection.getTypedMethod(clazz, methodName, null, params);
    }

    public static MethodInvoker getMethod(Class<?> clazz, int index, Class<?> ... params) {
        return Reflection.getTypedMethod(clazz, index, null, params);
    }

    public static MethodInvoker getMethod(Class<?> clazz, Class<?> returnType, int index, Class<?> ... params) {
        return Reflection.getTypedMethod(clazz, index, returnType, params);
    }

    public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?> ... params) {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (methodName != null && !method.getName().equals(methodName) || returnType != null && !method.getReturnType().equals(returnType) || !Arrays.equals(method.getParameterTypes(), params)) continue;
            method.setAccessible(true);
            return new MethodInvoker(){

                @Override
                public Object invoke(Object target, Object ... arguments) {
                    try {
                        return method.invoke(target, arguments);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Cannot invoke method " + method, e);
                    }
                }
            };
        }
        if (clazz.getSuperclass() != null) {
            return Reflection.getMethod(clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    public static MethodInvoker getTypedMethod(Class<?> clazz, int index, Class<?> returnType, Class<?> ... params) {
        for (final Method method : clazz.getDeclaredMethods()) {
            if (returnType != null && !method.getReturnType().equals(returnType) || !Arrays.equals(method.getParameterTypes(), params) || index-- > 0) continue;
            method.setAccessible(true);
            return new MethodInvoker(){

                @Override
                public Object invoke(Object target, Object ... arguments) {
                    try {
                        return method.invoke(target, arguments);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Cannot invoke method " + method, e);
                    }
                }
            };
        }
        if (clazz.getSuperclass() != null) {
            return Reflection.getMethod(clazz.getSuperclass(), index, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", index, Arrays.asList(params)));
    }

    public static ConstructorInvoker getConstructor(String className, Class<?> ... params) {
        return Reflection.getConstructor(Reflection.getClass(className), params);
    }

    public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?> ... params) {
        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (!Arrays.equals(constructor.getParameterTypes(), params)) continue;
            constructor.setAccessible(true);
            return new ConstructorInvoker(){

                @Override
                public Object invoke(Object ... arguments) {
                    try {
                        return constructor.newInstance(arguments);
                    }
                    catch (Exception e) {
                        throw new RuntimeException("Cannot invoke constructor " + constructor, e);
                    }
                }
            };
        }
        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    public static Class<Object> getUntypedClass(String lookupName) {
        Class<Object> clazz = (Class<Object>) Reflection.getClass(lookupName);
        return clazz;
    }

    public static Class<?> getClass(String lookupName) {
        return Reflection.getCanonicalClass(Reflection.expandVariables(lookupName));
    }

    public static Class<?> getMinecraftClass(String name) {
        return Reflection.getCanonicalClass(NMS_PREFIX + "." + name);
    }

    public static Class<?> getCraftBukkitClass(String name) {
        return Reflection.getCanonicalClass(OBC_PREFIX + "." + name);
    }

    private static Class<?> getCanonicalClass(String canonicalName) {
        try {
            return Class.forName(canonicalName);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, e);
        }
    }

    private static String expandVariables(String name) {
        StringBuffer output = new StringBuffer();
        Matcher matcher = MATCH_VARIABLE.matcher(name);
        while (matcher.find()) {
            String variable = matcher.group(1);
            String replacement = "";
            if ("nms".equalsIgnoreCase(variable)) {
                replacement = NMS_PREFIX;
            } else if ("obc".equalsIgnoreCase(variable)) {
                replacement = OBC_PREFIX;
            } else if ("version".equalsIgnoreCase(variable)) {
                replacement = VERSION;
            } else {
                throw new IllegalArgumentException("Unknown variable: " + variable);
            }
            if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
                replacement = replacement + ".";
            }
            matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(output);
        return output.toString();
    }

}

