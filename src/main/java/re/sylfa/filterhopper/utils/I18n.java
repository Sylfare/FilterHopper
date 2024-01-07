package re.sylfa.filterhopper.utils;

import net.kyori.adventure.text.Component;
import re.sylfa.filterhopper.FilterHopper;

public class I18n {

    public static Component get(String id){
        return Component.translatable("sylfare.filterhopper." + id, getConfig(id));
    }

    public static String getConfig(String id){
        return FilterHopper.plugin.getConfig().getString("i18n."+id, id);
    }
}
