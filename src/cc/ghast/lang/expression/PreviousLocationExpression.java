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

@Expression(name="PreviousLocation", use={"PreviousX", "PreviousY", "PreviousZ", "PreviousPitch", "PreviousYaw"}, type=ClassType.NUMERIC)
public class PreviousLocationExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck customCheck) {
        switch (value.toLowerCase()) {
            case "PreviousX": {
                return data.movement.getLastLocation().getX();
            }
            case "PreviousY": {
                return data.movement.getLastLocation().getY();
            }
            case "PreviousZ": {
                return data.movement.getLastLocation().getZ();
            }
            case "PreviousPitch": {
                return Float.valueOf(data.movement.getLastLocation().getPitch());
            }
            case "PreviousYaw": {
                return Float.valueOf(data.movement.getLastLocation().getYaw());
            }
        }
        return null;
    }
}

