/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ReflectionsUtil;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.Vec3D;
import java.util.List;
import java.util.Objects;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class WrappedInUseEntityPacket
extends NMSObject {
    private static String packet = "PacketPlayInUseEntity";
    private static FieldAccessor<Integer> fieldId = WrappedInUseEntityPacket.fetchField(packet, Integer.TYPE, 0);
    private static FieldAccessor<Enum> fieldAction = WrappedInUseEntityPacket.fetchField(packet, Enum.class, 0);
    private static FieldAccessor<Object> fieldBody = WrappedInUseEntityPacket.fetchField(packet, Object.class, 0);
    public int id;
    public EnumEntityUseAction action;
    public Entity entity;
    public Vec3D body;

    public WrappedInUseEntityPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = Objects.requireNonNull(this.fetch(fieldId));
        Enum fieldAct = Objects.nonNull(this.fetch(fieldAction)) ? this.fetch(fieldAction) : null;
        this.action = fieldAct == null ? EnumEntityUseAction.INTERACT_AT : EnumEntityUseAction.valueOf(fieldAct.name());
        List<Entity> entities = ReflectionsUtil.getEntitiesInWorld(player.getWorld());
        for (Entity ent : entities) {
            if (this.id != ent.getEntityId()) continue;
            this.entity = ent;
            break;
        }
        if (this.action == EnumEntityUseAction.INTERACT_AT) {
            if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
                fieldBody = WrappedInUseEntityPacket.fetchField(packet, Object.class, 1);
            } else if (ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_9)) {
                fieldBody = WrappedInUseEntityPacket.fetchField(packet, Object.class, 2);
            }
            this.body = new Vec3D(this.fetch(fieldBody));
        } else {
            this.body = new Vec3D(0.0, 0.0, 0.0);
        }
        Object vec = this.fetch(fieldBody);
    }

    public int getId() {
        return this.id;
    }

    public EnumEntityUseAction getAction() {
        return this.action;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Vec3D getBody() {
        return this.body;
    }

    public static enum EnumEntityUseAction {
        INTERACT("INTERACT"),
        ATTACK("ATTACK"),
        INTERACT_AT("INTERACT_AT");

        private String name;

        private EnumEntityUseAction(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}

