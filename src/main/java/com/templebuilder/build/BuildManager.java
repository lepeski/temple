package com.templebuilder.build;

import com.templebuilder.TempleBuilderPlugin;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BuildManager {

    private final TempleBuilderPlugin plugin;
    private final Map<UUID, BuildTask> activeTasks = new HashMap<>();
    private final Deque<UndoSnapshot> undoHistory = new ArrayDeque<>();
    private BuildPlan lastPlan;

    public BuildManager(TempleBuilderPlugin plugin) {
        this.plugin = plugin;
    }

    public void queueBuild(CommandSender sender, BuildPlan plan) {
        if (plan == null || plan.getPlacements().isEmpty()) {
            sender.sendMessage("§cTemple plan is empty; nothing to build.");
            return;
        }
        World world = plan.getWorld();
        if (world == null) {
            sender.sendMessage("§cBuild plan has no world set.");
            return;
        }

        Player player = sender instanceof Player ? (Player) sender : null;
        UUID uuid = player != null ? player.getUniqueId() : new UUID(0L, 0L);
        cancelExisting(uuid);
        lastPlan = plan;
        BuildTask task = new BuildTask(plugin, plan, snapshot -> {
            undoHistory.push(snapshot);
            sender.sendMessage("§aTemple build complete. Blocks placed: " + snapshot.size());
            activeTasks.remove(uuid);
        });
        activeTasks.put(uuid, task);
        task.runTaskTimer(plugin, 1L, 1L);
        sender.sendMessage("§6Starting temple construction: " + plan.getPlacements().size() + " blocks queued.");
    }

    private void cancelExisting(UUID uuid) {
        BuildTask existing = activeTasks.remove(uuid);
        if (existing != null) {
            existing.cancel();
        }
    }

    public void undoLast(CommandSender sender) {
        if (undoHistory.isEmpty()) {
            sender.sendMessage("§cNo temple builds to undo.");
            return;
        }
        UndoSnapshot snapshot = undoHistory.pop();
        snapshot.restore();
        sender.sendMessage("§eUndo complete. Restored " + snapshot.size() + " blocks.");
    }

    public void clearHistory() {
        undoHistory.clear();
    }

    public BuildPlan getLastPlan() {
        return lastPlan;
    }
}
