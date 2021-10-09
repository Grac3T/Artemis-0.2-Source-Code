/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ICondition;

@Condition(name="onGround", use={"onGround"})
public class OnGroundCondition
implements ICondition {
    @Override
    public boolean value(String value, PlayerData data, AbstractCustomCheck abstractCustomCheck, NMSObject packet) {
        if (value.contains("!")) {
            return !data.user.isOnGround();
        }
        return data.user.isOnGround();
    }
}

