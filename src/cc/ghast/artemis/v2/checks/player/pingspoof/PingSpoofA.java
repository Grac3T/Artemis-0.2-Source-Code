/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.player.pingspoof;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;

@Check
public class PingSpoofA
extends AbstractCheck {
    private int vl;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (data.user.getLastKeepAlive() == 0L || data.user.getLastSentKeepAlive() < 500L) {
            return;
        }
        long one = System.currentTimeMillis() - data.user.getLastKeepAlive();
        long two = System.currentTimeMillis() - data.user.getLastSentKeepAlive();
        int n = this.vl = one > 7000L && two < 3000L ? (this.vl = this.vl + 3) : (this.vl = this.vl - 20);
        if (this.vl > 5) {
            this.log(data, "vl=" + this.vl);
            this.vl = 0;
        }
    }
}

