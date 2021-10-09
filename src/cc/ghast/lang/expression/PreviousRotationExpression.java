/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.location.Rotation;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="PreviousRotation", use={"PreviousPitch", "PreviousYaw"}, type=ClassType.NUMERIC)
public class PreviousRotationExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck customCheck) {
        switch (value.toLowerCase()) {
            case "PreviousPitch": {
                return Float.valueOf(data.movement.getLastRotation().getPitch());
            }
            case "PreviousYaw": {
                return Float.valueOf(data.movement.getLastRotation().getYaw());
            }
        }
        return null;
    }
}

