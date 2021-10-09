/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="CurrentLocation", use={"CurrentX", "CurrentY", "CurrentZ", "CurrentPitch", "CurrentYaw"}, type=ClassType.NUMERIC)
public class CurrentLocationExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck customCheck) {
        switch (value.toLowerCase()) {
            case "CurrentX": {
                return data.movement.getLocation().getX();
            }
            case "CurrentY": {
                return data.movement.getLocation().getY();
            }
            case "CurrentZ": {
                return data.movement.getLocation().getZ();
            }
            case "CurrentPitch": {
                return Float.valueOf(data.movement.getLocation().getPitch());
            }
            case "CurrentYaw": {
                return Float.valueOf(data.movement.getLocation().getYaw());
            }
        }
        return null;
    }
}

