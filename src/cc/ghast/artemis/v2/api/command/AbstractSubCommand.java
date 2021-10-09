/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package cc.ghast.artemis.v2.api.command;

import cc.ghast.artemis.v2.api.command.SubCommand;
import java.lang.annotation.Annotation;
import org.bukkit.command.CommandSender;

public abstract class AbstractSubCommand {
    private String name = this.getClass().getAnnotation(SubCommand.class).name();
    private String permission = this.getClass().getAnnotation(SubCommand.class).permission();

    public abstract void run(CommandSender var1, String ... var2);

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }
}

