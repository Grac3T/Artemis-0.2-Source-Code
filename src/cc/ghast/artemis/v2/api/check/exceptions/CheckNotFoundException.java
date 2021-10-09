/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.check.exceptions;

public class CheckNotFoundException
extends RuntimeException {
    public CheckNotFoundException() {
        super("Check not found! Contact an administrator.");
    }
}

