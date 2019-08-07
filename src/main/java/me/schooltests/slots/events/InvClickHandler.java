package me.schooltests.slots.events;

import me.schooltests.slots.Slots;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickHandler implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(Slots.getInstance().getAPI().getInSlots().contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        } else {
            if(event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null) {
                if(event.getClickedInventory().getTitle().equals(ChatColor.GREEN + "You win!") || event.getClickedInventory().getTitle().equals(ChatColor.RED + "You lose!")) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
