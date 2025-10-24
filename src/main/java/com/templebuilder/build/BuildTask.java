package com.templebuilder.build;

import com.templebuilder.TempleBuilderPlugin;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.function.Consumer;

public class BuildTask extends BukkitRunnable {

    private static final int BLOCKS_PER_TICK = 400;

    private final TempleBuilderPlugin plugin;
    private final BuildPlan plan;
    private final Consumer<UndoSnapshot> onComplete;
    private int cursor = 0;
    private final UndoSnapshot snapshot;

    public BuildTask(TempleBuilderPlugin plugin, BuildPlan plan, Consumer<UndoSnapshot> onComplete) {
        this.plugin = plugin;
        this.plan = plan;
        this.onComplete = onComplete;
        this.snapshot = new UndoSnapshot(plan.getWorld());
    }

    @Override
    public void run() {
        World world = plan.getWorld();
        List<BlockPlacement> placements = plan.getPlacements();
        int placedThisTick = 0;
        while (cursor < placements.size() && placedThisTick < BLOCKS_PER_TICK) {
            BlockPlacement placement = placements.get(cursor++);
            Block block = world.getBlockAt(placement.getX(), placement.getY(), placement.getZ());
            BlockData previous = block.getBlockData().clone();
            snapshot.add(block.getX(), block.getY(), block.getZ(), previous);
            block.setBlockData(placement.getBlockData(), false);
            placedThisTick++;
        }
        if (cursor >= placements.size()) {
            cancel();
            onComplete.accept(snapshot);
        }
    }
}
