/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package cc.ghast.artemis.v2.storage;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.saving.AbstractStorage;
import cc.ghast.artemis.v2.api.saving.SaveData;
import cc.ghast.artemis.v2.api.saving.SaveDataSerializer;
import cc.ghast.artemis.v2.api.saving.StorageSaveException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FlatFileStorage
extends AbstractStorage {
    @Override
    public List<SaveData> load(PlayerData data) {
        File dir = Arrays.stream(Objects.requireNonNull(Artemis.INSTANCE.getPlugin().getDataFolder().listFiles())).filter(file -> file.getName().equals("data")).findFirst().orElse(null);
        if (dir == null) {
            return null;
        }
        ArrayList<SaveData> result = new ArrayList<SaveData>();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SaveData.class, (Object)new SaveDataSerializer(data));
        for (File file2 : Objects.requireNonNull(dir.listFiles())) {
            try {
                Gson gson = builder.create();
                FileReader reader = new FileReader(file2);
                BufferedReader buffer = new BufferedReader(reader);
                String json = buffer.lines().toString();
                SaveData save = (SaveData)gson.fromJson(json, SaveData.class);
                result.add(save);
            }
            catch (IOException gson) {
                // empty catch block
            }
        }
        return result;
    }

    @Override
    public void save(List<SaveData> saveData, PlayerData data) {
        File dir = Arrays.stream(Objects.requireNonNull(Artemis.INSTANCE.getPlugin().getDataFolder().listFiles())).filter(file -> file.getName().equals("data")).findFirst().orElse(null);
        if (dir == null) {
            throw new StorageSaveException();
        }
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(SaveData.class, (Object)new SaveDataSerializer(data));
        saveData.forEach(d -> {
            Gson gson = builder.create();
            File file = new File(dir.getAbsolutePath(), d.getUuid());
            try {
                FileWriter writer = new FileWriter(file);
                String lines = gson.toJson(d);
                writer.write(lines);
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

