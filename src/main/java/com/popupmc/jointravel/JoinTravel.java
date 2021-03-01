package com.popupmc.jointravel;

import com.popupmc.jointravel.commands.MainCommand;
import com.popupmc.jointravel.events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinTravel extends JavaPlugin {

    /**
     * Sends a message to the console with prefix included.
     * @param message The message to be sent.
     */
    private void send(String message){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix")+" "+message));
    }

    /**
     * Plugin enable logic.
     */
    @Override
    public void onEnable() {
        registerConfig();
        registerCommand();
        registerEvent();

        send("&fStatus: &aEnabled");
        send("&6JoinTravel &fby junebug12851 and lelesape");
        send("&fJoin PopUpMC discord server here: &chttps://discord.gg/ru3Hk9Vfny");
    }

    /**
     * Plugin disable logic.
     */
    @Override
    public void onDisable() {
        send("&fStatus: &cDisabled&f. Thank you for using this plugin.");
        send("&6AprilFoolsDay &fby junebug12851 and lelesape");
        send("&fJoin PopUpMC discord server here: &chttps://discord.gg/ru3Hk9Vfny");
    }

    /**
     * Registers the config file.
     */
    private void registerConfig(){
        saveDefaultConfig();
    }

    /**
     * Register the main command for this plugin.
     */
    private void registerCommand(){
        PluginCommand mainCommand = getCommand("joinTravel");

        if(mainCommand == null){
            send("&cCould not get main command. Please check your plugin.yml file is intact.");
            send("&cDisabling JoinTravel");
            this.setEnabled(false);
            return;
        }

        mainCommand.setExecutor(new MainCommand(this));
    }

    /**
     * Registers the PlayerJoinEvent for this plugin.
     */
    private void registerEvent(){
        Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
    }




}
