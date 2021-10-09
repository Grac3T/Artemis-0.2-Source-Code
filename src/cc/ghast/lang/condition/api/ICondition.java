/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.lang.check.AbstractCustomCheck;

public interface ICondition {
    public boolean value(String var1, PlayerData var2, AbstractCustomCheck var3, NMSObject var4);
}

