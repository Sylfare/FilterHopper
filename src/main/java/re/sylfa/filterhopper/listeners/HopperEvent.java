package re.sylfa.filterhopper.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

import re.sylfa.filterhopper.filters.Filter;

public class HopperEvent implements Listener {

    @EventHandler
    public void onHopperTransfer(InventoryMoveItemEvent event) {
        // only filter on hoppers
        if (event.getDestination().getType() != InventoryType.HOPPER)
            return;

        Filter filter = Filter.getFromBlock(event.getDestination());
        // no filter, no need to filter
        if (filter == null)
            return;

        if (!filter.checks(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        Filter filter = Filter.getFromBlock(event.getInventory());
        if (filter == null || filter.typeFilter() == null)
            return;
        if (!filter.checks(event.getItem().getItemStack()))
            event.setCancelled(true);
    }
}
