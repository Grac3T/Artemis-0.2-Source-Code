/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ICondition;

@Condition(name="inLiquid", use={"inLiquid"})
public class InLiquidCondition
implements ICondition {
    @Override
    public boolean value(String value, PlayerData data, AbstractCustomCheck customCheck, NMSObject packet) {
        if (value.contains("!")) {
            return !data.user.isInLiquid();
        }
        return data.user.isInLiquid();
    }
}

