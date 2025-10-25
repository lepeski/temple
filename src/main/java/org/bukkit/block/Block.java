package org.bukkit.block;

import org.bukkit.block.data.BlockData;

public interface Block {

    int getX();

    int getY();

    int getZ();

    BlockData getBlockData();

    void setBlockData(BlockData data, boolean applyPhysics);
}
