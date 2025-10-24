package com.templebuilder.geometry;

import org.bukkit.Material;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Blueprint {

    private final Map<BlockVector, Material> blocks = new HashMap<>();
    private int minX = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minZ = Integer.MAX_VALUE;
    private int maxZ = Integer.MIN_VALUE;

    public void set(BlockVector vector, Material material) {
        blocks.put(vector, material);
        minX = Math.min(minX, vector.getX());
        maxX = Math.max(maxX, vector.getX());
        minY = Math.min(minY, vector.getY());
        maxY = Math.max(maxY, vector.getY());
        minZ = Math.min(minZ, vector.getZ());
        maxZ = Math.max(maxZ, vector.getZ());
    }

    public Material get(BlockVector vector) {
        return blocks.get(vector);
    }

    public Collection<Map.Entry<BlockVector, Material>> entries() {
        return Collections.unmodifiableCollection(blocks.entrySet());
    }

    public int minX() {
        return minX;
    }

    public int maxX() {
        return maxX;
    }

    public int minY() {
        return minY;
    }

    public int maxY() {
        return maxY;
    }

    public int minZ() {
        return minZ;
    }

    public int maxZ() {
        return maxZ;
    }
}
