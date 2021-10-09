/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedEnumGameMode;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedGameProfile;
import org.bukkit.entity.Player;

public class WrappedPlayerInfoData
extends NMSObject {
    private static String type = NMSObject.Type.PLAYERINFODATA;
    private static FieldAccessor<Enum> enumGamemodeAccessor = WrappedPlayerInfoData.fetchField(type, Enum.class, 0);
    private static FieldAccessor<Object> profileAcessor = WrappedPlayerInfoData.fetchFieldByName(type, "d", Object.class);
    private static FieldAccessor<Integer> pingAcessor = WrappedPlayerInfoData.fetchField(type, Integer.class, 0);
    private int ping;
    private WrappedEnumGameMode gameMode;
    private WrappedGameProfile gameProfile;
    private String username = "";

    public WrappedPlayerInfoData(Object object, Player player) {
        super(object, player);
    }

    public WrappedPlayerInfoData(Object object) {
        super(object);
        this.ping = this.fetch(pingAcessor);
        this.gameProfile = new WrappedGameProfile(this.fetch(profileAcessor));
        this.gameMode = WrappedEnumGameMode.fromObject(this.fetch(enumGamemodeAccessor));
    }

    public WrappedPlayerInfoData(WrappedGameProfile gameProfile, WrappedEnumGameMode gameMode, int ping) {
        this.ping = ping;
        this.gameProfile = gameProfile;
        this.gameMode = gameMode;
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        super.process(player, version);
        this.ping = this.fetch(pingAcessor);
        this.gameProfile = new WrappedGameProfile(this.fetch(profileAcessor));
        this.gameMode = WrappedEnumGameMode.fromObject(this.fetch(enumGamemodeAccessor));
        this.username = player.getName();
    }

    public WrappedPlayerInfoData() {
    }
}

