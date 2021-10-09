/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="PreviousUser", use={"LastFlyingPacket", "LastKeepAlive", "LastLag", "lastPlace", "LastUnderBlock", "TimeOnServer", "LastSentKeepAlive", "LastDig"}, type=ClassType.NUMERIC)
public class UserPreviousExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck check) {
        switch (value.toLowerCase()) {
            case "lastflyingpacket": {
                return data.user.getLastFlyingPacket();
            }
            case "lastkeepalive": {
                return data.user.getLastKeepAlive();
            }
            case "lastlag": {
                return data.user.getLastLag();
            }
            case "lastplace": {
                return data.user.getLastPlace();
            }
            case "lastunderblock": {
                return data.user.getLastUnderBlock();
            }
            case "timeonserver": {
                return data.user.getLongInTimePassed();
            }
            case "lastsentkeepalive": {
                return data.user.getLastSentKeepAlive();
            }
            case "lastdig": {
                return data.user.getLastDig();
            }
        }
        return null;
    }
}

