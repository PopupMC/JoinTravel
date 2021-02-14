package com.popupmc.jointravel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import static org.bukkit.ChatColor.RED;

import java.util.Objects;

public class JoinTravel extends JavaPlugin implements Listener, CommandExecutor {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("jt-continue")).setExecutor(this);

        // Log enabled status
        getLogger().info("JoinTravel is enabled.");
    }

    // Log disabled status
    @Override
    public void onDisable() {
        getLogger().info("JoinTravel is disabled");
    }

    @EventHandler
    public void onPlayerJoinEvent (PlayerJoinEvent e) {
        if(!e.getPlayer().hasPlayedBefore()) {
            // New player incoming

            // Set time to day so they dont spawn at night
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day main");

            // Remove weather effects
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather main sun");

            // Teleport randomly in main world
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asl travel-w-command " + e.getPlayer().getName() + " jt-continue " + e.getPlayer().getName());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("jt.continue")) {
            sender.sendMessage(RED + "You don't have permission to use that command");
            return false;
        }

        if(args.length != 1) {
            sender.sendMessage(RED + "Wrong arguments");
            getLogger().info("Wrong Arguments");
            return false;
        }

        String playerName = args[0];
        Player p = Bukkit.getPlayer(playerName);

        if(p == null) {
            sender.sendMessage(RED + "Can't find that player");
            getLogger().info("Can't find that player");
            return false;
        }

        // Offset x by 2
        Location location = p.getLocation().clone();
        location.setX(location.getX() - 2);

        // Place chest there
        placeBlock(location, "minecraft:chest", "LootTable:\"chests/spawn_bonus_chest\"");

        // Place torch +X +Z
        Location torchLocation = location.clone();
        torchLocation.setX(torchLocation.getX() + 1);
        torchLocation.setZ(torchLocation.getZ() + 1);
        placeBlock(torchLocation, "minecraft:torch");

        // Place torch +X -Z
        torchLocation = location.clone();
        torchLocation.setX(torchLocation.getX() + 1);
        torchLocation.setZ(torchLocation.getZ() - 1);
        placeBlock(torchLocation, "minecraft:torch");

        // Place torch -X +Z
        torchLocation = location.clone();
        torchLocation.setX(torchLocation.getX() - 1);
        torchLocation.setZ(torchLocation.getZ() + 1);
        placeBlock(torchLocation, "minecraft:torch");

        // Place torch -X -Z
        torchLocation = location.clone();
        torchLocation.setX(torchLocation.getX() - 1);
        torchLocation.setZ(torchLocation.getZ() - 1);
        placeBlock(torchLocation, "minecraft:torch");


        // Make block above air
        Location airLocation = location.clone();
        airLocation.setY(location.getY() + 1);
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                Location airLocationOffset = airLocation.clone();
                airLocationOffset.setX(airLocation.getX() + x);
                airLocationOffset.setZ(airLocation.getZ() + z);

                placeBlock(airLocationOffset, "minecraft:air");
            }
        }

        return true;
    }

    public void placeBlock(Location location, String item) {
        placeBlock(location, item, null);
    }

    public void placeBlock(Location location, String item, String nbt) {
        if(nbt != null)
            nbt = "{" + nbt + "}";
        else
            nbt = "";

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "setblock " +
                        location.getBlockX() + " " +
                        location.getBlockY() + " " +
                        location.getBlockZ() + " " +
                        item +
                        nbt +
                        " replace");
    }
}
