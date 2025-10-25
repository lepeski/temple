package org.bukkit.entity;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public interface Player extends CommandSender {

    World getWorld();

    UUID getUniqueId();
}
