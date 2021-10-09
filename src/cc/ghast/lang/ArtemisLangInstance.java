/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  cc.ghast.artemisloader.yggdrasil.Instance
 */
package cc.ghast.lang;


public enum ArtemisLangInstance {
    INSTANCE;

    private ArtemisLang api;

    public void enable() {
        this.api = new ArtemisLang();
    }

    public ArtemisLang getApi() {
        return this.api;
    }
}

