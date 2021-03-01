package com.popupmc.jointravel.events;

import com.popupmc.jointravel.JoinTravel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final JoinTravel plugin;

    public JoinEvent(JoinTravel plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!event.getPlayer().hasPlayedBefore()) {
            // New player incoming

            // Set time to day so they dont spawn at night
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day main");

            // Remove weather effects
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather main sun");

            // Teleport randomly in main world
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asl travel-w-command " + event.getPlayer().getName() + " jt-continue " + event.getPlayer().getName());
        }

    }
}
