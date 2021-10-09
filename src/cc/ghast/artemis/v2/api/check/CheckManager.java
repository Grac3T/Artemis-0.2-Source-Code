/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.check;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistE;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistF;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistG;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistH;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistI;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistK;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistL;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistM;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistN;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistO;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistP;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistQ;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistR;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistS;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistT;
import cc.ghast.artemis.v2.checks.combat.aimassist.AimAssistU;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerA;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerB;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerC;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerD;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerE;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerF;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerG;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerH;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerI;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerJ;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerK;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerL;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerL2;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerM;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerN;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerO;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerP;
import cc.ghast.artemis.v2.checks.combat.autoclicker.AutoClickerQ;
import cc.ghast.artemis.v2.checks.combat.hitbox.HitboxA;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraA;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraB;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraC;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraD;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraE;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraF;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraG;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraH;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraI;
import cc.ghast.artemis.v2.checks.combat.killaura.KillAuraJ;
import cc.ghast.artemis.v2.checks.combat.reach.ReachA;
import cc.ghast.artemis.v2.checks.combat.reach.ReachD;
import cc.ghast.artemis.v2.checks.combat.reach.ReachE;
import cc.ghast.artemis.v2.checks.combat.velocity.VelocityA;
import cc.ghast.artemis.v2.checks.combat.velocity.VelocityB;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsA;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsB;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsC;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsD;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsE;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsF;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsG;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsH;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsI;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsJ;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsK;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsL;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsM;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsN;
import cc.ghast.artemis.v2.checks.misc.badpackets.BadPacketsO;
import cc.ghast.artemis.v2.checks.movement.fly.FlyA;
import cc.ghast.artemis.v2.checks.movement.fly.FlyB;
import cc.ghast.artemis.v2.checks.movement.inventory.InventoryWalkA;
import cc.ghast.artemis.v2.checks.movement.jesus.JesusA;
import cc.ghast.artemis.v2.checks.movement.scaffold.ScaffoldA;
import cc.ghast.artemis.v2.checks.movement.speed.SpeedA;
import cc.ghast.artemis.v2.checks.movement.speed.SpeedB;
import cc.ghast.artemis.v2.checks.movement.speed.SpeedC;
import cc.ghast.artemis.v2.checks.movement.speed.SpeedD;
import cc.ghast.artemis.v2.checks.player.pingspoof.PingSpoofA;
import cc.ghast.artemis.v2.checks.player.pingspoof.PingSpoofB;
import cc.ghast.artemis.v2.checks.player.pingspoof.PingSpoofC;
import cc.ghast.artemis.v2.checks.player.timer.TimerA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CheckManager
extends Manager {
    private List<AbstractCheck> abstractChecks = new ArrayList<AbstractCheck>();

    public CheckManager() {
        this.init();
    }

    @Override
    public void init() {
        this.abstractChecks.addAll(Arrays.asList(new AimAssistE(), new AimAssistF(), new AimAssistG(), new AimAssistH(), new AimAssistI(), new AimAssistK(), new AimAssistL(), new AimAssistM(), new AimAssistN(), new AimAssistO(), new AimAssistP(), new AimAssistQ(), new AimAssistR(), new AimAssistS(), new AimAssistT(), new AimAssistU(), new KillAuraA(), new KillAuraB(), new KillAuraC(), new KillAuraD(), new KillAuraE(), new KillAuraF(), new KillAuraG(), new KillAuraH(), new KillAuraI(), new KillAuraJ(), new AutoClickerA(), new AutoClickerB(), new AutoClickerC(), new AutoClickerD(), new AutoClickerE(), new AutoClickerF(), new AutoClickerG(), new AutoClickerH(), new AutoClickerI(), new AutoClickerJ(), new AutoClickerK(), new AutoClickerL(), new AutoClickerL2(), new AutoClickerM(), new AutoClickerN(), new AutoClickerO(), new AutoClickerP(), new AutoClickerQ(), new SpeedA(), new SpeedB(), new SpeedC(), new SpeedD(), new JesusA(), new FlyA(Artemis.INSTANCE.getPlugin()), new FlyB(), new ScaffoldA(), new PingSpoofA(), new PingSpoofB(), new PingSpoofC(), new TimerA(), new VelocityA(), new VelocityB(), new ReachA(), new ReachD(), new ReachE(), new HitboxA(), new InventoryWalkA(), new BadPacketsA(), new BadPacketsB(), new BadPacketsC(), new BadPacketsD(), new BadPacketsE(), new BadPacketsF(), new BadPacketsG(), new BadPacketsH(), new BadPacketsI(), new BadPacketsJ(), new BadPacketsK(), new BadPacketsL(), new BadPacketsM(), new BadPacketsN(), new BadPacketsO()));
    }

    @Override
    public void disinit() {
        this.abstractChecks.clear();
    }

    public AbstractCheck getCheckByName(String name) {
        return this.abstractChecks.stream().filter(check -> (check.getType().name() + check.getVar()).equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addCheck(AbstractCheck check) {
        this.abstractChecks.add(check);
    }

    public List<AbstractCheck> getAbstractChecks() {
        return this.abstractChecks;
    }
}

