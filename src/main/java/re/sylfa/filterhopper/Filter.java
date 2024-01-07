package re.sylfa.filterhopper;

import java.util.Arrays;

import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public record Filter(Material type, boolean invertType) {

    public static final String SEPARATOR = "|";

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
                .append(this.type.getKey().asString()).append(SEPARATOR)
                .append(0)
                .toString();
    }

    public static Filter deserialize(String string) {
        if (!string.startsWith("FILTER"+SEPARATOR))
            return null;

        String[] args = Arrays.copyOf(string.split("\\"+SEPARATOR), 3);

        Filter filter = new Filter(
            Material.matchMaterial(args[1]),
            ObjectUtils.defaultIfNull(args[2], "").equalsIgnoreCase("true")
        );
        return filter;
    }

    public String toString() {
        return this.type == null ? "filtre vide" : this.type.toString();
    }

    public static Filter getFromBlock(Nameable hopper) {
        if(hopper.customName() == null) return null;
        if (!getPlainName(hopper.customName()).startsWith("FILTER"+SEPARATOR))
            return null;

        return Filter.deserialize(getPlainName(hopper.customName()));
    }

    public static Filter getFromBlock(Inventory destination) {
        if (!(destination.getHolder() instanceof Hopper || destination.getHolder() instanceof HopperMinecart)) return null;
        return getFromBlock((Nameable) destination.getHolder());
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
