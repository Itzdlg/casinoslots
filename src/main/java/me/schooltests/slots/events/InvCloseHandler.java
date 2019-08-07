package me.schooltests.slots.events;

import me.schooltests.slots.Slots;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InvCloseHandler implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(Slots.getInstance().getAPI().getInSlots().contains(event.getPlayer().getUniqueId())) {
            final Inventory inv = event.getInventory();
            final HumanEntity plr = event.getPlayer();
            new BukkitRunnable() {
                public void run() {
                    plr.openInventory(inv);
                }
            }.runTaskLater(Slots.getInstance(), 1L);
        }
    }
}
