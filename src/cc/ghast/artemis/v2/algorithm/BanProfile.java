/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 */
package cc.ghast.artemis.v2.algorithm;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.data.Violation;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class BanProfile {
    private final OfflinePlayer player;
    private final UUID uuid;
    private final int totalFlags;
    private final LocalDateTime time;
    private final String ip;
    private final String username;
    private final boolean active;
    private final Map<AbstractCheck, Violation> flag;

    public OfflinePlayer getPlayer() {
        return this.player;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getTotalFlags() {
        return this.totalFlags;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getIp() {
        return this.ip;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isActive() {
        return this.active;
    }

    public Map<AbstractCheck, Violation> getFlag() {
        return this.flag;
    }

    public BanProfile(OfflinePlayer player, UUID uuid, int totalFlags, LocalDateTime time, String ip, String username, boolean active, Map<AbstractCheck, Violation> flag) {
        this.player = player;
        this.uuid = uuid;
        this.totalFlags = totalFlags;
        this.time = time;
        this.ip = ip;
        this.username = username;
        this.active = active;
        this.flag = flag;
    }
}

