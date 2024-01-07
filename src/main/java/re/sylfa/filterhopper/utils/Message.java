package re.sylfa.filterhopper.utils;

import org.bukkit.entity.Player;

public class Message {
    public static void actionBar(Player player, String messageId) {
        player.sendActionBar(I18n.get(messageId));
    }

}
