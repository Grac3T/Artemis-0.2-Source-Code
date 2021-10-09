/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler.ChannelHandler1_7;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler.ChannelHandler1_8;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler.ChannelHandlerAbstract;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflections;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChannelInjector
implements Listener {
    private ChannelHandlerAbstract channel = Reflections.classExists("net.minecraft.util.io.netty.channel.Channel") ? new ChannelHandler1_8() : new ChannelHandler1_7();

    @EventHandler(priority=EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        this.channel.addChannel(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.channel.removeChannel(event.getPlayer());
    }

    public ChannelHandlerAbstract getChannel() {
        return this.channel;
    }
}

