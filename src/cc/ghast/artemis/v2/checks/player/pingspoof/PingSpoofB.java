/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.player.pingspoof;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInKeepAlivePacket;

@Check
public class PingSpoofB
extends AbstractCheck {
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInKeepAlivePacket) {
            try {
                int id = (int)((WrappedInKeepAlivePacket)packet).getTime();
                if (id == 10000 && this.verbose++ > 1) {
                    this.log(data, "Sigma");
                }
            }
            catch (Exception e) {
                return;
            }
        }
    }
}

