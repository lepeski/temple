package com.templebuilder.build;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockPlacement {

    private final int x;
    private final int y;
    private final int z;
    private final BlockData blockData;

    public BlockPlacement(int x, int y, int z, BlockData blockData) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
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

    public BlockData getBlockData() {
        return blockData;
    }
}
