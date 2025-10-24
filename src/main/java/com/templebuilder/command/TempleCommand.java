package com.templebuilder.command;

import com.templebuilder.TempleBuilder;
import com.templebuilder.TempleBuilderPlugin;
import com.templebuilder.TempleStyle;
import com.templebuilder.build.BuildManager;
import com.templebuilder.build.BuildPlan;
import com.templebuilder.build.SchematicExporter;
import com.templebuilder.geometry.BlockPos;
import com.templebuilder.palette.Palette;
import com.templebuilder.palette.PaletteRegistry;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TempleCommand implements CommandExecutor, TabCompleter {

    private final TempleBuilderPlugin plugin;
    private final BuildManager buildManager;

    public TempleCommand(TempleBuilderPlugin plugin, BuildManager buildManager) {
        this.plugin = plugin;
        this.buildManager = buildManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6TempleBuilder §7- §f/temple build <x> <y> <z> [scale] [palette]");
            sender.sendMessage("§7Other commands: /temple undo, /temple schem <name>");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "build":
                return executeBuild(sender, Arrays.copyOfRange(args, 1, args.length));
            case "undo":
                buildManager.undoLast(sender);
                return true;
            case "schem":
                return executeSchematic(sender, Arrays.copyOfRange(args, 1, args.length));
            default:
                sender.sendMessage("§cUnknown subcommand. Use /temple build, /temple undo, or /temple schem.");
                return true;
        }
    }

    private boolean executeBuild(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /temple build <x> <y> <z> [scale] [palette]");
            return true;
        }
        int x;
        int y;
        int z;
        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            sender.sendMessage("§cCoordinates must be integers.");
            return true;
        }
        double scale = 1.0;
        if (args.length >= 4) {
            try {
                scale = Double.parseDouble(args[3]);
            } catch (NumberFormatException ex) {
                sender.sendMessage("§cScale must be a number.");
                return true;
            }
        }
        String paletteKey = args.length >= 5 ? args[4] : "default";
        Palette palette = PaletteRegistry.get(paletteKey);
        World world = getWorldForSender(sender);
        if (world == null) {
            sender.sendMessage("§cCould not determine a world to build in.");
            return true;
        }
        BlockPos origin = new BlockPos(x, y, z);
        BuildPlan plan = TempleBuilder.build(world, origin, scale, palette, TempleStyle.GRAND);
        buildManager.queueBuild(sender, plan);
        return true;
    }

    private boolean executeSchematic(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /temple schem <name>");
            return true;
        }
        BuildPlan plan = buildManager.getLastPlan();
        if (plan == null) {
            sender.sendMessage("§cNo build has been executed yet; nothing to export.");
            return true;
        }
        String name = args[0].replaceAll("[^a-zA-Z0-9-_]", "_");
        try {
            SchematicExporter.export(plugin, plan, name);
            sender.sendMessage("§aSaved schematic as " + name + ".yml in the plugin schematics folder.");
        } catch (IOException ex) {
            sender.sendMessage("§cFailed to save schematic: " + ex.getMessage());
            ex.printStackTrace();
        }
        return true;
    }

    private World getWorldForSender(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender).getWorld();
        }
        List<World> worlds = Bukkit.getWorlds();
        return worlds.isEmpty() ? null : worlds.get(0);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("build", "undo", "schem"), new ArrayList<>());
        }
        if (args.length == 5 && args[0].equalsIgnoreCase("build")) {
            return StringUtil.copyPartialMatches(args[4], Arrays.asList("default", "alt"), new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
