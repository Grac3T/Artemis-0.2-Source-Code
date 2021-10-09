/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.location;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.boundingbox.BoundingBox;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Position {
    private final Player player;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final World bukkitWorld;
    private final Location bukkitLocation;
    private final long timestamp;
    private final double minX;
    private final double centerX;
    private final double maxX;
    private final double minZ;
    private final double centerZ;
    private final double maxZ;
    private final double minY;
    private final double centerY;
    private final double maxY;

    public Position(Player player, double x, double y, double z, float pitch, float yaw, World bukkitWorld, Location bukkitLocation, long timestamp) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.minX = x - 0.3;
        this.centerX = x;
        this.maxX = x + 0.3;
        this.minY = y;
        this.centerY = y + 0.925;
        this.maxY = y + 1.85;
        this.minZ = z - 0.3;
        this.centerZ = z;
        this.maxZ = z + 0.3;
        this.bukkitLocation = bukkitLocation;
        this.bukkitWorld = bukkitWorld;
        this.timestamp = timestamp;
    }

    public Position(Player player, double x, double y, double z, float pitch, float yaw, World bukkitWorld, long timestamp) {
        this(player, x, y, z, pitch, yaw, bukkitWorld, new Location(bukkitWorld, x, y, z, pitch, yaw), timestamp);
    }

    public Position(Player player, World bukkitWorld, Location bukkitLocation, long timestamp) {
        this(player, bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ(), bukkitLocation.getPitch(), bukkitLocation.getYaw(), bukkitWorld, new Location(bukkitWorld, bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ(), bukkitLocation.getPitch(), bukkitLocation.getYaw()), timestamp);
    }

    public Position(PlayerData player, double x, double y, double z, World bukkitWorld, long timestamp) {
        this(player.getPlayer(), x, y, z, player.movement.getLocation().getPitch(), player.movement.getLocation().getYaw(), bukkitWorld, new Location(bukkitWorld, x, y, z, player.movement.getLocation().getPitch(), player.movement.getLocation().getYaw()), timestamp);
    }

    public double getDistanceSquared(Position hitbox) {
        double dx = Math.min(Math.abs(hitbox.centerX - this.minX), Math.abs(hitbox.centerX - this.maxX));
        double dz = Math.min(Math.abs(hitbox.centerZ - this.minZ), Math.abs(hitbox.centerZ - this.maxZ));
        return dx * dx + dz * dz;
    }

    public BoundingBox box() {
        return new BoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ, this.timestamp).expand(0.095, 0.095, 0.095);
    }

    public Player getPlayer() {
        return this.player;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public World getBukkitWorld() {
        return this.bukkitWorld;
    }

    public Location getBukkitLocation() {
        return this.bukkitLocation;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public double getCenterZ() {
        return this.centerZ;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getCenterY() {
        return this.centerY;
    }

    public double getMaxY() {
        return this.maxY;
    }
}

