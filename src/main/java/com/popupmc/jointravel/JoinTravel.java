package com.popupmc.jointravel;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinTravel extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

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
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asl travel-w-command " + e.getPlayer().getName() + " menu-nplayer");
        }
    }
}
