/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

public class ArithmeticParsingException
extends RuntimeException {
    public ArithmeticParsingException() {
        super("Error when parsing eff condition. Check and make sure the return value is a digit");
    }
}

