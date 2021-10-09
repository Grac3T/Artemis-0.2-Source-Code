/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package cc.ghast.artemis.v2.utils.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectBuilder {
    private JsonObject jsonObject = new JsonObject();

    public JsonObjectBuilder addProperty(String key, String value) {
        this.jsonObject.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder addProperty(String key, int value) {
        this.jsonObject.addProperty(key, (Number)value);
        return this;
    }

    public JsonObjectBuilder addProperty(String key, double value) {
        this.jsonObject.addProperty(key, (Number)value);
        return this;
    }

    public JsonObjectBuilder addProperty(String key, long value) {
        this.jsonObject.addProperty(key, (Number)value);
        return this;
    }

    public JsonObjectBuilder addProperty(String key, float value) {
        this.jsonObject.addProperty(key, (Number)Float.valueOf(value));
        return this;
    }

    public JsonObjectBuilder addProperty(String key, char value) {
        this.jsonObject.addProperty(key, Character.valueOf(value));
        return this;
    }

    public JsonObjectBuilder addProperty(String key, boolean value) {
        this.jsonObject.addProperty(key, Boolean.valueOf(value));
        return this;
    }

    public JsonObjectBuilder addProperty(String key, JsonElement value) {
        this.jsonObject.add(key, value);
        return this;
    }

    public JsonObject get() {
        return this.jsonObject;
    }
}

