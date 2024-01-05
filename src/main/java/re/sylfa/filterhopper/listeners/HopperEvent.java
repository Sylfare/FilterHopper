package re.sylfa.filterhopper.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

import re.sylfa.filterhopper.Filter;

public class HopperEvent implements Listener {

    // DEBUG
    Filter debugFilter = Filter.deserialize("FILTER|DIAMOND_BLOCK");
    
    @EventHandler
    public void onHopperTransfer(InventoryMoveItemEvent event) {
        // only filter on hoppers

        if (event.getDestination().getType() != InventoryType.HOPPER) return;

        // no filter, no need to filter
        if (debugFilter.type() == null)
            return;
         
        if (!debugFilter.checks(event.getItem())) {
            event.setCancelled(true);
        }
    }
}
