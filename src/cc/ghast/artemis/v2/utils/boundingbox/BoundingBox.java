/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.utils.boundingbox;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.utils.location.Position;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class BoundingBox {
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;
    private final long timestamp;

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, long timestamp) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.timestamp = timestamp;
    }

    public BoundingBox(Position min, long timestamp) {
        this(min.getMinX(), min.getY(), min.getMinZ(), min.getMaxX(), min.getY(), min.getMaxZ(), timestamp);
    }

    public BoundingBox(Vector min, Vector max, long timestamp) {
        this.minX = min.getX();
        this.minZ = min.getZ();
        this.minY = min.getY();
        this.maxX = max.getX();
        this.maxY = max.getY();
        this.maxZ = max.getZ();
        this.timestamp = timestamp;
    }

    public BoundingBox shrink(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX -= x;
        this.maxY -= y;
        this.maxZ -= z;
        return this;
    }

    public BoundingBox expand(double x, double y, double z) {
        this.minX -= x;
        this.minY -= y;
        this.minZ -= z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public BoundingBox add(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    public BoundingBox subtract(double x, double y, double z) {
        this.minX -= x;
        this.minY -= y;
        this.minZ -= z;
        this.maxX -= x;
        this.maxY -= y;
        this.maxZ -= z;
        return this;
    }

    public double middleX() {
        return (this.minX + this.maxX) / 2.0;
    }

    public double middleY() {
        return (this.minY + this.maxY) / 2.0;
    }

    public double middleZ() {
        return (this.minZ + this.maxZ) / 2.0;
    }

    public double distance(BoundingBox box) {
        double x = Math.abs(this.middleX() - box.middleX());
        double y = Math.abs(this.middleY() - box.middleY());
        double z = Math.abs(this.middleZ() - box.middleZ());
        return Math.sqrt(x * x + z * z + y * y);
    }

    public double distanceXZ(BoundingBox box) {
        double x = Math.abs(this.middleX() - box.middleX());
        double z = Math.abs(this.middleZ() - box.middleZ());
        return Math.sqrt(x * x + z * z);
    }

    public double distanceMinMax() {
        double minXZDiagonal = Math.sqrt(this.minX * this.minX + this.minZ * this.minZ);
        double minMaxDiagonal = Math.sqrt(minXZDiagonal * minXZDiagonal + this.maxY * this.maxY);
        return minMaxDiagonal;
    }

    public boolean checkCollision(final World world, final Predicate<Material> predicate) throws InterruptedException {
        final AtomicInteger x = new AtomicInteger((int)Math.floor(this.minX));
        final AtomicInteger y = new AtomicInteger((int)Math.max(Math.floor(this.minY), 0.0));
        final AtomicInteger z = new AtomicInteger((int)Math.floor(this.minZ));
        final int x2 = (int)Math.floor(this.maxX);
        final int y2 = (int)Math.floor(this.maxY);
        final int z2 = (int)Math.floor(this.maxZ);
        if (x.get() == Integer.MAX_VALUE || y.get() == Integer.MAX_VALUE || z.get() == Integer.MAX_VALUE || x2 == Integer.MAX_VALUE || y2 == Integer.MAX_VALUE || z2 == Integer.MAX_VALUE) {
            return false;
        }
        final AtomicBoolean result = new AtomicBoolean(false);
        boolean executed = false;
        BukkitRunnable runnable = new BukkitRunnable(){

            public void run() {
                while (x.getAndIncrement() < x2) {
                    while (y.getAndIncrement() < y2) {
                        while (z.getAndIncrement() < z2) {
                            Block block = world.getBlockAt(x.get(), y.get(), z.get());
                            boolean res = predicate.test(block.getType());
                            if (!res) continue;
                            result.set(true);
                            return;
                        }
                    }
                }
            }
        };
        Bukkit.getScheduler().runTask((Plugin)Artemis.INSTANCE.getPlugin(), runnable);
        return result.get();
    }

}

