/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.api;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import org.bukkit.entity.Player;

public class ArtemisAPI {
    public static boolean injectCheckToLoad(AbstractCheck check) {
        try {
            Artemis.INSTANCE.getApi().injectCheck(check);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PlayerData getPlayerData(Player player) {
        return Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
    }
}

