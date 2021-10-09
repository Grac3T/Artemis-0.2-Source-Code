/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.storage;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.saving.AbstractStorage;
import cc.ghast.artemis.v2.api.saving.SaveData;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import java.util.List;

public class MongoStorage
extends AbstractStorage {
    private MongoClient client;
    private MongoCredential credential;
    protected DB database;

    public MongoStorage(String ip, int port, String user, String password, String table) {
        try {
            this.client = new MongoClient(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.credential = MongoCredential.createPlainCredential(user, table, password.toCharArray());
        this.database = this.client.getDB(table);
    }

    @Override
    public List<SaveData> load(PlayerData data) {
        return null;
    }

    @Override
    public void save(List<SaveData> saveData, PlayerData data) {
    }
}

