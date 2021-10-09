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
import org.bukkit.entity.Player;

public class WrappedOutAbilitiesPacket
extends NMSObject {
    private static final String packet = "PacketPlayOutAbilities";
    private static FieldAccessor<Boolean> invulnerableField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Boolean.TYPE, 0);
    private static FieldAccessor<Boolean> flyingField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Boolean.TYPE, 1);
    private static FieldAccessor<Boolean> allowedFlightField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Boolean.TYPE, 2);
    private static FieldAccessor<Boolean> creativeModeField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Boolean.TYPE, 3);
    private static FieldAccessor<Float> flySpeedField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Float.TYPE, 0);
    private static FieldAccessor<Float> walkSpeedField = WrappedOutAbilitiesPacket.fetchField("PacketPlayOutAbilities", Float.TYPE, 1);
    private boolean invulnerable;
    private boolean flying;
    private boolean allowedFlight;
    private boolean creativeMode;
    private float flySpeed;
    private float walkSpeed;

    public WrappedOutAbilitiesPacket(Object object, Player player) {
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

