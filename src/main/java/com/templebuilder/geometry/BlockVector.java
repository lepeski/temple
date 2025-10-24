package com.templebuilder.geometry;

import java.util.Objects;

public class BlockVector {
    private final int x;
    private final int y;
    private final int z;

    public BlockVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public BlockVector add(int dx, int dy, int dz) {
        return new BlockVector(x + dx, y + dy, z + dz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockVector)) return false;
        BlockVector that = (BlockVector) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
