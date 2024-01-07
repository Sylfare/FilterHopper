package re.sylfa.filterhopper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

public record Filter(Material type, boolean invertType, DurabilityFilter durabilityFilter) {

    public static final String SEPARATOR = "|";

    /**
     * Is the item is valid for this filter ?
     * 
     * @param item
     * @return
     */
    public boolean checks(ItemStack item) {
        boolean checkType = (this.type == item.getType()) != this.invertType;
        return checkType && durabilityFilter.checks(item);
    }

    public String serialize() {
        return new StringBuilder("FILTER|")
                .append(this.type.getKey().asString()).append(SEPARATOR)
                .append(invertType ? "true" : "false")
                .append(durabilityFilter.serialize())
                .toString();
    }

    public static Filter deserialize(String string) {
        if (!string.startsWith("FILTER" + SEPARATOR))
            return null;

        List<String> split = Arrays.asList(string.split("\\"+ SEPARATOR))
        .stream().map(value -> value == null ? "" : value).toList();
        
        ArrayList<String> args = new ArrayList<String>(Collections.nCopies(4, ""));
        Collections.copy(args, split);

        Filter filter = new Filter(
                Material.matchMaterial(args.get(1)),
                ObjectUtils.defaultIfNull(args.get(2), "").equalsIgnoreCase("true"),
                ObjectUtils.defaultIfNull(DurabilityFilter.deserialize(args.get(3)),
                        new DurabilityFilter(DurabilityFilter.Type.ANY, 0)));
        return filter;
    }

    public String toString() {
        return this.type == null ? "filtre vide" : this.type.toString();
    }

    public static Filter getFromBlock(Nameable hopper) {
        if (hopper.customName() == null)
            return null;
        if (!getPlainName(hopper.customName()).startsWith("FILTER" + SEPARATOR))
            return null;

        return Filter.deserialize(getPlainName(hopper.customName()));
    }

    public static Filter getFromBlock(Inventory destination) {
        if (!(destination.getHolder() instanceof Hopper || destination.getHolder() instanceof HopperMinecart))
            return null;
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
