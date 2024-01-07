package re.sylfa.filterhopper.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import re.sylfa.filterhopper.FilterHopper;

public class Log {
    private static CommandSender cs = Bukkit.getConsoleSender();

    public static void log(Component text) {
        cs.sendMessage(FilterHopper.prefix.append(text));
    }

    public static void log(String text) {
        log(Component.text(text));
    }

    public static void debug(Component text) {
        Bukkit.broadcast(text);
    }

    public static void debugItem(ItemStack item, Component message) {
        Bukkit.broadcast(message.append(
                Component.text(item.toString())
                        .hoverEvent(item.asHoverEvent())
                        .color(NamedTextColor.GREEN)));
    }

    public static void debugItem(ItemStack item) {
        debugItem(item, Component.empty());
    }
}
