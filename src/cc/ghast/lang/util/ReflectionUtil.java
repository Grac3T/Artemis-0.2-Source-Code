package cc.ghast.lang.util;

public class ReflectionUtil
{
    public static Object getEnumConstant(Class clazz, String constant) {
        return Enum.valueOf(clazz, constant);
    }

    public static boolean isNumber(final Class<?> type) {
        return type == Number.class;
    }

    public static boolean isBoolean(final Class<?> type) {
        return type == Boolean.class;
    }

    public static boolean isString(final Class<?> type) {
        return type == String.class;
    }
}
