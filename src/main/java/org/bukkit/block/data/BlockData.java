package org.bukkit.block.data;

public interface BlockData extends Cloneable {

    BlockData clone();

    String getAsString();
}
