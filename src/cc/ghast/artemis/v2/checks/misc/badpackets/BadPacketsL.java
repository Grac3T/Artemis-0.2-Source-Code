/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Check
public class BadPacketsL
extends AbstractCheck {
    private final BaseBlockPosition INVALID_POSITION = new BaseBlockPosition(0, 0, 0);

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        BaseBlockPosition blockPosition;
        if (packet instanceof WrappedInBlockPlacePacket && (blockPosition = ((WrappedInBlockPlacePacket)packet).getPosition()).getX() == this.INVALID_POSITION.getX() && blockPosition.getY() == this.INVALID_POSITION.getY() && blockPosition.getZ() == this.INVALID_POSITION.getZ() && data.getPlayer().getItemInHand().getType().isBlock() && !data.isLagging()) {
            this.log(data, new String[0]);
        }
    }
}

