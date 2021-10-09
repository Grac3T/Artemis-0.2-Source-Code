/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import org.bukkit.entity.Player;

public class WrappedInAbilitiesPacket
extends NMSObject {
    private static final String packet = "PacketPlayInAbilities";
    private static FieldAccessor<Boolean> invulnerableField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Boolean.TYPE, 0);
    private static FieldAccessor<Boolean> flyingField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Boolean.TYPE, 1);
    private static FieldAccessor<Boolean> allowedFlightField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Boolean.TYPE, 2);
    private static FieldAccessor<Boolean> creativeModeField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Boolean.TYPE, 3);
    private static FieldAccessor<Float> flySpeedField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Float.TYPE, 0);
    private static FieldAccessor<Float> walkSpeedField = WrappedInAbilitiesPacket.fetchField("PacketPlayInAbilities", Float.TYPE, 1);
    private boolean invulnerable;
    private boolean flying;
    private boolean allowedFlight;
    private boolean creativeMode;
    private float flySpeed;
    private float walkSpeed;

    public WrappedInAbilitiesPacket(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.invulnerable = this.fetch(invulnerableField);
        this.flying = this.fetch(flyingField);
        this.allowedFlight = this.fetch(allowedFlightField);
        this.creativeMode = this.fetch(creativeModeField);
        this.flySpeed = this.fetch(flySpeedField).floatValue();
        this.walkSpeed = this.fetch(walkSpeedField).floatValue();
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isAllowedFlight() {
        return this.allowedFlight;
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }
}

