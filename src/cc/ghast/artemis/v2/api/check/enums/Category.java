/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.check.enums;

public enum Category {
    COMBAT("&7Combat checks are such an important ones", "&7They provide full protection against the", "&7most dangerous types of cheaters", "&7Fucked by"),
    MOVEMENT(""),
    PLAYER(""),
    EXPLOIT(""),
    MISC("");

    private String[] description;

    private Category(String ... s) {
        this.description = s;
    }

    public String[] getDescription() {
        return this.description;
    }
}

