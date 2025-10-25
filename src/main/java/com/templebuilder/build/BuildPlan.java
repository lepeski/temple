package com.templebuilder.build;

import org.bukkit.World;

import java.util.Collections;
import java.util.List;

public class BuildPlan {

    private final World world;
    private final List<BlockPlacement> placements;

    public BuildPlan(World world, List<BlockPlacement> placements) {
        this.world = world;
        this.placements = placements;
    }

    public World getWorld() {
        return world;
    }

    public List<BlockPlacement> getPlacements() {
        return Collections.unmodifiableList(placements);
    }
}
