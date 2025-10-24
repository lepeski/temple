package org.bukkit.plugin.java;

import org.bukkit.command.PluginCommand;

import java.io.File;
import java.util.logging.Logger;

public abstract class JavaPlugin {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final File dataFolder = new File("build/tmp/" + getClass().getSimpleName());

    public JavaPlugin() {
        if (!dataFolder.exists()) {
            // no-op if creation fails; operations will throw later
            dataFolder.mkdirs();
        }
    }

    public void onEnable() {
        // lifecycle hook for subclasses
    }

    public PluginCommand getCommand(String name) {
        return new PluginCommand(name);
    }

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}
