package com.templebuilder;

import com.templebuilder.build.BuildManager;
import com.templebuilder.command.TempleCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TempleBuilderPlugin extends JavaPlugin {

    private BuildManager buildManager;

    @Override
    public void onEnable() {
        this.buildManager = new BuildManager(this);
        TempleCommand templeCommand = new TempleCommand(this, buildManager);
        PluginCommand command = getCommand("temple");
        if (command != null) {
            command.setExecutor(templeCommand);
            command.setTabCompleter(templeCommand);
        } else {
            getLogger().severe("Command /temple is not defined in plugin.yml");
        }
    }

    public BuildManager getBuildManager() {
        return buildManager;
    }
}
