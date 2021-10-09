/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.utils.location.Position;

@Check(maxVls=1)
public class BadPacketsG
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Position from = data.getMovement().getLastLocation();
        Position to = data.getMovement().getLocation();
        if (from != null && to != null && (to.getX() == Double.MAX_VALUE || to.getX() == Double.MIN_VALUE || to.getZ() == Double.MAX_VALUE || to.getZ() == Double.MIN_VALUE || from.getX() == Double.MAX_VALUE || from.getX() == Double.MIN_VALUE || from.getZ() == Double.MIN_VALUE || from.getZ() == Double.MAX_VALUE)) {
            this.log(data, 1, new String[0]);
        }
    }
}

