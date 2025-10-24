package com.templebuilder.build;

import com.templebuilder.TempleBuilderPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class SchematicExporter {

    private SchematicExporter() {
    }

    public static void export(TempleBuilderPlugin plugin, BuildPlan plan, String name) throws IOException {
        File folder = new File(plugin.getDataFolder(), "schematics");
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("Unable to create schematics directory: " + folder.getAbsolutePath());
        }
        File file = new File(folder, name + ".yml");

        int minX = plan.getPlacements().stream().mapToInt(BlockPlacement::getX).min().orElse(0);
        int minY = plan.getPlacements().stream().mapToInt(BlockPlacement::getY).min().orElse(0);
        int minZ = plan.getPlacements().stream().mapToInt(BlockPlacement::getZ).min().orElse(0);

        List<String> serialized = new ArrayList<>();
        plan.getPlacements().stream()
                .sorted(Comparator
                        .comparingInt(BlockPlacement::getY)
                        .thenComparingInt(BlockPlacement::getZ)
                        .thenComparingInt(BlockPlacement::getX))
                .forEach(placement -> {
                    int rx = placement.getX() - minX;
                    int ry = placement.getY() - minY;
                    int rz = placement.getZ() - minZ;
                    String blockData = placement.getBlockData().getAsString();
                    serialized.add(rx + "," + ry + "," + rz + "," + blockData);
                });

        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("origin.x", minX);
        yaml.set("origin.y", minY);
        yaml.set("origin.z", minZ);
        yaml.set("world", plan.getWorld().getName());
        yaml.set("blocks", serialized);
        yaml.save(file);
    }
}
