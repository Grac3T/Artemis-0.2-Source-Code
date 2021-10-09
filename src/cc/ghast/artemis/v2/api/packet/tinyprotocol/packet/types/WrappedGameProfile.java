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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.ReflectionsUtil;
import java.util.UUID;
import org.bukkit.entity.Player;

public class WrappedGameProfile
extends NMSObject {
    private static final String type = NMSObject.Type.GAMEPROFILE;
    private static FieldAccessor<UUID> fieldId = WrappedGameProfile.fetchField(type, UUID.class, 0);
    private static FieldAccessor<String> fieldName = WrappedGameProfile.fetchField(type, String.class, 0);
    private static FieldAccessor<?> fieldPropertyMap = WrappedGameProfile.fetchField(type, Reflection.getClass(NMSObject.Type.PROPERTYMAP), 0);
    public UUID id;
    public String name;
    public Object propertyMap;

    public WrappedGameProfile(Object type) {
        super(type);
    }

    public WrappedGameProfile(Player player) {
        Object entityPlayer = ReflectionsUtil.getEntityPlayer(player);
        FieldAccessor gameProfileAcessor = WrappedGameProfile.fetchField("EntityHuman", Reflection.NMS_PREFIX + type, 0);
        this.setObject(this.fetch(gameProfileAcessor));
        this.id = fieldId.get(this.getObject());
        this.name = fieldName.get(this.getObject());
        this.propertyMap = fieldPropertyMap.get(this.getObject());
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = fieldId.get(this.getObject());
        this.name = fieldName.get(this.getObject());
        this.propertyMap = fieldPropertyMap.get(this.getObject());
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Object getPropertyMap() {
        return this.propertyMap;
    }
}

