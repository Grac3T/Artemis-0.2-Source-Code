/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.theme;

public class ThemeNotFoundException
extends RuntimeException {
    public ThemeNotFoundException(String message) {
        super("Theme " + message + " was not found! Please make sure it is the correct name of the file!");
    }
}

