/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.condition.api.ICondition;

public class AbstractCachedCondition {
    private final String valid;
    private final PlayerData data;
    private final ICondition condition;

    public boolean isValid(AbstractCustomCheck customCheck, NMSObject packet) {
        return this.condition.value(this.valid, this.data, customCheck, packet);
    }

    public AbstractCachedCondition(String valid, PlayerData data, ICondition condition) {
        this.valid = valid;
        this.data = data;
        this.condition = condition;
    }

    public String getValid() {
        return this.valid;
    }

    public PlayerData getData() {
        return this.data;
    }

    public ICondition getCondition() {
        return this.condition;
    }
}

