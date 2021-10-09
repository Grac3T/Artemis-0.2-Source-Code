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
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.api.data.Verbose;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
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

public class AbstractExternalCheck
extends AbstractCheck {
    private Check check = this.getClass().getAnnotation(Check.class);
    private Type type;
    private String var;
    private boolean experimental;
    private int maxvls;
    private int maxvbs;

    public AbstractExternalCheck() {
        this.type = Arrays.stream(Type.values()).filter(type -> this.getClass().getSimpleName().toUpperCase().contains(type.name())).findFirst().orElse(Type.UNKNOWN);
        this.var = this.getClass().getSimpleName().toUpperCase().replace(this.type.name(), "");
        this.experimental = this.getClass().isAnnotationPresent(Experimental.class);
        this.maxvbs = this.getVbs();
        this.maxvls = 15;
    }

    public AbstractExternalCheck(Type type, String var, boolean experimental, int maxvls, int maxvbs) {
        this.type = type;
        this.var = var;
        this.experimental = experimental;
        this.maxvls = maxvls;
        this.maxvbs = maxvbs;
    }

    public AbstractExternalCheck(Type type, String var, boolean experimental) {
        this.type = type;
        this.var = var;
        this.experimental = experimental;
        this.maxvls = 15;
        this.maxvbs = 20;
    }

    @Override
    public void verbose(PlayerData data, int violation, String ... args) {
        if (Arrays.stream(this.check.invalidVersions()).anyMatch(vr -> data.getVersion().isOrAbove((ProtocolVersion)((Object)vr)))) {
            return;
        }
        String baseMsg = ConfigManager.getSettings().getString("general.prefix") + " " + ConfigManager.getSettings().getString("message.verbose");
        if (this.experimental) {
            Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
                PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
                if (player.hasPermission("artemis.verbose")) {
                    String msg = Chat.translate(baseMsg.replace("%player%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%type%", this.var).replace("%violation%", "EXPERIMENTAL"));
                    if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                        if (args.length > 0) {
                            player.sendMessage(msg.replace("%identifiers%", args[0]));
                        } else {
                            player.sendMessage(msg.replace("%identifiers%", ""));
                        }
                    }
                }
            });
        } else {
            data.addVerbose(this, violation);
            Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
                PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
                if (player.hasPermission("artemis.verbose")) {
                    String msg = Chat.translate(baseMsg.replace("%player%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%type%", this.var).replace("%violation%", Integer.toString(data.getVerboses(this))));
                    if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.VERBOSE) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                        if (args.length > 0) {
                            player.sendMessage(msg.replace("%identifiers%", args[0]));
                        } else {
                            player.sendMessage(msg.replace("%identifiers%", ""));
                        }
                    }
                }
            });
            if (data.getVerboses(this) >= this.maxvbs) {
                this.violate(data, args);
                data.user.getVerboses().remove(this);
            }
        }
    }

    @Override
    public void violate(PlayerData data, String ... args) {
        if (!this.experimental) {
            data.addViolation(this);
            Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
                PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
                if (player.hasPermission("artemis.alert")) {
                    String msg = Chat.translate(ConfigManager.getSettings().getString("general.prefix") + " " + ConfigManager.getSettings().getString("message.violation").replace("%player%", data.getPlayer().getName()).replace("%check%", this.type.name()).replace("%type%", this.var).replace("%violation%", Integer.toString(data.getViolations(this))));
                    if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.ALERTS) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.VERBOSE) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                        if (args.length > 0) {
                            player.sendMessage(msg.replace("%identifiers%", args[0]));
                        } else {
                            player.sendMessage(msg.replace("%identifiers%", ""));
                        }
                    }
                }
            });
            if (data.getViolations(this) >= ConfigManager.getChecks().getInt(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".max-vls")) {
                if (this.isBannable()) {
                    Artemis.INSTANCE.getApi().getBanManager().addProfile(data);
                }
                return;
            }
        }
    }

    @Override
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
        Bukkit.getScheduler().runTask(Artemis.INSTANCE.getPlugin(), runnable);
    }

    private int getVbs() {
        try {
            return this.getClass().getAnnotation(Check.class).maxVls();
        }
        catch (NullPointerException e) {
            return 15;
        }
    }

    @Override
    public boolean isBannable() {
        try {
            return ConfigManager.getChecks().getBoolean(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".bannable");
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public void handle(PlayerData data, NMSObject packet) {
    }

    @Override
    public void handle(PlayerData data, BlockBreakEvent blockBreakEvent) {
    }

    @Override
    public void handle(PlayerData data, BlockPlaceEvent blockPlaceEvent) {
    }

    @Override
    public void handle(PlayerData data, PlayerMoveEvent playerMoveEvent) {
    }

    @Override
    public void handle(PlayerData data, InventoryClickEvent inventoryClickEvent) {
    }

    @Override
    public Check getCheck() {
        return this.check;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getVar() {
        return this.var;
    }

    @Override
    public boolean isExperimental() {
        return this.experimental;
    }

    public int getMaxvls() {
        return this.maxvls;
    }

    @Override
    public int getMaxvbs() {
        return this.maxvbs;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void setVar(String var) {
        this.var = var;
    }

    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
    }

    public void setMaxvls(int maxvls) {
        this.maxvls = maxvls;
    }

    public void setMaxvbs(int maxvbs) {
        this.maxvbs = maxvbs;
    }
}

