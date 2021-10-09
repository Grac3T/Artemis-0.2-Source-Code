/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.check.enums;

import cc.ghast.artemis.v2.api.check.enums.Category;

public enum Type {
    KILLAURA(Category.COMBAT),
    AIMASSIST(Category.COMBAT),
    REACH(Category.COMBAT),
    AUTOCLICKER(Category.COMBAT),
    SPEED(Category.MOVEMENT),
    FLY(Category.MOVEMENT),
    BADPACKETS(Category.MISC),
    OMNISPRINT(Category.MOVEMENT),
    INVENTORYWALK(Category.MOVEMENT),
    NOFALL(Category.MOVEMENT),
    MAGIC(Category.MOVEMENT),
    SCAFFOLD(Category.MOVEMENT),
    PINGSPOOF(Category.EXPLOIT),
    TIMER(Category.PLAYER),
    VELOCITY(Category.COMBAT),
    JESUS(Category.MOVEMENT),
    UNKNOWN(Category.MISC);

    private Category category;

    private Type(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }
}

