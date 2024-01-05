package re.sylfa.filterhopper;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record Filter(Material type) {
    /**
     * Is the item is valid for this filter ?
     * 
     * @param item
     * @return
     */
    public boolean checks(ItemStack item) {
        return this.type == item.getType();
    }

    public String serialize() {
        return new StringBuilder("FILTER|")
                .append(this.type.getKey().asString())
                .toString();
    }

    public static Filter deserialize(String string) {
        if (!string.startsWith("FILTER|"))
            return null;

        String[] args = string.split("\\|");
        return new Filter(Material.matchMaterial(args[1]));
    }

    public String toString() {
        return this.type == null ? "filtre vide" : this.type.toString();
    }

}
