/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.theme;

import java.util.List;

public class AbstractTheme {
    private final String id;
    private final String mainColor;
    private final String secondaryColor;
    private final String bracketColor;
    private final String prefix;
    private final String violationMsg;
    private final String verboseMsg;
    private final String banMessage;
    private final List<String> helpMessage;

    public AbstractTheme(String id, String mainColor, String secondaryColor, String bracketColor, String prefix, String violationMsg, String verboseMsg, String banMessage, List<String> helpMessage) {
        this.id = id;
        this.mainColor = mainColor;
        this.secondaryColor = secondaryColor;
        this.bracketColor = bracketColor;
        this.prefix = prefix;
        this.violationMsg = violationMsg;
        this.verboseMsg = verboseMsg;
        this.banMessage = banMessage;
        this.helpMessage = helpMessage;
    }

    public String getId() {
        return this.id;
    }

    public String getMainColor() {
        return this.mainColor;
    }

    public String getSecondaryColor() {
        return this.secondaryColor;
    }

    public String getBracketColor() {
        return this.bracketColor;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getViolationMsg() {
        return this.violationMsg;
    }

    public String getVerboseMsg() {
        return this.verboseMsg;
    }

    public String getBanMessage() {
        return this.banMessage;
    }

    public List<String> getHelpMessage() {
        return this.helpMessage;
    }
}

