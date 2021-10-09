/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.lang.check.CustomCheckLoadError;
import cc.ghast.lang.check.Variable;
import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.check.variable.AddExecution;
import cc.ghast.lang.check.variable.DecreaseExecution;
import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.check.variable.IncreaseExecution;
import cc.ghast.lang.check.variable.RemoveExecution;
import cc.ghast.lang.check.variable.SetExecution;
import cc.ghast.lang.check.variable.SwitchExecution;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.tree.AbstractBranch;
import cc.ghast.lang.tree.BType;
import cc.ghast.lang.tree.IBranch;
import cc.ghast.lang.tree.ITree;
import cc.ghast.lang.tree.Root;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileCustomCheck
extends AbstractCheck {
    public List<String> stringConditions = new ArrayList<String>();
    public Map<String, ?> values = new HashMap();
    public List<Variable> variables = new ArrayList<Variable>();
    public Map<Integer, AbstractBranch> branches = new HashMap<Integer, AbstractBranch>();

    public void init(File file) {
        try {
            String line;
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            boolean init = false;
            boolean settings = false;
            int branchNb = -1;
            ArrayList potential = new ArrayList();
            boolean achieved = false;
            Root root = null;
            Object lastBranch = null;
            Object lastLastBranch = null;
            Object previous = null;
            ITree previousPrevious = null;
            int previousSpace = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equalsIgnoreCase("init")) {
                    init = true;
                    settings = false;
                    continue;
                }
                if (line.contains("settings")) {
                    settings = true;
                    continue;
                }
                int size = this.getBeginningSpace(line) / 4;
                if (!init && settings) {
                    if (line.contains("name:")) {
                        String var = line.split(":")[1].replace(" ", "");
                        super.setType(Type.valueOf(var));
                        continue;
                    }
                    if (!line.contains("var:")) continue;
                    String var = line.split(":")[1].replace(" ", "");
                    super.setVar(var);
                    continue;
                }
                if (!init) continue;
                for (BType value : BType.values()) {
                    if (!Arrays.stream(value.scan).anyMatch(line::contains)) continue;
                    if (value.equals((Object)BType.IF)) {
                        if (size == 0 || root == null) {
                            root = new Root(line, value, new IBranch[0]);
                            previous = root;
                            previousPrevious = root;
                        } else {
                            Object branch = null;
                            if (previousSpace < size) {
                                branch = new AbstractBranch(line, root, value, (ITree)previous, new IBranch[0]);
                                if (lastBranch != null) {
                                    ((AbstractBranch)lastBranch).addBranch((IBranch)branch);
                                    lastLastBranch = lastBranch;
                                } else {
                                    root.addBranch((AbstractBranch)branch);
                                }
                                lastBranch = branch;
                                previous = branch;
                                previousPrevious = (ITree) previous;
                            } else if (previousSpace == size) {
                                branch = new AbstractBranch(line, root, value, previousPrevious, new IBranch[0]);
                                if (lastBranch != null) {
                                    ((AbstractBranch)lastBranch).addBranch((IBranch)branch);
                                    lastBranch = branch;
                                } else {
                                    root.addBranch((AbstractBranch)branch);
                                }
                                previous = branch;
                            } else {
                                if (size - 1 > 1) {
                                    branch = new AbstractBranch(line, root, value, this.branches.get(size - 1), new IBranch[0]);
                                    this.branches.get(size - 1).addBranch((IBranch)branch);
                                } else {
                                    branch = new AbstractBranch(line, root, value, null, new IBranch[0]);
                                    root.addBranch((AbstractBranch)branch);
                                }
                                previous = previousPrevious;
                                previousPrevious = previousPrevious.previous();
                            }
                            this.branches.put(size, (AbstractBranch)branch);
                        }
                    }
                    if (value == BType.VARIABLE) {
                        for (String s : value.scan) {
                            if (!line.contains(s)) continue;
                            for (ExecutionType v : ExecutionType.values()) {
                                String id = v.getId();
                                if (!line.contains(id)) continue;
                                String name = line.replace(s, "").split(Pattern.quote(id))[0].replace(" ", "");
                                String strvalue = line.replace(s, "").split(Pattern.quote(id))[1].replace(" ", "");
                                Variable prob = this.variables.stream().filter(variable -> name.contains(variable.getName())).findFirst().orElse(null);
                                if (prob != null) {
                                    switch (v) {
                                        case ADD: {
                                            prob.getConditions().put(this.getConditions(root, size), new AddExecution(strvalue));
                                            break;
                                        }
                                        case SET: {
                                            prob.getConditions().put(this.getConditions(root, size), new SetExecution(strvalue));
                                            break;
                                        }
                                        case REMOVE: {
                                            prob.getConditions().put(this.getConditions(root, size), new RemoveExecution(strvalue));
                                            break;
                                        }
                                        case DECREASE: {
                                            prob.getConditions().put(this.getConditions(root, size), new DecreaseExecution());
                                            break;
                                        }
                                        case SWITCH: {
                                            prob.getConditions().put(this.getConditions(root, size), new SwitchExecution());
                                            break;
                                        }
                                        case INCREASE: {
                                            prob.getConditions().put(this.getConditions(root, size), new IncreaseExecution());
                                        }
                                    }
                                    continue;
                                }
                                Variable var = null;
                                ArrayList<String> strConditions = new ArrayList<String>();
                                if (root != null) {
                                    strConditions.add(root.getValue());
                                }
                                for (int i = 0; i < size; ++i) {
                                    if (!this.branches.containsKey(i)) continue;
                                    strConditions.add(this.branches.get(i).getValue());
                                }
                                VariableExecution execution = null;
                                switch (v) {
                                    case SET: {
                                        execution = new SetExecution(strvalue);
                                        break;
                                    }
                                    case ADD: {
                                        execution = new AddExecution(strvalue);
                                        break;
                                    }
                                    case REMOVE: {
                                        execution = new RemoveExecution(strvalue);
                                        break;
                                    }
                                    case SWITCH: {
                                        execution = new SwitchExecution();
                                        break;
                                    }
                                    case DECREASE: {
                                        execution = new DecreaseExecution();
                                        break;
                                    }
                                    case INCREASE: {
                                        execution = new IncreaseExecution();
                                        break;
                                    }
                                    default: {
                                        throw new CustomCheckLoadError();
                                    }
                                }
                                switch (s.toUpperCase()) {
                                    case "STR": {
                                        var = new Variable(name.replace(" ", ""), strvalue, ClassType.STRING, strConditions, execution);
                                        break;
                                    }
                                    case "BOOL": {
                                        var = new Variable(name.replace(" ", ""), Boolean.parseBoolean(strvalue), ClassType.BOOLEAN, strConditions, execution);
                                        break;
                                    }
                                    case "INT": {
                                        var = new Variable(name.replace(" ", ""), Double.parseDouble(strvalue), ClassType.NUMERIC, strConditions, execution);
                                    }
                                }
                                this.variables.add(var);
                            }
                        }
                    }
                    if (value == BType.LOG) {
                        this.stringConditions.add(root.getValue());
                        for (int i = 0; i < size; ++i) {
                            if (!this.branches.containsKey(i)) continue;
                            this.stringConditions.add(this.branches.get(i).getValue());
                        }
                    }
                    previousSpace = this.getBeginningSpace(line);
                }
            }
        }
        catch (IOException e) {
            throw new CustomCheckLoadError();
        }
    }

    private int getBeginningSpace(String s) {
        if (s == null) {
            return 0;
        }
        int streak = 0;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                ++streak;
                continue;
            }
            return streak;
        }
        return streak;
    }

    private List<String> getConditions(Root root, int size) {
        ArrayList<String> strConditions = new ArrayList<String>();
        if (root != null) {
            strConditions.add(root.getValue());
            for (int i = 0; i < size; ++i) {
                if (!this.branches.containsKey(i)) continue;
                strConditions.add(this.branches.get(i).getValue());
            }
        }
        return strConditions;
    }

}

