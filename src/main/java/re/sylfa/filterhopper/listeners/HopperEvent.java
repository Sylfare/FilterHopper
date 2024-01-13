package re.sylfa.filterhopper.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import re.sylfa.filterhopper.filters.Filter;

public class HopperEvent implements Listener {

    @EventHandler
    public void onHopperTransfer(InventoryMoveItemEvent event) {
        // only filter on hoppers
        if (!event.getDestination().getType().equals(InventoryType.HOPPER))
            return;

        Filter filter = Filter.getFromBlock(event.getDestination());
        // no filter, no need to filter
        if (filter == null)
            return;

        int slot = getFirstCorrespondingSlot(event.getSource(), filter);
        if (slot == -1) {
            event.setCancelled(true);
            return;
        }
        
        ItemStack item = event.getSource().getItem(slot);
        
        @NotNull
        ItemStack firstCheckedItem = event.getItem();
        int firstSlot = event.getSource().first(firstCheckedItem);
        if (slot == firstSlot) {
            return;    
        }
        event.setCancelled(true);
        if(item.getType().equals(Material.AIR)) return;

        ItemStack oneItem = new ItemStack(item).asOne();
        var result = event.getDestination().addItem(item.asOne());
        if(result.size() == 0) {
            event.getSource().getItem(slot).add(-1);         
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

    public int getFirstCorrespondingSlot(Inventory inventory, Filter filter) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType().equals(Material.AIR))
                continue;

            if (filter.checks(item))
                return i;
        }
        return -1;
    }
}
