package com.popupmc.soloexperience.events;

import com.popupmc.soloexperience.SoloExperience;
import org.bukkit.Bukkit;
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

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!event.getPlayer().hasPlayedBefore()) {
            // New player incoming
            FileConfiguration config = plugin.getConfig();
            Player player = event.getPlayer();

            for(String command : config.getStringList("join commands")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                        .replace("%player%", player.getName())
                        .replace("%world%", player.getWorld().getName()));
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
