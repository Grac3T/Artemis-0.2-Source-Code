/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.saving;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.saving.SaveData;
import java.util.List;

public abstract class AbstractStorage {
    public abstract void save(List<SaveData> var1, PlayerData var2);

    public abstract List<SaveData> load(PlayerData var1);
}

