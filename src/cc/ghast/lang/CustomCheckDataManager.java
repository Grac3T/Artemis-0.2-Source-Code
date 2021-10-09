/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.yggdrasil.CheckLoader;
import java.util.ArrayList;
import java.util.List;

public class CustomCheckDataManager {
    private List<AbstractCustomCheck> checks = new ArrayList<AbstractCustomCheck>();

    public CustomCheckDataManager(PlayerData data) {
        new CheckLoader(data);
    }

    public List<AbstractCustomCheck> getChecks() {
        return this.checks;
    }
}

