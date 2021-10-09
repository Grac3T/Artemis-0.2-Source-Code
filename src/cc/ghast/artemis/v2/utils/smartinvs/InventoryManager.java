/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.server.PluginDisableEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package cc.ghast.artemis.v2.utils.smartinvs;

import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.InventoryListener;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import cc.ghast.artemis.v2.utils.smartinvs.opener.ChestInventoryOpener;
import cc.ghast.artemis.v2.utils.smartinvs.opener.InventoryOpener;
import cc.ghast.artemis.v2.utils.smartinvs.opener.SpecialInventoryOpener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class InventoryManager {
    private JavaPlugin plugin;
    private PluginManager pluginManager;
    private Map<Player, SmartInventory> inventories;
    private Map<Player, InventoryContents> contents;
    private List<InventoryOpener> defaultOpeners;
    private List<InventoryOpener> openers;

    public InventoryManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginManager = Bukkit.getPluginManager();
        this.inventories = new HashMap<Player, SmartInventory>();
        this.contents = new HashMap<Player, InventoryContents>();
        this.defaultOpeners = Arrays.asList(new ChestInventoryOpener(), new SpecialInventoryOpener());
        this.openers = new ArrayList<InventoryOpener>();
    }

    public void init() {
        this.pluginManager.registerEvents((Listener)new InvListener(), (Plugin)this.plugin);
        new InvTask().runTaskTimer((Plugin)this.plugin, 1L, 1L);
    }

    public Optional<InventoryOpener> findOpener(InventoryType type) {
        Optional<InventoryOpener> opInv = this.openers.stream().filter(opener -> opener.supports(type)).findAny();
        if (!opInv.isPresent()) {
            opInv = this.defaultOpeners.stream().filter(opener -> opener.supports(type)).findAny();
        }
        return opInv;
    }

    public void registerOpeners(InventoryOpener ... openers) {
        this.openers.addAll(Arrays.asList(openers));
    }

    public List<Player> getOpenedPlayers(SmartInventory inv) {
        ArrayList<Player> list = new ArrayList<Player>();
        this.inventories.forEach((player, playerInv) -> {
            if (inv.equals(playerInv)) {
                list.add((Player)player);
            }
        });
        return list;
    }

    public Optional<SmartInventory> getInventory(Player p) {
        return Optional.ofNullable(this.inventories.get((Object)p));
    }

    protected void setInventory(Player p, SmartInventory inv) {
        if (inv == null) {
            this.inventories.remove((Object)p);
        } else {
            this.inventories.put(p, inv);
        }
    }

    public Optional<InventoryContents> getContents(Player p) {
        return Optional.ofNullable(this.contents.get((Object)p));
    }

    protected void setContents(Player p, InventoryContents contents) {
        if (contents == null) {
            this.contents.remove((Object)p);
        } else {
            this.contents.put(p, contents);
        }
    }

    class InvTask
    extends BukkitRunnable {
        InvTask() {
        }

        public void run() {
            new HashMap<Player, SmartInventory>(InventoryManager.this.inventories).forEach((player, inv) -> inv.getProvider().update((Player)player, (InventoryContents)InventoryManager.this.contents.get(player)));
        }
    }

    class InvListener
    implements Listener {
        InvListener() {
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onInventoryClick(InventoryClickEvent e) {
            Player p = (Player)e.getWhoClicked();
            if (!InventoryManager.this.inventories.containsKey((Object)p)) {
                return;
            }
            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getAction() == InventoryAction.NOTHING) {
                e.setCancelled(true);
                return;
            }
            if (e.getClickedInventory() == p.getOpenInventory().getTopInventory()) {
                e.setCancelled(true);
                int row = e.getSlot() / 9;
                int column = e.getSlot() % 9;
                if (row < 0 || column < 0) {
                    return;
                }
                SmartInventory inv = (SmartInventory)InventoryManager.this.inventories.get((Object)p);
                if (row >= inv.getRows() || column >= inv.getColumns()) {
                    return;
                }
                inv.getListeners().stream().filter(listener -> listener.getType() == InventoryClickEvent.class).forEach(listener -> listener.accept(e));
                ((InventoryContents)InventoryManager.this.contents.get((Object)p)).get(row, column).ifPresent(item -> item.run(e));
                p.updateInventory();
            }
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onInventoryDrag(InventoryDragEvent e) {
            Player p = (Player)e.getWhoClicked();
            if (!InventoryManager.this.inventories.containsKey((Object)p)) {
                return;
            }
            SmartInventory inv = (SmartInventory)InventoryManager.this.inventories.get((Object)p);
            Iterator iterator = e.getRawSlots().iterator();
            while (iterator.hasNext()) {
                int slot = (Integer)iterator.next();
                if (slot >= p.getOpenInventory().getTopInventory().getSize()) continue;
                e.setCancelled(true);
                break;
            }
            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryDragEvent.class).forEach(listener -> listener.accept(e));
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onInventoryOpen(InventoryOpenEvent e) {
            Player p = (Player)e.getPlayer();
            if (!InventoryManager.this.inventories.containsKey((Object)p)) {
                return;
            }
            SmartInventory inv = (SmartInventory)InventoryManager.this.inventories.get((Object)p);
            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryOpenEvent.class).forEach(listener -> listener.accept(e));
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onInventoryClose(InventoryCloseEvent e) {
            Player p = (Player)e.getPlayer();
            if (!InventoryManager.this.inventories.containsKey((Object)p)) {
                return;
            }
            SmartInventory inv = (SmartInventory)InventoryManager.this.inventories.get((Object)p);
            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryCloseEvent.class).forEach(listener -> listener.accept(e));
            if (inv.isCloseable()) {
                e.getInventory().clear();
                InventoryManager.this.inventories.remove((Object)p);
                InventoryManager.this.contents.remove((Object)p);
            } else {
                Bukkit.getScheduler().runTask((Plugin)InventoryManager.this.plugin, () -> p.openInventory(e.getInventory()));
            }
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onPlayerQuit(PlayerQuitEvent e) {
            Player p = e.getPlayer();
            if (!InventoryManager.this.inventories.containsKey((Object)p)) {
                return;
            }
            SmartInventory inv = (SmartInventory)InventoryManager.this.inventories.get((Object)p);
            inv.getListeners().stream().filter(listener -> listener.getType() == PlayerQuitEvent.class).forEach(listener -> listener.accept(e));
            InventoryManager.this.inventories.remove((Object)p);
            InventoryManager.this.contents.remove((Object)p);
        }

        @EventHandler(priority=EventPriority.LOW)
        public void onPluginDisable(PluginDisableEvent e) {
            new HashMap<Player, SmartInventory>(InventoryManager.this.inventories).forEach((player, inv) -> {
                inv.getListeners().stream().filter(listener -> listener.getType() == PluginDisableEvent.class).forEach(listener -> listener.accept(e));
                inv.close((Player)player);
            });
            InventoryManager.this.inventories.clear();
            InventoryManager.this.contents.clear();
        }
    }

}

