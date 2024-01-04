package re.sylfa.filterhopper;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import re.sylfa.filterhopper.listeners.HopperEvent;

public class FilterHopper extends JavaPlugin {
    
    static CommandSender cs = Bukkit.getConsoleSender();
    static Component prefix = Component.text("[FilterHopper] ");
    private static PluginManager pm = Bukkit.getPluginManager();
   

    @Override
    public void onEnable() {
        pm.registerEvents(new HopperEvent(), this);


        Log.log(Component.text("Enabled").color(NamedTextColor.GREEN));
    }


    @Override
    public void onDisable() {
        Log.log(Component.text("Disabled").color(NamedTextColor.RED));
    }
}