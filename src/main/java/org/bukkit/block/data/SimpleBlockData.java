package org.bukkit.block.data;

import org.bukkit.Material;

public final class SimpleBlockData implements BlockData {

    private final Material material;

    public SimpleBlockData(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public BlockData clone() {
        return new SimpleBlockData(material);
    }

    @Override
    public String getAsString() {
        return material.name().toLowerCase();
    }

    @Override
    public String toString() {
        return getAsString();
    }
}
