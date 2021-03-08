package com.popupmc.soloexperience;

import com.popupmc.soloexperience.commands.MainCommand;
import com.popupmc.soloexperience.events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoloExperience extends JavaPlugin {

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
        PluginCommand mainCommand = getCommand("soloExperience");

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


    /**
     * Spawns a bonus chest for teh given player.
     * @param player The player to spawn the bonus chest for.
     */
    public void spawnChest(Player player){

        Location chestLoc = player.getLocation().clone().add(-2,0,0);

        chestLoc.getBlock().setType(Material.CHEST);

        Chest chest;

        if(getConfig().getBoolean("vanilla contents")) {
            chest = (Chest) chestLoc.getBlock().getState();
            chest.setLootTable(LootTables.SPAWN_BONUS_CHEST.getLootTable());
        }else {
            chest = (Chest) chestLoc.getBlock().getState();
            Inventory chestInv = chest.getInventory();
            List<ItemStack> items = new ArrayList<>();
            getConfig().getStringList("chest contents").forEach(i -> items.add(
                    new ItemStack(Material.valueOf(i.split(",")[0]), Integer.parseInt(i.split(",")[1]))
            ));

            Random r = new Random();
            for (int i = 0; i < 27; i++) {
                if(r.nextBoolean()) {
                    chestInv.setItem(i, items.get(r.nextInt(items.size())));
                }
            }
        }
        chest.update();

        //torches
        chestLoc.clone().add(0,0,1).getBlock().setType(Material.TORCH);
        chestLoc.clone().add(0,0,-1).getBlock().setType(Material.TORCH);
        chestLoc.clone().add(1,0,0).getBlock().setType(Material.TORCH);
        chestLoc.clone().add(-1,0,0).getBlock().setType(Material.TORCH);

        // Make block above air
        Location airLocation = chestLoc.clone();
        airLocation.add(0,1,0);
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                airLocation.clone().add(x,0,z).getBlock().setType(Material.AIR);
            }
        }

    }


}
