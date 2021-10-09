/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package cc.ghast.lang.check;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.lang.ArtemisLang;
import cc.ghast.lang.ArtemisLangInstance;
import cc.ghast.lang.check.CachedVariable;
import cc.ghast.lang.check.CheckHandleException;
import cc.ghast.lang.check.CustomCheckLoadError;
import cc.ghast.lang.check.FileCustomCheck;
import cc.ghast.lang.check.Variable;
import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.condition.api.AbstractCachedCondition;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ConditionManager;
import cc.ghast.lang.condition.api.ICondition;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.ExpressionManager;
import cc.ghast.lang.expression.api.IExpression;
import cc.ghast.lang.processing.MathematicalProcessor;
import java.io.File;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class AbstractCustomCheck
extends FileCustomCheck {
    private final PlayerData data;
    private final List<AbstractCachedCondition> conditions = new ArrayList<AbstractCachedCondition>();
    private final Map<CachedVariable, Map<List<AbstractCachedCondition>, VariableExecution>> varConditions = new ConcurrentHashMap<CachedVariable, Map<List<AbstractCachedCondition>, VariableExecution>>();

    public AbstractCustomCheck(PlayerData data, File file) {
        this.data = data;
        super.init(file);
        this.loadConditions();
        this.loadVariabes();
    }

    @Override
    public void verbose(PlayerData data, int violation, String ... args) {
        String baseMsg = ConfigManager.getSettings().getString("general.prefix") + " " + ConfigManager.getSettings().getString("message.verbose");
        Bukkit.getOnlinePlayers().parallelStream().forEach(player -> {
            PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
            if (player.hasPermission("artemis.verbose")) {
                String msg = Chat.translate(baseMsg.replace("%player%", data.getPlayer().getName()).replace("%check%", this.getType().toString()).replace("%type%", this.getVar()).replace("%violation%", "EXPERIMENTAL"));
                if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                    if (args.length > 0) {
                        player.sendMessage(msg.replace("%identifiers%", args[0]));
                    } else {
                        player.sendMessage(msg.replace("%identifiers%", ""));
                    }
                }
            }
        });
    }

    @Override
    public void setVar(String var) {
        super.setVar(var);
    }

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        this.varConditions.forEach((name, listList) -> listList.forEach((list, exec) -> {
            if (list.size() == 0) {
                return;
            }
            for (AbstractCachedCondition cond : list) {
                if (cond.isValid(this, packet)) continue;
                return;
            }
            String execValue = exec.getValueToSet();
            String[] execParsed = execValue.split("(?=[+\\-/*^])");
            StringBuilder parser = new StringBuilder();
            for (String s : execParsed) {
                if (!MathUtil.isNumeric(s.replaceAll("[+\\-/*^\\s]", "")) && !MathUtil.isBoolean(s.replaceAll("[+\\-/*^\\s]", ""))) {
                    IExpression item = ArtemisLangInstance.INSTANCE.getApi().getExpressionManager().getExpressions().stream().filter(som -> Arrays.stream(som.getClass().getAnnotation(Expression.class).use()).anyMatch(s::contains) && som.getClass().getAnnotation(Expression.class).type().equals((Object)ClassType.NUMERIC)).findFirst().orElseThrow(CheckHandleException::new);
                    parser.append(item.value(s, data, this).toString() + " ");
                    continue;
                }
                parser.append(s + " ");
            }
            double result = new MathematicalProcessor().doTheShuntingYard(parser.toString());
            String value = Double.toString(result);
            name.setValue(exec.update(name.getValue(), result));
        }));
        boolean valid = true;
        for (AbstractCachedCondition condition : this.conditions) {
            if (condition.isValid(this, packet)) continue;
            valid = false;
        }
        if (valid) {
            this.log(data, new String[0]);
        }
    }

    public void loadConditions() {
        if (this.stringConditions.size() == 0) {
            return;
        }
        this.stringConditions.forEach(s -> {
            AbstractCachedCondition cachedCondition = new AbstractCachedCondition((String)s, this.data, ArtemisLangInstance.INSTANCE.getApi().getConditionManager().getConditions().stream().filter(c -> Arrays.stream(c.getClass().getAnnotation(Condition.class).use()).anyMatch(s::contains)).findFirst().orElseThrow(CustomCheckLoadError::new));
            this.conditions.add(cachedCondition);
            System.out.println(cachedCondition.getValid());
        });
    }

    public void loadVariabes() {
        if (this.variables.size() == 0) {
            return;
        }
        this.variables.forEach(v -> {
            ConcurrentHashMap conds = new ConcurrentHashMap();
            v.getConditions().forEach((list, exec) -> {
                ArrayList cached = new ArrayList();
                list.forEach(s -> {
                    AbstractCachedCondition cachedCondition = new AbstractCachedCondition((String)s, this.data, ArtemisLangInstance.INSTANCE.getApi().getConditionManager().getConditions().stream().filter(c -> Arrays.stream(c.getClass().getAnnotation(Condition.class).use()).anyMatch(s::contains)).findFirst().orElseThrow(CustomCheckLoadError::new));
                    cached.add(cachedCondition);
                });
                conds.put(cached, exec);
            });
            CachedVariable var = new CachedVariable(v.getName(), v.getValue());
            this.varConditions.put(var, conds);
        });
    }

    public Map<CachedVariable, Map<List<AbstractCachedCondition>, VariableExecution>> getVarConditions() {
        return this.varConditions;
    }

    private /* synthetic */ void lambda$verbose$1(String baseMsg, PlayerData data, String[] args, Player player) {
        PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        if (player.hasPermission("artemis.verbose")) {
            String msg = Chat.translate(baseMsg.replace("%player%", data.getPlayer().getName()).replace("%check%", "Unknown").replace("%type%", "A").replace("%violation%", Integer.toString(data.getVerboses(this))));
            if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.VERBOSE) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                if (args.length > 0) {
                    player.sendMessage(msg.replace("%identifiers%", args[0]));
                } else {
                    player.sendMessage(msg.replace("%identifiers%", ""));
                }
            }
        }
    }
}

