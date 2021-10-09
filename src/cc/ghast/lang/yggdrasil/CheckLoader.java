/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.yggdrasil;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

public class CheckLoader {
    public CheckLoader(PlayerData data) {
        File dir = new File(Artemis.INSTANCE.getPlugin().getDataFolder().getPath() + "/checks");
        File[] listing = dir.listFiles();
        if (listing != null) {
            for (File child : listing) {
                data.getCheckManager().getAbstractChecks().add(new AbstractCustomCheck(data, child){});
                System.out.println("Successfully added new custom check!");
            }
        }
    }

}

