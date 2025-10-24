package com.templebuilder.build;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

public class UndoSnapshot {

    private final World world;
    private final List<BlockRestore> blocks = new ArrayList<>();

    public UndoSnapshot(World world) {
        this.world = world;
    }

    public void add(int x, int y, int z, BlockData previous) {
        blocks.add(new BlockRestore(x, y, z, previous));
    }

    public int size() {
        return blocks.size();
    }

    public void restore() {
        for (BlockRestore restore : blocks) {
            Block block = world.getBlockAt(restore.x, restore.y, restore.z);
            block.setBlockData(restore.data, false);
        }
    }

    private static final class BlockRestore {
        private final int x;
        private final int y;
        private final int z;
        private final BlockData data;

        private BlockRestore(int x, int y, int z, BlockData data) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.data = data.clone();
        }
    }
}
