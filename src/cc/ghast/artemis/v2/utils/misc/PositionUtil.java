/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.misc;

import cc.ghast.artemis.v2.utils.location.Position;
import java.util.List;

public class PositionUtil {
    public static Position getNearestPost(List<Position> positions, long time) {
        long nearest = Long.MAX_VALUE;
        Position near = null;
        for (Position position : positions) {
            if (Math.abs(position.getTimestamp() - time) >= nearest) continue;
            near = position;
            nearest = Math.abs(position.getTimestamp() - time);
        }
        return near;
    }

    public static Position getNearestPost(long time, Position ... positions) {
        long nearest = Long.MAX_VALUE;
        Position near = null;
        for (Position position : positions) {
            if (Math.abs(position.getTimestamp() - time) >= nearest) continue;
            near = position;
            nearest = Math.abs(position.getTimestamp() - time);
        }
        return near;
    }
}

