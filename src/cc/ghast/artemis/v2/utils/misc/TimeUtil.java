/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.misc;

import cc.ghast.artemis.v2.utils.MathUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    public static boolean hasExpired(long timestamp, long seconds) {
        return System.currentTimeMillis() - timestamp > TimeUnit.SECONDS.toMillis(seconds);
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static long nowlong() {
        return System.currentTimeMillis();
    }

    public static String when(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return sdf.format(time);
    }

    public static long a(String a) {
        if (a.endsWith("s")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 1000L;
        }
        if (a.endsWith("m")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 60000L;
        }
        if (a.endsWith("h")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 3600000L;
        }
        if (a.endsWith("d")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 86400000L;
        }
        if (a.endsWith("m")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 2592000000L;
        }
        if (a.endsWith("y")) {
            return Long.valueOf(a.substring(0, a.length() - 1)) * 31104000000L;
        }
        return -1L;
    }

    public static String date() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DAY);
        return sdf.format(cal.getTime());
    }

    public static String getTime(int time) {
        Date timeDiff = new Date();
        timeDiff.setTime(time * 1000);
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        return timeFormat.format(timeDiff);
    }

    public static String since(long epoch) {
        return "Took " + TimeUtil.convertString(System.currentTimeMillis() - epoch, 1, TimeUnits.FIT) + ".";
    }

    public static double convert(long time, int trim, TimeUnits type) {
        if (type == TimeUnits.FIT) {
            type = time < 60000L ? TimeUnits.SECONDS : (time < 3600000L ? TimeUnits.MINUTES : (time < 86400000L ? TimeUnits.HOURS : TimeUnits.DAYS));
        }
        if (type == TimeUnits.DAYS) {
            return MathUtil.trim(trim, (double)time / 8.64E7);
        }
        if (type == TimeUnits.HOURS) {
            return MathUtil.trim(trim, (double)time / 3600000.0);
        }
        if (type == TimeUnits.MINUTES) {
            return MathUtil.trim(trim, (double)time / 60000.0);
        }
        if (type == TimeUnits.SECONDS) {
            return MathUtil.trim(trim, (double)time / 1000.0);
        }
        return MathUtil.trim(trim, time);
    }

    public static String MakeStr(long time) {
        return TimeUtil.convertString(time, 1, TimeUnits.FIT);
    }

    public static String MakeStr(long time, int trim) {
        return TimeUtil.convertString(time, trim, TimeUnits.FIT);
    }

    public static String convertString(long time, int trim, TimeUnits type) {
        if (time == -1L) {
            return "Permanent";
        }
        if (type == TimeUnits.FIT) {
            type = time < 60000L ? TimeUnits.SECONDS : (time < 3600000L ? TimeUnits.MINUTES : (time < 86400000L ? TimeUnits.HOURS : TimeUnits.DAYS));
        }
        if (type == TimeUnits.DAYS) {
            return MathUtil.trim(trim, (double)time / 8.64E7) + " Days";
        }
        if (type == TimeUnits.HOURS) {
            return MathUtil.trim(trim, (double)time / 3600000.0) + " Hours";
        }
        if (type == TimeUnits.MINUTES) {
            return MathUtil.trim(trim, (double)time / 60000.0) + " Minutes";
        }
        if (type == TimeUnits.SECONDS) {
            return MathUtil.trim(trim, (double)time / 1000.0) + " Seconds";
        }
        return MathUtil.trim(trim, time) + " Milliseconds";
    }

    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    public static long elapsed(long starttime) {
        return System.currentTimeMillis() - starttime;
    }

    public static long left(long start, long required) {
        return required + start - System.currentTimeMillis();
    }

    public static enum TimeUnits {
        FIT,
        DAYS,
        HOURS,
        MINUTES,
        SECONDS,
        MILLISECONDS;

    }

}

