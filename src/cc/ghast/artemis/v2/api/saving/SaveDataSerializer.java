/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 */
package cc.ghast.artemis.v2.api.saving;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.Violation;
import cc.ghast.artemis.v2.api.encryption.IpTransformer;
import cc.ghast.artemis.v2.api.saving.SaveData;
import cc.ghast.artemis.v2.api.saving.StorageLoadException;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SaveDataSerializer
implements JsonSerializer<SaveData>,
JsonDeserializer<SaveData> {
    private final PlayerData data;

    public SaveDataSerializer(PlayerData data) {
        this.data = data;
    }

    public JsonElement serialize(SaveData data, Type type, JsonSerializationContext jsonSerializationContext) {
        String ip;
        JsonObject result = new JsonObject();
        result.add("uuid", (JsonElement)new JsonPrimitive(data.getUuid()));
        result.add("name", (JsonElement)new JsonPrimitive(data.getUsername()));
        try {
            ip = IpTransformer.serialize(data.getIp(), ConfigManager.getSettings().getString("encryption.ip.secret-key"));
        }
        catch (Exception e) {
            ip = data.getIp();
            Chat.sendConsoleMessage("&7[&b&lArtemis&7] &c&lFailed to encrypt IP! Please make sure everything is correct!");
        }
        result.add("ip", (JsonElement)new JsonPrimitive(ip));
        JsonArray violations = new JsonArray();
        data.getViolations().forEach(vl -> {
            JsonObject violation = new JsonObject();
            violation.add("check", (JsonElement)new JsonPrimitive(vl.getCheck().getType().toString() + vl.getCheck().getVar()));
            violation.add("count", (JsonElement)new JsonPrimitive((Number)vl.getCount()));
            violation.add("timestamp", (JsonElement)new JsonPrimitive((Number)vl.getTimestamp()));
            violations.add((JsonElement)violation);
        });
        result.add("violations", (JsonElement)violations);
        return result;
    }

    public SaveData deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String ip;
        JsonObject object = json.getAsJsonObject();
        String uuid = object.getAsJsonPrimitive("uuid").getAsString();
        String name = object.getAsJsonPrimitive("name").getAsString();
        try {
            ip = IpTransformer.deserialize(object.getAsJsonPrimitive("ip").getAsString(), ConfigManager.getSettings().getString("encryption.ip.secret-key"));
        }
        catch (Exception e) {
            ip = object.getAsJsonPrimitive("ip").getAsString();
        }
        ArrayList<Violation> vls = new ArrayList<Violation>();
        object.getAsJsonArray("violations").forEach(vlobj -> {
            JsonObject obj = vlobj.getAsJsonObject();
            AbstractCheck check = this.data.getCheckManager().getCheckByName(obj.getAsJsonPrimitive("check").getAsString());
            if (check == null) {
                throw new StorageLoadException();
            }
            int count = obj.getAsJsonPrimitive("count").getAsInt();
            long timestamp = obj.getAsJsonPrimitive("timestamp").getAsLong();
            vls.add(new Violation(check, timestamp, count));
        });
        return new SaveData(uuid, name, ip, vls);
    }
}

