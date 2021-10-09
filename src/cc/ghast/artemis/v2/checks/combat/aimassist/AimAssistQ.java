/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
@Experimental
public class AimAssistQ
extends AbstractCheck {
    private float previousYawDif = 0.0f;
    private float previousYawChange = 0.0f;
    private int count;
    private int cooldown;
    private long quant;
    private float max;
    private float min;
    private float previousYawDelta;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
        if (TimeUtil.hasExpired(data.combat.getLastAttack(), 3L) || data.getPlayer().isSneaking()) {
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        float yawChange = Math.abs(to.getYaw() - from.getYaw());
        float yawDif = Math.abs(this.previousYawChange - yawChange);
        float pitchChange = MathUtil.getDistanceBetweenAngles(to.getPitch(), from.getPitch());
        float magicVal = pitchChange * 100.0f / this.previousYawDelta;
        if (yawDif > 100.0f) {
            this.cooldown = 15;
        }
        if ((double)Math.abs(this.max - yawDif) > 1.78784584) {
            this.cooldown = 5;
            this.max = 0.0f;
        }
        if (this.cooldown <= 0 && 60.0f < magicVal) {
            if (yawDif > 5.0f || yawChange == 0.0f) {
                this.count = 0;
            }
            if (yawChange < 0.2f && this.count > 0) {
                --this.count;
            }
            if (Float.toString(yawDif).length() != Float.toString(this.previousYawDif).length() && this.count > 0) {
                --this.count;
            }
            if (yawDif < 1.0f && yawChange > 0.6f) {
                this.count += 2;
            } else if (this.count > 0) {
                --this.count;
            }
            if (this.count > 12) {
                if (this.quant < 20L) {
                    this.log(data, new String[0]);
                } else {
                    this.quant = 0L;
                }
            }
            this.debug(data, "Yaw dif [" + yawDif + "] YawChange [" + yawChange + "] PrevYawChange" + this.previousYawChange + "] Count [" + this.count + "] Quant [" + this.quant + "]");
        }
        this.previousYawChange = yawChange;
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        if (this.max < yawDif) {
            this.max = yawDif;
        }
        if (this.min > yawDif) {
            this.min = yawDif;
        }
        this.previousYawDelta = pitchChange;
        ++this.quant;
    }
}

