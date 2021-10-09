/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  us.myles.ViaVersion.api.Via
 *  us.myles.ViaVersion.api.ViaAPI
 */
package cc.ghast.artemis.v2.dependency;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.dependency.AbstractDependency;
import cc.ghast.artemis.v2.dependency.Dependency;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

@Dependency(name="ViaVersion", version="2.1.3")
public class ViaVersionDependency
extends AbstractDependency {
    public ViaAPI plugin;

    @Override
    public void init() {
        try {
            this.plugin = Via.getAPI();
        }
        catch (Exception e) {
            Chat.translate("&8[&4WARNING8]&c ViaVersion not detected!");
            Bukkit.getPluginManager().disablePlugin((Plugin)Artemis.INSTANCE.getPlugin());
        }
    }

    public ProtocolVersion getVersion(Player player) {
        return ProtocolVersion.getVersion(this.plugin.getPlayerVersion(player.getUniqueId()));
    }
}

