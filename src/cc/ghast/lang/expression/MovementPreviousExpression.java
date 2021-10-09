/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="PreviousMovement", use={"LastDelayedMovePacket", "LastMovePacket", "LastJump", "LastMoveCancel", "LastOnGround", "LastOnIce", "LastSlime"}, type=ClassType.NUMERIC)
public class MovementPreviousExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck check) {
        switch (value.toLowerCase()) {
            case "lastdelayedmovepacket": {
                return data.movement.getLastDelayedMovePacket();
            }
            case "lastmovepacket": {
                return data.movement.getLastMovePacket();
            }
            case "lastjump": {
                return data.movement.getLastJump();
            }
            case "lastmovecancel": {
                return data.movement.getLastMoveCancel();
            }
            case "lastonground": {
                return data.movement.getLastOnGround();
            }
            case "lastonice": {
                return data.movement.getLastOnIce();
            }
            case "lastslime": {
                return data.movement.getLastSlime();
            }
        }
        return null;
    }
}

