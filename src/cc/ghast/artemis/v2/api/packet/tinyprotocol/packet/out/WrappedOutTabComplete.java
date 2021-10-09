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
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.entity.Player;

public class WrappedOutTabComplete
extends NMSObject {
    private static String packet = "PacketPlayOutTabComplete";
    private String[] result;
    private static FieldAccessor<String[]> arrayAccessor;
    private static FieldAccessor<Object> suggestsAccessor;
    private static FieldAccessor<List> suggestionListAccessor;
    private static FieldAccessor<String> suggestionStringAccessor;

    public WrappedOutTabComplete(Object object) {
        super(object);
    }

    public WrappedOutTabComplete(Object object, Player player) {
        super(object, player);
    }

    public WrappedOutTabComplete(String[] result) {
        this.setPacketArg(packet, result);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_13)) {
            this.result = this.fetch(arrayAccessor);
        } else {
            Object suggestions = this.fetch(suggestsAccessor);
            List suggestsList = suggestionListAccessor.get(suggestions);
            List<String> strings = (List<String>) suggestsList.stream().map(object -> suggestionStringAccessor.get(object)).collect(Collectors.toList());
            this.result = new String[strings.size()];
            for (int i = 0; i < strings.size(); ++i) {
                this.result[i] = (String)strings.get(i);
            }
        }
    }

    public String[] getResult() {
        return this.result;
    }

    static {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_13)) {
            arrayAccessor = WrappedOutTabComplete.fetchField(packet, String[].class, 0);
        } else {
            suggestsAccessor = WrappedOutTabComplete.fetchField(packet, Object.class, 1);
            suggestionListAccessor = WrappedOutTabComplete.fetchField(packet, List.class, 0);
            suggestionStringAccessor = WrappedOutTabComplete.fetchField(packet, String.class, 0);
        }
    }
}

