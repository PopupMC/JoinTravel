package com.popupmc.soloexperience.commands;

import com.popupmc.soloexperience.SoloExperience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private final SoloExperience plugin;

    public MainCommand(SoloExperience plugin){
        this.plugin = plugin;
    }

    private void send(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")+" "+msg));
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0 || args[0].equalsIgnoreCase("help")){
            send(sender, "&6List of commands");
            send(sender, "&f/"+label+" help");
            send(sender, "&f/"+label+" version");
            send(sender, "&f/"+label+" reload");
            send(sender, "&f/"+label+" chest (player)");


        }else if(args[0].equalsIgnoreCase("version")){
            if(!sender.hasPermission("soloExperience.version")){
                send(sender, "&cNo permission");
                return true;
            }
            send(sender, "&fVersion: "+plugin.getDescription().getVersion());


        }else if(args[0].equalsIgnoreCase("reload")){
            if(!sender.hasPermission("soloExperience.reload")){
                send(sender, "&cNo permission");
                return true;
            }
            plugin.reloadConfig();
            send(sender, "&aFiles reloaded!");


        }else if(args[0].equalsIgnoreCase("chest")){
            if(!sender.hasPermission("soloExperience.chest")){
                send(sender, "&cNo permission");
                return true;
            }
            if(args.length < 2){
                send(sender, "&cUse: &f/"+label+" chest (player)");
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null){
                send(sender, "&cThat player does not exist or is not online.");
                return true;
            }
            plugin.spawnChest(player);



        }else{
            send(sender, "&cUnknown command. &fTry /"+label+" help");
        }


        return true;
    }
}
