/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.api.packet;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.packet.event.PacketReceiveEvent;
import cc.ghast.artemis.v2.api.packet.event.PacketSendEvent;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.ChannelInjector;
import cc.ghast.artemis.v2.listeners.EventAdapterListener;
import cc.ghast.artemis.v2.listeners.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TinyProtocolHandler {
    private static ChannelInjector instance;
    private static JavaPlugin plugin;
    private PacketListener packetListener;
    private static EventAdapterListener eventAdapterListener;

    public TinyProtocolHandler(JavaPlugin javaPlugin) {
        this.init(javaPlugin);
    }

    public synchronized void init(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        this.packetListener = new PacketListener();
        instance = new ChannelInjector();
        eventAdapterListener = new EventAdapterListener(Artemis.INSTANCE);
        Bukkit.getPluginManager().registerEvents((Listener)instance, (Plugin)javaPlugin);
    }

    public static Object onPacketOutAsync(Player sender, Object packet) {
        String name = packet.getClass().getName();
        int index = name.lastIndexOf(".");
        String packetName = name.substring(index + 1).replace("PacketPlayInUseItem", "PacketPlayInBlockPlace").replace("PacketPlayInFlying$PacketPlayInLook", "PacketPlayInLook").replace("PacketPlayInFlying$PacketPlayInPosition", "PacketPlayInPosition").replace("PacketPlayInFlying$PacketPlayInPositionLook", "PacketPlayInPositionLook");
        PacketReceiveEvent event = new PacketReceiveEvent(sender, packet, packetName);
        Artemis.INSTANCE.getApi().getTinyProtocolHandler().getPacketListener().onPacket(event);
        Bukkit.getPluginManager().callEvent((Event)event);
        return !event.isCancelled() ? event.getPacket() : null;
    }

    public static Object onPacketInAsync(Player sender, Object packet) {
        String name = packet.getClass().getName();
        int index = name.lastIndexOf(".");
        String packetName = name.substring(index + 1);
        PacketSendEvent event = new PacketSendEvent(sender, packet, packetName);
        Artemis.INSTANCE.getApi().getTinyProtocolHandler().getPacketListener().onSend(event);
        Bukkit.getPluginManager().callEvent((Event)event);
        return !event.isCancelled() ? event.getPacket() : null;
    }

    public static ChannelInjector getInstance() {
        return instance;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public PacketListener getPacketListener() {
        return this.packetListener;
    }
}

