/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EvictingQueue
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.data;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.check.exceptions.CheckLoadError;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.api.data.Verbose;
import cc.ghast.artemis.v2.api.data.Violation;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.dependency.ViaVersionDependency;
import cc.ghast.artemis.v2.managers.DependencyManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.boundingbox.BoundingBox;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.location.Rotation;
import cc.ghast.artemis.v2.utils.location.Velocity;
import cc.ghast.artemis.v2.utils.maths.EvictingDeque;
import cc.ghast.artemis.v2.utils.maths.EvictingLinkedList;
import cc.ghast.lang.CustomCheckDataManager;
import com.google.common.collect.EvictingQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData {
    private final Player player;
    private final UUID uuid;
    public final Movement movement;
    public final Combat combat;
    public final User user;
    public final Staff staff;
    private final CheckManager checkManager;
    private final CustomCheckDataManager checkDataManager;
    private ProtocolVersion version;

    public PlayerData(Player player) {
        block2: {
            this.player = player;
            this.uuid = player.getUniqueId();
            this.movement = new Movement();
            this.combat = new Combat();
            this.user = new User();
            this.staff = new Staff();
            this.checkManager = new CheckManager();
            this.checkDataManager = new CustomCheckDataManager(this);
            try {
                this.version = Bukkit.getPluginManager().isPluginEnabled("ViaVersion") ? (Artemis.INSTANCE.getApi().getDependencyManager().getViaVersionDependency() != null ? Artemis.INSTANCE.getApi().getDependencyManager().getViaVersionDependency().getVersion(player) : ProtocolVersion.getGameVersion()) : ProtocolVersion.getGameVersion();
            }
            catch (Exception e) {
                if (this.version != null) break block2;
                this.version = ProtocolVersion.getGameVersion();
            }
        }
        this.user.violations = new ConcurrentHashMap();
        this.user.verboses = new ConcurrentHashMap();
        this.staff.logDebug = new ArrayList();
        this.staff.debug = new ArrayList();
        this.staff.log = new ArrayList();
        this.movement.previousPositions = (Queue)EvictingQueue.create((int)8);
        this.user.keepAlives = new HashMap();
        this.combat.velocities = new LinkedList();
        Artemis.INSTANCE.getApi().getPlayerDataManager().getToInject().forEach(clazz -> {
            try {
                this.checkManager.addCheck((AbstractCheck)clazz.newInstance());
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new CheckLoadError();
            }
        });
    }

    public void addViolation(AbstractCheck abstractCheck) {
        if (this.user.violations.containsKey(abstractCheck)) {
            this.user.violations.put(abstractCheck, new Violation(abstractCheck, System.currentTimeMillis(), ((Violation)this.user.violations.get(abstractCheck)).getCount() + 1));
        } else {
            this.user.violations.put(abstractCheck, new Violation(abstractCheck, System.currentTimeMillis(), 1));
        }
    }

    public int getViolations(AbstractCheck abstractCheck) {
        if (this.user.violations.containsKey(abstractCheck)) {
            return ((Violation)this.user.violations.get(abstractCheck)).getCount();
        }
        return 0;
    }

    public void addVerbose(AbstractCheck abstractCheck, int quant) {
        if (this.user.verboses.containsKey(abstractCheck)) {
            this.user.verboses.put(abstractCheck, new Verbose(System.currentTimeMillis(), ((Verbose)this.user.verboses.get(abstractCheck)).getCount() + quant));
        } else {
            this.user.verboses.put(abstractCheck, new Verbose(System.currentTimeMillis(), quant));
        }
    }

    public int getVerboses(AbstractCheck abstractCheck) {
        if (this.user.verboses.containsKey(abstractCheck)) {
            return ((Verbose)this.user.verboses.get(abstractCheck)).getCount();
        }
        return 0;
    }

    public boolean wasOnSlime() {
        return System.currentTimeMillis() - this.movement.lastSlime < 2000L;
    }

    public boolean hasBeenDamaged() {
        return System.currentTimeMillis() - this.combat.lastDamage < 1000L;
    }

    public boolean isLagging() {
        long now = System.currentTimeMillis();
        return now - this.movement.lastDelayedMovePacket < 220L || this.movement.teleportTicks > 0;
    }

    public boolean hasJumped() {
        return System.currentTimeMillis() - this.movement.lastJump < 100L;
    }

    public boolean hasAttacked() {
        return System.currentTimeMillis() - this.combat.lastAttack < 200L;
    }

    public boolean isDebug(AbstractCheck abstractCheck) {
        return this.staff.debug.contains(abstractCheck);
    }

    public boolean isLogDebug(AbstractCheck abstractCheck) {
        return this.staff.logDebug.contains(abstractCheck);
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Movement getMovement() {
        return this.movement;
    }

    public CheckManager getCheckManager() {
        return this.checkManager;
    }

    public CustomCheckDataManager getCheckDataManager() {
        return this.checkDataManager;
    }

    public ProtocolVersion getVersion() {
        return this.version;
    }

    public class Staff {
        private boolean isAlerts;
        private boolean packetListening;
        private List<AbstractCheck> debug;
        private List<AbstractCheck> logDebug;
        private List<String> log;
        private StaffEnums.StaffAlerts staffAlert = StaffEnums.StaffAlerts.NONE;

        public boolean isAlerts() {
            return this.isAlerts;
        }

        public boolean isPacketListening() {
            return this.packetListening;
        }

        public void setAlerts(boolean isAlerts) {
            this.isAlerts = isAlerts;
        }

        public void setPacketListening(boolean packetListening) {
            this.packetListening = packetListening;
        }

        public List<AbstractCheck> getDebug() {
            return this.debug;
        }

        public List<AbstractCheck> getLogDebug() {
            return this.logDebug;
        }

        public List<String> getLog() {
            return this.log;
        }

        public StaffEnums.StaffAlerts getStaffAlert() {
            return this.staffAlert;
        }

        public void setStaffAlert(StaffEnums.StaffAlerts staffAlert) {
            this.staffAlert = staffAlert;
        }
    }

    public class User {
        private Map<AbstractCheck, Violation> violations;
        private Map<AbstractCheck, Verbose> verboses;
        private Map<Long, Long> keepAlives;
        private BoundingBox box;
        private long lastFlyingPacket;
        private long lastKeepAlive;
        private long lastLag;
        private long ping;
        private long lastPlace;
        private long lastUnderBlock;
        private long longInTimePassed;
        private long lastSentKeepAlive;
        private long lastDig;
        private boolean placed;
        private boolean tpUnknown;
        private boolean onGround;
        private boolean inLiquid;
        private boolean digging;
        private boolean fakeDigging;
        private boolean inventoryOpen;
        private boolean underBlock;
        private boolean aboveLiquid;
        private String customClientPayload = "";

        public boolean isLagging() {
            long now = System.currentTimeMillis();
            return now - PlayerData.this.movement.getLastDelayedMovePacket() < 220L || PlayerData.this.movement.getTeleportTicks() > 0;
        }

        public boolean keepAliveExists(long id) {
            return this.keepAlives.containsKey(id);
        }

        public long getKeepAliveTime(long id) {
            long time = this.keepAlives.get(id);
            this.keepAlives.remove(id);
            return time;
        }

        public void addKeepAliveTime(long id) {
            this.keepAlives.put(id, System.currentTimeMillis());
        }

        public Map<AbstractCheck, Violation> getViolations() {
            return this.violations;
        }

        public Map<AbstractCheck, Verbose> getVerboses() {
            return this.verboses;
        }

        public Map<Long, Long> getKeepAlives() {
            return this.keepAlives;
        }

        public BoundingBox getBox() {
            return this.box;
        }

        public long getLastFlyingPacket() {
            return this.lastFlyingPacket;
        }

        public long getLastKeepAlive() {
            return this.lastKeepAlive;
        }

        public long getLastLag() {
            return this.lastLag;
        }

        public long getPing() {
            return this.ping;
        }

        public long getLastPlace() {
            return this.lastPlace;
        }

        public long getLastUnderBlock() {
            return this.lastUnderBlock;
        }

        public long getLongInTimePassed() {
            return this.longInTimePassed;
        }

        public long getLastSentKeepAlive() {
            return this.lastSentKeepAlive;
        }

        public long getLastDig() {
            return this.lastDig;
        }

        public void setLastFlyingPacket(long lastFlyingPacket) {
            this.lastFlyingPacket = lastFlyingPacket;
        }

        public void setLastKeepAlive(long lastKeepAlive) {
            this.lastKeepAlive = lastKeepAlive;
        }

        public void setLastLag(long lastLag) {
            this.lastLag = lastLag;
        }

        public void setPing(long ping) {
            this.ping = ping;
        }

        public void setLastPlace(long lastPlace) {
            this.lastPlace = lastPlace;
        }

        public void setLastUnderBlock(long lastUnderBlock) {
            this.lastUnderBlock = lastUnderBlock;
        }

        public void setLongInTimePassed(long longInTimePassed) {
            this.longInTimePassed = longInTimePassed;
        }

        public void setLastSentKeepAlive(long lastSentKeepAlive) {
            this.lastSentKeepAlive = lastSentKeepAlive;
        }

        public void setLastDig(long lastDig) {
            this.lastDig = lastDig;
        }

        public boolean isPlaced() {
            return this.placed;
        }

        public boolean isTpUnknown() {
            return this.tpUnknown;
        }

        public boolean isOnGround() {
            return this.onGround;
        }

        public boolean isInLiquid() {
            return this.inLiquid;
        }

        public boolean isDigging() {
            return this.digging;
        }

        public boolean isFakeDigging() {
            return this.fakeDigging;
        }

        public boolean isInventoryOpen() {
            return this.inventoryOpen;
        }

        public boolean isUnderBlock() {
            return this.underBlock;
        }

        public boolean isAboveLiquid() {
            return this.aboveLiquid;
        }

        public void setPlaced(boolean placed) {
            this.placed = placed;
        }

        public void setTpUnknown(boolean tpUnknown) {
            this.tpUnknown = tpUnknown;
        }

        public void setOnGround(boolean onGround) {
            this.onGround = onGround;
        }

        public void setInLiquid(boolean inLiquid) {
            this.inLiquid = inLiquid;
        }

        public void setDigging(boolean digging) {
            this.digging = digging;
        }

        public void setFakeDigging(boolean fakeDigging) {
            this.fakeDigging = fakeDigging;
        }

        public void setInventoryOpen(boolean inventoryOpen) {
            this.inventoryOpen = inventoryOpen;
        }

        public void setUnderBlock(boolean underBlock) {
            this.underBlock = underBlock;
        }

        public void setAboveLiquid(boolean aboveLiquid) {
            this.aboveLiquid = aboveLiquid;
        }

        public String getCustomClientPayload() {
            return this.customClientPayload;
        }

        public void setCustomClientPayload(String customClientPayload) {
            this.customClientPayload = customClientPayload;
        }
    }

    public class Combat {
        private Player lastOpponent;
        private double cps;
        private double lastCps;
        private long lastAttack;
        private long lastDamage;
        private long lastBowDamage;
        private long lastExplosion;
        private List<Velocity> velocities;

        public Player getLastOpponent() {
            return this.lastOpponent;
        }

        public void setLastOpponent(Player lastOpponent) {
            this.lastOpponent = lastOpponent;
        }

        public double getCps() {
            return this.cps;
        }

        public double getLastCps() {
            return this.lastCps;
        }

        public void setCps(double cps) {
            this.cps = cps;
        }

        public void setLastCps(double lastCps) {
            this.lastCps = lastCps;
        }

        public long getLastAttack() {
            return this.lastAttack;
        }

        public long getLastDamage() {
            return this.lastDamage;
        }

        public long getLastBowDamage() {
            return this.lastBowDamage;
        }

        public long getLastExplosion() {
            return this.lastExplosion;
        }

        public void setLastAttack(long lastAttack) {
            this.lastAttack = lastAttack;
        }

        public void setLastDamage(long lastDamage) {
            this.lastDamage = lastDamage;
        }

        public void setLastBowDamage(long lastBowDamage) {
            this.lastBowDamage = lastBowDamage;
        }

        public void setLastExplosion(long lastExplosion) {
            this.lastExplosion = lastExplosion;
        }

        public List<Velocity> getVelocities() {
            return this.velocities;
        }

        public void setVelocities(List<Velocity> velocities) {
            this.velocities = velocities;
        }
    }

    public class Movement {
        private EvictingLinkedList<Position> positions = new EvictingLinkedList<Position>(25);
        private Position location;
        private Position lastLocation;
        private Rotation rotation;
        private Rotation lastRotation;
        private int teleportTicks;
        private int respawnTicks;
        private int sprintTicks;
        private int standTicks;
        private int deathTicks;
        private int flyTicks;
        private long lastDelayedMovePacket;
        private long lastMovePacket;
        private long lastJump;
        private long lastMoveCancel;
        private long lastOnGround;
        private long lastOnIce;
        private long lastSlime;
        private boolean sprinting;
        private boolean wasOnGround;
        private boolean wasOnIce;
        private boolean onIce;
        private Queue<Position> previousPositions;
        private Velocity velocity;
        private Velocity lastVelocity;
        private double horizontalSpeed;

        public EvictingLinkedList<Position> getPositions() {
            return this.positions;
        }

        public Position getLocation() {
            return this.location;
        }

        public Position getLastLocation() {
            return this.lastLocation;
        }

        public void setLocation(Position location) {
            this.location = location;
        }

        public void setLastLocation(Position lastLocation) {
            this.lastLocation = lastLocation;
        }

        public Rotation getRotation() {
            return this.rotation;
        }

        public Rotation getLastRotation() {
            return this.lastRotation;
        }

        public void setRotation(Rotation rotation) {
            this.rotation = rotation;
        }

        public void setLastRotation(Rotation lastRotation) {
            this.lastRotation = lastRotation;
        }

        public int getTeleportTicks() {
            return this.teleportTicks;
        }

        public int getRespawnTicks() {
            return this.respawnTicks;
        }

        public int getSprintTicks() {
            return this.sprintTicks;
        }

        public int getStandTicks() {
            return this.standTicks;
        }

        public int getDeathTicks() {
            return this.deathTicks;
        }

        public int getFlyTicks() {
            return this.flyTicks;
        }

        public void setTeleportTicks(int teleportTicks) {
            this.teleportTicks = teleportTicks;
        }

        public void setRespawnTicks(int respawnTicks) {
            this.respawnTicks = respawnTicks;
        }

        public void setSprintTicks(int sprintTicks) {
            this.sprintTicks = sprintTicks;
        }

        public void setStandTicks(int standTicks) {
            this.standTicks = standTicks;
        }

        public void setDeathTicks(int deathTicks) {
            this.deathTicks = deathTicks;
        }

        public void setFlyTicks(int flyTicks) {
            this.flyTicks = flyTicks;
        }

        public long getLastDelayedMovePacket() {
            return this.lastDelayedMovePacket;
        }

        public long getLastMovePacket() {
            return this.lastMovePacket;
        }

        public long getLastJump() {
            return this.lastJump;
        }

        public long getLastMoveCancel() {
            return this.lastMoveCancel;
        }

        public long getLastOnGround() {
            return this.lastOnGround;
        }

        public long getLastOnIce() {
            return this.lastOnIce;
        }

        public long getLastSlime() {
            return this.lastSlime;
        }

        public void setLastDelayedMovePacket(long lastDelayedMovePacket) {
            this.lastDelayedMovePacket = lastDelayedMovePacket;
        }

        public void setLastMovePacket(long lastMovePacket) {
            this.lastMovePacket = lastMovePacket;
        }

        public void setLastJump(long lastJump) {
            this.lastJump = lastJump;
        }

        public void setLastMoveCancel(long lastMoveCancel) {
            this.lastMoveCancel = lastMoveCancel;
        }

        public void setLastOnGround(long lastOnGround) {
            this.lastOnGround = lastOnGround;
        }

        public void setLastOnIce(long lastOnIce) {
            this.lastOnIce = lastOnIce;
        }

        public void setLastSlime(long lastSlime) {
            this.lastSlime = lastSlime;
        }

        public boolean isSprinting() {
            return this.sprinting;
        }

        public boolean isWasOnGround() {
            return this.wasOnGround;
        }

        public boolean isWasOnIce() {
            return this.wasOnIce;
        }

        public boolean isOnIce() {
            return this.onIce;
        }

        public void setSprinting(boolean sprinting) {
            this.sprinting = sprinting;
        }

        public void setWasOnGround(boolean wasOnGround) {
            this.wasOnGround = wasOnGround;
        }

        public void setWasOnIce(boolean wasOnIce) {
            this.wasOnIce = wasOnIce;
        }

        public void setOnIce(boolean onIce) {
            this.onIce = onIce;
        }

        public Queue<Position> getPreviousPositions() {
            return this.previousPositions;
        }

        public Velocity getVelocity() {
            return this.velocity;
        }

        public Velocity getLastVelocity() {
            return this.lastVelocity;
        }

        public void setVelocity(Velocity velocity) {
            this.velocity = velocity;
        }

        public void setLastVelocity(Velocity lastVelocity) {
            this.lastVelocity = lastVelocity;
        }

        public double getHorizontalSpeed() {
            return this.horizontalSpeed;
        }

        public void setHorizontalSpeed(double horizontalSpeed) {
            this.horizontalSpeed = horizontalSpeed;
        }
    }

}

