/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflections;

public enum WrappedEnumGameMode {
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    int f;
    String g;

    private WrappedEnumGameMode(int var3, String var4) {
        this.f = var3;
        this.g = var4;
    }

    public int getId() {
        return this.f;
    }

    public String b() {
        return this.g;
    }

    public boolean c() {
        return this == ADVENTURE || this == SPECTATOR;
    }

    public boolean d() {
        return this == CREATIVE;
    }

    public boolean e() {
        return this == SURVIVAL || this == ADVENTURE;
    }

    public static WrappedEnumGameMode getById(int var0) {
        for (WrappedEnumGameMode var4 : WrappedEnumGameMode.values()) {
            if (var4.f != var0) continue;
            return var4;
        }
        return SURVIVAL;
    }

    public static WrappedEnumGameMode getByName(String name) {
        for (WrappedEnumGameMode var : WrappedEnumGameMode.values()) {
            if (!var.name().equals(name)) continue;
            return var;
        }
        return SURVIVAL;
    }

    public Object getObject(WrappedEnumGameMode gamemode) {
        return Reflections.getNMSClass("EnumGameMode").getEnum(gamemode.name());
    }

    public Object getObject() {
        return this.getObject(WrappedEnumGameMode.getById(this.f));
    }

    public static WrappedEnumGameMode fromObject(Enum var) {
        return WrappedEnumGameMode.getByName(var.name());
    }
}

