package org.bukkit;

import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.SimpleBlockData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Bukkit {

    private static final List<World> WORLDS = new ArrayList<>();

    private Bukkit() {
    }

    public static BlockData createBlockData(Material material) {
        return new SimpleBlockData(material);
    }

    public static List<World> getWorlds() {
        return Collections.unmodifiableList(WORLDS);
    }

    public static void registerWorld(World world) {
        if (!WORLDS.contains(world)) {
            WORLDS.add(world);
        }
    }
}
