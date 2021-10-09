/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package cc.ghast.artemis.v2.api.command;

import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.Command;
import java.lang.annotation.Annotation;
import java.util.List;
import org.bukkit.command.CommandSender;

public abstract class AbstractCommand {
    private final String name = this.getClass().getAnnotation(Command.class).name();
    private final String permission = this.getClass().getAnnotation(Command.class).permission();
    private List<AbstractSubCommand> abstractSubCommands;

    public abstract void run(CommandSender var1, String ... var2);

    public abstract List<AbstractSubCommand> initSubCommands();

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<AbstractSubCommand> getAbstractSubCommands() {
        return this.abstractSubCommands;
    }

    public void setAbstractSubCommands(List<AbstractSubCommand> abstractSubCommands) {
        this.abstractSubCommands = abstractSubCommands;
    }
}

