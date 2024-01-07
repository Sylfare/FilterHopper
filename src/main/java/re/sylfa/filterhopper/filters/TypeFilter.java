package re.sylfa.filterhopper.filters;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record TypeFilter(Material type, boolean inverted) {
    public boolean checks(ItemStack item) {
        if (type == null)
            return true;

        return item.getType().equals(type) != inverted;
    }

    public String serialize() {
        return new StringBuilder()
        .append(type.name()).append("|")
        .append(inverted ? "true" : "false").toString();
    }

    public static TypeFilter deserialize(String material, String inverted) {
        Material matchMaterial = Material.matchMaterial(material);
        if (matchMaterial == null)
            matchMaterial = Material.AIR;
        return new TypeFilter(matchMaterial, inverted == "true");
    }
}
