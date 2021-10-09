/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.out;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflections;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ReflectionsUtil;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedClass;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedConstructor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedMethod;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedEnumGameMode;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedEnumPlayerInfoAction;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedGameProfile;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedPlayerInfoData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.entity.Player;

public class WrappedOutPlayerInfo
extends NMSObject {
    private static String packet = "PacketPlayOutPlayerInfo";
    private static FieldAccessor<List> playerInfoListAccessor;
    private static FieldAccessor<Enum> actionAcessorEnum;
    private static FieldAccessor<Integer> actionAcessorInteger;
    private static FieldAccessor<Integer> gamemodeAccessor;
    private static FieldAccessor<Object> profileAcessor;
    private static FieldAccessor<Integer> pingAcessor;
    private List<WrappedPlayerInfoData> playerInfo = new ArrayList<WrappedPlayerInfoData>();
    private WrappedEnumPlayerInfoAction action;

    public WrappedOutPlayerInfo(Object object, Player player) {
        super(object, player);
    }

    public WrappedOutPlayerInfo(WrappedEnumPlayerInfoAction action, Player ... players) {
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            this.setPacket(packet, Reflections.getNMSClass("PacketPlayOutPlayerInfo.EnumPlayerInfoAction").getEnum(action.name()), Arrays.stream(players).map(ReflectionsUtil::getEntityPlayer).collect(Collectors.toList()));
        } else {
            WrappedClass outPlayerInfo = Reflections.getNMSClass(packet);
            Object packet = outPlayerInfo.getConstructor().newInstance();
            outPlayerInfo.getMethod(action.legacyMethodName, ReflectionsUtil.EntityPlayer).invoke(packet, ReflectionsUtil.getEntityPlayer(players[0]));
            this.setObject(packet);
        }
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            playerInfoListAccessor = WrappedOutPlayerInfo.fetchField(packet, List.class, 0);
            actionAcessorEnum = WrappedOutPlayerInfo.fetchField(packet, Enum.class, 0);
            List list = this.fetch(playerInfoListAccessor);
            for (Object object : list) {
                this.playerInfo.add(new WrappedPlayerInfoData(object));
            }
            this.action = WrappedEnumPlayerInfoAction.valueOf(this.fetch(actionAcessorEnum).name());
        } else {
            actionAcessorInteger = WrappedOutPlayerInfo.fetchField(packet, Integer.class, 5);
            profileAcessor = WrappedOutPlayerInfo.fetchFieldByName(packet, "player", Object.class);
            gamemodeAccessor = WrappedOutPlayerInfo.fetchField(packet, Integer.class, 6);
            pingAcessor = WrappedOutPlayerInfo.fetchField(packet, Integer.class, 7);
            this.action = WrappedEnumPlayerInfoAction.values()[this.fetch(actionAcessorInteger)];
            WrappedGameProfile profile = new WrappedGameProfile(this.fetch(profileAcessor));
            WrappedEnumGameMode gamemode = WrappedEnumGameMode.getById(this.fetch(gamemodeAccessor));
            int ping = this.fetch(pingAcessor);
            this.playerInfo.add(new WrappedPlayerInfoData(profile, gamemode, ping));
        }
    }
}

