/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package cc.ghast.artemis.v2.api.check;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.algorithm.BanManager;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.check.annotations.Special;
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.api.data.Verbose;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.theme.AbstractTheme;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.managers.ThemeManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class AbstractCheck {
    private Check check = this.getClass().getAnnotation(Check.class);
    private Type type = Arrays.stream(Type.values()).filter(type -> this.getClass().getSimpleName().toUpperCase().contains(type.name())).findFirst().orElse(Type.UNKNOWN);
    private String var = this.getClass().getSimpleName().toUpperCase().replace(this.type.name(), "");
    private int maxvbs = this.getVbs();
    private final boolean experimental = this.getClass().isAnnotationPresent(Experimental.class);
    private final boolean special = this.getClass().isAnnotationPresent(Special.class);

    public void log(PlayerData data, int violation, String ... args) {
        this.runTask(() -> this.verbose(data, violation, args));
    }

    public void log(PlayerData data, String ... args) {
        this.runTask(() -> this.verbose(data, 1, args));
    }

    public void verbose(PlayerData data, int violation, String ... args) {
        if (!ConfigManager.getChecks().getBoolean(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var.toUpperCase() + ".enabled")) {
            return;
        }
        if (Arrays.stream(this.check.invalidVersions()).anyMatch(vr -> data.getVersion().isOrAbove((ProtocolVersion)((Object)vr)))) {
            return;
        }
        String baseMsg = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getVerboseMsg().replace("%user%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%type%", this.var).replace("%ids%", args.length > 0 ? args[0] : " ").replace("%vl%", this.experimental ? "EXPERIMENTAL" : Integer.toString(data.getVerboses(this)));
        Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
            PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
            if (this.experimental ? targetData.staff.getStaffAlert().isHighEnough(StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE) : targetData.staff.getStaffAlert().isHighEnough(StaffEnums.StaffAlerts.VERBOSE) && targetData.getPlayer().hasPermission("artemis.verbose")) {
                player.sendMessage(baseMsg);
            }
        });
        if (this.experimental) {
            return;
        }
        data.addVerbose(this, violation);
        if (data.getVerboses(this) >= this.maxvbs) {
            this.violate(data, args);
            if (!this.special) {
                data.user.getVerboses().remove(this);
            }
        }
    }

    public void violate(PlayerData data, String ... args) {
        if (!ConfigManager.getChecks().getBoolean(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var.toUpperCase() + ".enabled")) {
            return;
        }
        String baseMsg = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getViolationMsg().replace("%user%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%type%", this.var).replace("%vl%", this.experimental ? "EXPERIMENTAL" : Integer.toString(data.getVerboses(this)).replace("%ids%", args.length > 0 ? args[0] : ""));
        Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
            PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
            if (this.experimental ? targetData.staff.getStaffAlert().isHighEnough(StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE) : targetData.staff.getStaffAlert().isHighEnough(StaffEnums.StaffAlerts.VERBOSE) && targetData.getPlayer().hasPermission("artemis.alert")) {
                player.sendMessage(baseMsg);
            }
        });
        data.addViolation(this);
        if (data.getViolations(this) >= ConfigManager.getChecks().getInt(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".max-vls") && this.isBannable()) {
            Artemis.INSTANCE.getApi().getBanManager().addProfile(data);
        }
    }

    public void debug(PlayerData data, String ... args) {
        if (ConfigManager.getSettings().getBoolean("general.debugger")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
                if (player.hasPermission("artemis.debug") && targetData.isDebug(this)) {
                    String msg = Chat.translate("&7[&6&lDEBUG&7]&f &6TerminalSin &f&l-> &6%check% &f&l->&r %identifiers%").replace("%player%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%violation%", Integer.toString(data.getViolations(this))).replace("%type%", this.var);
                    if (args.length != 0) {
                        msg = msg.replace("%identifiers%", args[0]);
                    }
                    player.sendMessage(msg);
                }
            });
            if (data.isLogDebug(this)) {
                data.staff.getLog().add("[" + this.type.name() + this.var + "] " + args[0]);
            }
        }
    }

    private void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask((Plugin)Artemis.INSTANCE.getPlugin(), runnable);
    }

    private int getVbs() {
        try {
            return this.getClass().getAnnotation(Check.class).maxVls();
        }
        catch (NullPointerException e) {
            return 15;
        }
    }

    public boolean isBannable() {
        try {
            return ConfigManager.getChecks().getBoolean(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".bannable");
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public void handle(PlayerData data, NMSObject packet) {
    }

    public void handle(PlayerData data, BlockBreakEvent blockBreakEvent) {
    }

    public void handle(PlayerData data, BlockPlaceEvent blockPlaceEvent) {
    }

    public void handle(PlayerData data, PlayerMoveEvent playerMoveEvent) {
    }

    public void handle(PlayerData data, InventoryClickEvent inventoryClickEvent) {
    }

    public Check getCheck() {
        return this.check;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getVar() {
        return this.var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getMaxvbs() {
        return this.maxvbs;
    }

    public boolean isExperimental() {
        return this.experimental;
    }

    public boolean isSpecial() {
        return this.special;
    }
}

