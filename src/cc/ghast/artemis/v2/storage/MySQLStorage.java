/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mysql.jdbc.jdbc2.optional.MysqlDataSource
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.storage;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.saving.AbstractStorage;
import cc.ghast.artemis.v2.api.saving.SaveData;
import cc.ghast.artemis.v2.api.saving.SaveDataSerializer;
import cc.ghast.artemis.v2.utils.chat.Chat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

public class MySQLStorage
extends AbstractStorage {
    private final String ip;
    private final String port;
    private final String name;
    private final String table;
    private final String user;
    private final String password;
    private Connection connect = null;
    private Statement ignored = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public MySQLStorage(String ip, String port, String name, String table, String user, String password) {
        this.ip = ip;
        this.table = table;
        this.user = user;
        this.password = password;
        this.port = port;
        this.name = name;
        this.init();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<SaveData> load(PlayerData data) {
        ArrayList<String> set = new ArrayList<String>();
        ArrayList<SaveData> result = new ArrayList<SaveData>();
        try {
            this.statement = this.connect.prepareStatement("SELECT * FROM " + this.table);
            this.resultSet = this.statement.executeQuery();
            while (this.resultSet.next()) {
                set.add(this.resultSet.getString("data"));
            }
            this.statement.close();
            this.resultSet.close();
        }
        catch (SQLException e) {
            Chat.sendConsoleMessage("&7[&b&lArtemis&7]&c&l Issue when pulling data from database. Error?");
        }
        finally {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(SaveData.class, (Object)new SaveDataSerializer(data));
            Gson gson = builder.create();
            set.forEach(s -> result.add((SaveData)gson.fromJson(s, SaveData.class)));
        }
        return result;
    }

    @Override
    public void save(List<SaveData> saveData, PlayerData data) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(SaveData.class, (Object)new SaveDataSerializer(data));
        Gson gson = builder.create();
        saveData.forEach(save -> {
            String statement = "SELECT COUNT(*) FROM " + this.table + " WHERE `uuid`=" + data.getPlayer().getUniqueId().toString();
            try {
                PreparedStatement send = this.connect.prepareStatement(statement);
                ResultSet set = send.executeQuery();
                if (set.getInt("total") > 0) {
                    String string = "DELETE * FROM " + this.table + " WHERE `uuid`=" + data.getPlayer().getUniqueId().toString();
                }
                String insert = "INSERT INTO " + this.table + " (uuid, data)  VALUES ('" + save.getUuid() + "', '" + gson.toJson(save) + "');";
                this.connect.prepareStatement(insert).execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(this.user);
            dataSource.setPassword(this.password);
            dataSource.setPort(Integer.getInteger(this.port));
            dataSource.setServerName(this.ip);
            dataSource.setDatabaseName(this.name);
            this.connect = dataSource.getConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

