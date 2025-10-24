package org.bukkit.scheduler;

public final class BukkitTask {

    private final BukkitRunnable runnable;

    BukkitTask(BukkitRunnable runnable) {
        this.runnable = runnable;
    }

    public void cancel() {
        runnable.cancel();
    }
}
