/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelDuplexHandler
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelPipeline
 *  io.netty.channel.ChannelPromise
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler;

import cc.ghast.artemis.v2.api.packet.TinyProtocolHandler;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.handler.ChannelHandlerAbstract;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflections;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ReflectionsUtil;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedField;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.Executor;
import org.bukkit.entity.Player;

public class ChannelHandler1_7
extends ChannelHandlerAbstract {
    @Override
    public void addChannel(Player player) {
        Channel channel = this.getChannel(player);
        this.addChannelHandlerExecutor.execute(() -> {
            if (channel != null && channel.pipeline().get(this.playerKey) == null) {
                channel.pipeline().addBefore(this.handlerKey, this.playerKey, (io.netty.channel.ChannelHandler)new ChannelHandler(player, this));
            }
        });
    }

    @Override
    public void removeChannel(Player player) {
        Channel channel = this.getChannel(player);
        this.removeChannelHandlerExecutor.execute(() -> {
            if (channel != null && channel.pipeline().get(this.playerKey) != null) {
                channel.pipeline().remove(this.playerKey);
            }
        });
    }

    private Channel getChannel(Player player) {
        return (Channel)Reflections.getNMSClass("NetworkManager").getFirstFieldByType(Channel.class).get(networkManagerField.get(playerConnectionField.get(ReflectionsUtil.getEntityPlayer(player))));
    }

    @Override
    public void sendPacket(Player player, Object packet) {
        this.getChannel(player).pipeline().writeAndFlush(packet);
    }

    private static class ChannelHandler
    extends ChannelDuplexHandler {
        private final Player player;
        private final ChannelHandlerAbstract channelHandlerAbstract;

        ChannelHandler(Player player, ChannelHandlerAbstract channelHandlerAbstract) {
            this.player = player;
            this.channelHandlerAbstract = channelHandlerAbstract;
        }

        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Object packet = TinyProtocolHandler.onPacketInAsync(this.player, msg);
            if (packet != null) {
                super.write(ctx, packet, promise);
            }
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Object packet = TinyProtocolHandler.onPacketOutAsync(this.player, msg);
            if (packet != null) {
                super.channelRead(ctx, packet);
            }
        }
    }

}

