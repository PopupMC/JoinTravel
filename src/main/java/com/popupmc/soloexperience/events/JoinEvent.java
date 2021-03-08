package com.popupmc.soloexperience.events;

import com.popupmc.soloexperience.SoloExperience;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinEvent implements Listener {

    private final SoloExperience plugin;

    public JoinEvent(SoloExperience plugin){
        this.plugin = plugin;
    }

    /**
     * Logs an executed command to console.
     * @param cmd The command being executed.
     */
    private void logCommand(String cmd){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")+" &eExecuting cmd: &f"+cmd));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!event.getPlayer().hasPlayedBefore()) {
            // New player incoming
            FileConfiguration config = plugin.getConfig();
            Player player = event.getPlayer();
            boolean logCommands = config.getBoolean("log commands to console");

            for(String command : config.getStringList("join commands")){
                command = command.replace("%player%", player.getName()).replace("%world%", player.getWorld().getName());
                if(logCommands) logCommand(command);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }

            int delay = config.getInt("chest spawn delay");

            if(delay > 0){
                new BukkitRunnable(){
                    public void run(){
                        plugin.spawnChest(player);
                    }
                }.runTaskLater(plugin, delay);
            }

        }
    }

}
