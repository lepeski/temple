package org.bukkit;

import org.bukkit.block.Block;

public interface World {

    String getName();

    Block getBlockAt(int x, int y, int z);
}
