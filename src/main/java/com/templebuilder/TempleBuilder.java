package com.templebuilder;

import com.templebuilder.build.BlockPlacement;
import com.templebuilder.build.BuildPlan;
import com.templebuilder.geometry.BlockPos;
import com.templebuilder.geometry.BlockVector;
import com.templebuilder.geometry.Blueprint;
import com.templebuilder.geometry.TempleBlueprintGenerator;
import com.templebuilder.palette.Palette;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class TempleBuilder {

    private static final double DEFAULT_LIGHT_DENSITY = 0.6;

    private TempleBuilder() {
    }

    public static BuildPlan build(World world, BlockPos origin, double scale, Palette palette, TempleStyle style) {
        TempleBlueprintGenerator generator = new TempleBlueprintGenerator();
        Blueprint blueprint = generator.generate(scale, palette, style, DEFAULT_LIGHT_DENSITY);
        int shiftZ = -blueprint.minZ();
        List<BlockPlacement> placements = new ArrayList<>();
        for (Map.Entry<BlockVector, org.bukkit.Material> entry : blueprint.entries()) {
            BlockVector vector = entry.getKey();
            Material material = entry.getValue();
            BlockData data = Bukkit.createBlockData(material);
            int worldX = origin.getX() + vector.getX();
            int worldY = origin.getY() + vector.getY();
            int worldZ = origin.getZ() + vector.getZ() + shiftZ;
            placements.add(new BlockPlacement(worldX, worldY, worldZ, data));
        }
        placements.sort(Comparator
                .comparingInt(BlockPlacement::getY)
                .thenComparingInt(BlockPlacement::getZ)
                .thenComparingInt(BlockPlacement::getX));
        return new BuildPlan(world, placements);
    }
}
