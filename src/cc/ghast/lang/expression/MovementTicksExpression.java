/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="MovementTicks", use={"TeleportTicks", "RespawnTicks", "SprintTicks", "StandTicks", "DeathTicks"}, type=ClassType.NUMERIC)
public class MovementTicksExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck check) {
        switch (value.toLowerCase()) {
            case "teleportticks": {
                return data.movement.getTeleportTicks();
            }
            case "respawnticks": {
                return data.movement.getRespawnTicks();
            }
            case "sprintticks": {
                return data.movement.getSprintTicks();
            }
            case "standticks": {
                return data.movement.getStandTicks();
            }
            case "deathticks": {
                return data.movement.getDeathTicks();
            }
        }
        return null;
    }
}

