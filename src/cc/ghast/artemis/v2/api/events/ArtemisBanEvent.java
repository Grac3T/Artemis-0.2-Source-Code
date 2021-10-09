/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package cc.ghast.artemis.v2.api.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArtemisBanEvent
extends Event {
    private static HandlerList handlerList = new HandlerList();
    private OfflinePlayer player;

    public ArtemisBanEvent(OfflinePlayer player) {
        this.player = player;
    }

    public OfflinePlayer getPlayer() {
        return this.player;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}

