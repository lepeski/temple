package com.templebuilder.geometry;

import org.bukkit.Material;

public class SymmetryWriter {

    private final Blueprint blueprint;

    public SymmetryWriter(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public void set(int x, int y, int z, Material material) {
        int[] xs = x == 0 ? new int[]{0} : new int[]{x, -x};
        int[] zs = z == 0 ? new int[]{0} : new int[]{z, -z};
        for (int sx : xs) {
            for (int sz : zs) {
                blueprint.set(new BlockVector(sx, y, sz), material);
            }
        }
    }

    public void setLineX(int startX, int endX, int y, int z, Material material) {
        for (int x = startX; x <= endX; x++) {
            set(x, y, z, material);
        }
    }

    public void setLineZ(int x, int y, int startZ, int endZ, Material material) {
        for (int z = startZ; z <= endZ; z++) {
            set(x, y, z, material);
        }
    }

    public void fillHorizontal(int startX, int endX, int y, int startZ, int endZ, Material material) {
        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                set(x, y, z, material);
            }
        }
    }
}
