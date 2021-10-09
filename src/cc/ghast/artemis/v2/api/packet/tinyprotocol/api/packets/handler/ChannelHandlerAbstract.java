/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflections;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedField;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.bukkit.entity.Player;

public abstract class ChannelHandlerAbstract {
    static final WrappedField networkManagerField = Reflections.getNMSClass("PlayerConnection").getFieldByName("networkManager");
    static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");
    final Executor addChannelHandlerExecutor = Executors.newSingleThreadExecutor();
    final Executor removeChannelHandlerExecutor = Executors.newSingleThreadExecutor();
    final String handlerKey;
    final String playerKey;

    ChannelHandlerAbstract() {
        this.handlerKey = "packet_handler";
        this.playerKey = "trijes";
    }

    public abstract void addChannel(Player var1);

    public abstract void removeChannel(Player var1);

    public abstract void sendPacket(Player var1, Object var2);
}

