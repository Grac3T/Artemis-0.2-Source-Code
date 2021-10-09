/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.managers;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.Verbose;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.api.saving.SaveData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerDataManager
extends Manager {
    private Map<Player, PlayerData> playerData = new ConcurrentHashMap<Player, PlayerData>();
    private Map<Player, SaveData> saveData = new ConcurrentHashMap<Player, SaveData>();
    private List<Player> staffList = new ArrayList<Player>();
    private List<Class<? extends AbstractCheck>> toInject = new ArrayList<Class<? extends AbstractCheck>>();

    @Override
    public void init() {
        Bukkit.getOnlinePlayers().forEach(player -> this.playerData.computeIfAbsent((Player)player, PlayerData::new));
        this.reduceVerboses();
    }

    public PlayerData getData(Player player) {
        return this.playerData.computeIfAbsent(player, PlayerData::new);
    }

    public void destroyData(Player player) {
        this.playerData.remove((Object)player);
    }

    public void reduceVerboses() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Artemis.INSTANCE.getPlugin(), () -> this.getPlayerData().forEach((p, data) -> data.user.getVerboses().forEach((c, vb) -> {
            if (vb.getCount() > 0) {
                data.user.getVerboses().put((AbstractCheck)c, new Verbose(vb.getTimestamp(), vb.getCount() - 1));
            }
        })), 60L, 60L);
    }

    @Override
    public void disinit() {
        this.playerData.clear();
    }

    public Map<Player, PlayerData> getPlayerData() {
        return this.playerData;
    }

    public Map<Player, SaveData> getSaveData() {
        return this.saveData;
    }

    public List<Player> getStaffList() {
        return this.staffList;
    }

    public List<Class<? extends AbstractCheck>> getToInject() {
        return this.toInject;
    }
}

