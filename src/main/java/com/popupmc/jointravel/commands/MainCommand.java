package com.popupmc.jointravel.commands;

import com.popupmc.jointravel.JoinTravel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private final JoinTravel plugin;

    public MainCommand(JoinTravel plugin){
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("jt.continue")) {
            sender.sendMessage("You don't have permission to use that command");
            return false;
        }

        if(args.length != 1) {
            sender.sendMessage("Wrong arguments");
//            getLogger().info("Wrong Arguments");
            return false;
        }

        String playerName = args[0];
        Player p = Bukkit.getPlayer(playerName);

        if(p == null) {
            sender.sendMessage("Can't find that player");
//            getLogger().info("Can't find that player");
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
