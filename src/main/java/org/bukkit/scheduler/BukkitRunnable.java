package org.bukkit.scheduler;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitRunnable implements Runnable {

    private boolean cancelled;

    public abstract void run();

    public BukkitTask runTaskTimer(JavaPlugin plugin, long delay, long period) {
        // Immediate execution for testing purposes.
        run();
        return new BukkitTask(this);
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
