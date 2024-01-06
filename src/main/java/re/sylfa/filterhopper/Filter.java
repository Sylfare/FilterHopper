package re.sylfa.filterhopper;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

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

    public static Filter getFromBlock(Hopper hopper) {
        if(hopper.customName() == null) return null;
        if (!getPlainName(hopper.customName()).startsWith("FILTER|"))
            return null;

        return Filter.deserialize(getPlainName(hopper.customName()));
    }

    public static Filter getFromBlock(Inventory destination) {
        if (!(destination.getHolder() instanceof Hopper)) return null;
        return getFromBlock((Hopper) destination.getHolder());
    }

    public static Filter getFromBlock(Block block) {
        if (block.getType() != Material.HOPPER)
            return null;
        return getFromBlock((Hopper) block.getState());
    }

    private static String getPlainName(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    
}
