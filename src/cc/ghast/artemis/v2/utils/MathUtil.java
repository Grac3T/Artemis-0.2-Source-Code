/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class MathUtil {
    private static Random rand = new Random();
    private static DecimalFormat decimalFormat = new DecimalFormat("0.000");
    public static Random random = new Random();

    public static long gcd(long limit, long a, long b) {
        return b <= limit ? a : MathUtil.gcd(limit, b, a % b);
    }

    public static int gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    public static int gcd(long a, long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    public static boolean getRandomBoolean() {
        return rand.nextBoolean();
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float distance = Math.abs(angle1 - angle2) % 360.0f;
        if (distance > 180.0f) {
            distance = 360.0f - distance;
        }
        return distance;
    }

    public static float getRandomFloat() {
        boolean negative = rand.nextBoolean();
        if (!negative) {
            return rand.nextFloat();
        }
        return -rand.nextFloat();
    }

    public static double distanceXZ(Location from, Location to) {
        double deltaX = from.getX() - to.getX();
        double deltaZ = from.getZ() - to.getZ();
        return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }

    public static double distanceXZSquared(Location from, Location to) {
        double deltaX = from.getX() - to.getX();
        double deltaZ = from.getZ() - to.getZ();
        return deltaX * deltaX + deltaZ * deltaZ;
    }

    public static String getPrettyDecimal(double d) {
        return decimalFormat.format(d);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double trim(int degree, double d) {
        String format = "#.#";
        for (int i = 1; i < degree; ++i) {
            format = format + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.parseDouble(twoDForm.format(d));
    }

    public static int floor(double var0) {
        int var2 = (int)var0;
        return var0 < (double)var2 ? var2 - 1 : var2;
    }

    public static int r(int i) {
        return random.nextInt(i);
    }

    public static double abs(double a) {
        return a <= 0.0 ? 0.0 - a : a;
    }

    public static String ArrayToString(String[] list) {
        String string = "";
        for (String key : list) {
            string = string + key + ",";
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String ArrayToString(List<String> list) {
        String string = "";
        for (String key : list) {
            string = string + key + ",";
        }
        if (string.length() != 0) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }

    public static String[] StringToArray(String string, String split) {
        return string.split(split);
    }

    public static double offset2d(Entity a, Entity b) {
        return MathUtil.offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(Location a, Location b) {
        return MathUtil.offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(Vector a, Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static double offset(Entity a, Entity b) {
        return MathUtil.offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(Location a, Location b) {
        return MathUtil.offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static double offsetSquared(Vector a, Vector b) {
        return a.subtract(b).lengthSquared();
    }

    public static Vector getHorizontalVector(Vector v) {
        v.setY(0);
        return v;
    }

    public static Vector getVerticalVector(Vector v) {
        v.setX(0);
        v.setZ(0);
        return v;
    }

    public static String serializeLocation(Location location) {
        int X = (int)location.getX();
        int Y = (int)location.getY();
        int Z = (int)location.getZ();
        int P = (int)location.getPitch();
        int Yaw = (int)location.getYaw();
        return location.getWorld().getName() + "," + X + "," + Y + "," + Z + "," + P + "," + Yaw;
    }

    public static Location deserializeLocation(String string) {
        String[] parts = string.split(",");
        World world = Bukkit.getServer().getWorld(parts[0]);
        Double LX = Double.parseDouble(parts[1]);
        Double LY = Double.parseDouble(parts[2]);
        Double LZ = Double.parseDouble(parts[3]);
        Float P = Float.valueOf(Float.parseFloat(parts[4]));
        Float Y = Float.valueOf(Float.parseFloat(parts[5]));
        Location result = new Location(world, LX.doubleValue(), LY.doubleValue(), LZ.doubleValue());
        result.setPitch(P.floatValue());
        result.setYaw(Y.floatValue());
        return result;
    }

    public static float averageFloat(List<Float> list) {
        float avg = 0.0f;
        for (float value : list) {
            avg += value;
        }
        if (list.size() > 0) {
            return avg / (float)list.size();
        }
        return 0.0f;
    }

    public static long averageLong(List<Long> list) {
        long avg = 0L;
        for (long value : list) {
            avg += value;
        }
        if (list.size() > 0) {
            return avg / (long)list.size();
        }
        return 0L;
    }

    public static double averageDouble(List<Double> list) {
        Double add = 0.0;
        for (Double listlist : list) {
            add = add + listlist;
        }
        return add / (double)list.size();
    }

    public static double averageIDouble(List<Integer> list) {
        Double add = 0.0;
        for (Integer listlist : list) {
            add = add + (double)listlist.intValue();
        }
        return add / (double)list.size();
    }

    public static int getMode(int[] a) {
        int maxValue = 0;
        int maxCount = 0;
        for (int i = 0; i < a.length; ++i) {
            int count = 0;
            for (int j = 0; j < a.length; ++j) {
                if (a[j] != a[i]) continue;
                ++count;
            }
            if (count <= maxCount) continue;
            maxCount = count;
            maxValue = a[i];
        }
        return maxValue;
    }

    public static long getRange(List<Long> a) {
        int size = a.size();
        long first = a.get(0);
        long last = a.get(size - 1);
        return last - first;
    }

    public static double getDistanceBetweenAngles360(double alpha, double beta) {
        double abs = Math.abs(alpha % 360.0 - beta % 360.0);
        return Math.abs(Math.min(360.0 - abs, abs));
    }

    public static int pingFormula(long ping) {
        return (int)Math.ceil((double)ping / 50.0);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBoolean(String str) {
        switch (str.toLowerCase()) {
            case "true": 
            case "false": {
                return true;
            }
        }
        return false;
    }

    public static double getMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}

