package re.sylfa.filterhopper;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record Filter (Material type){
    public String serialize(){
        return new StringBuilder("FILTER|")
            .append(this.type.getKey().asString())
            .toString();
    }

    /**
     * Is the item is valid for this filter ?
     * @param item
     * @return
     */
    public boolean checks(ItemStack item){
        return this.type == item.getType();
    }
}
