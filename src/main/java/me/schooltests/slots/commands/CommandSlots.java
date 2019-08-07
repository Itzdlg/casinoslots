package me.schooltests.slots.commands;

import me.schooltests.slots.API;
import me.schooltests.slots.Slots;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSlots implements CommandExecutor {
    private Slots plugin = Slots.getInstance();
    private API api = plugin.getAPI();
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(api.canUseSlots(player)) {
            if (args.length > 0) {
                int amount = NumberUtils.toInt(args[0], 1000);
                if (amount > 100000) {
                    player.sendMessage(api.getPrefix() + amount + ChatColor.AQUA + " is too high! $1000 - $100,000");
                } else if (amount < 1000) {
                    player.sendMessage(api.getPrefix() + amount + ChatColor.AQUA + " is too low! $1000 - $100,000");
                } else {
                    if (api.getEcon().getBalance(player) >= amount) {
                        // Has enough money
                        api.addToSlotsUsed(player);
                        api.openSlotsGUI(player, amount);
                        api.getEcon().withdrawPlayer(player, amount);
                    } else {
                        player.sendMessage(api.getPrefix() + ChatColor.AQUA + "You do not have $" + ChatColor.WHITE + amount);
                    }
                }
            } else {
                player.sendMessage(api.getPrefix() + ChatColor.AQUA + "/slots <amount>");
            }
        } else {
            player.sendMessage(api.getPrefix() + ChatColor.AQUA + "You may not use /slots again today!");
        }

        return true;
    }
}
