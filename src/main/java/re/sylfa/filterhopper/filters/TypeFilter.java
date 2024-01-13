package re.sylfa.filterhopper.filters;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record TypeFilter(Material type, boolean inverted) {
    public boolean checks(ItemStack item) {
        boolean result;
        if (type == null || type.equals(Material.AIR))
            result = true;
        else {
            result = item.getType().equals(type);
        }

        if(inverted) result = !result;
        return result;

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
        return new TypeFilter(matchMaterial, inverted.equalsIgnoreCase("true"));
    }
}
