package me.schooltests.slots;

import me.schooltests.slots.util.DelayCounter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

public class API {
    private Slots plugin;
    private Economy econ;
    private List<UUID> inSlots = new ArrayList<UUID>();
    private Map<UUID, Integer> slotsUsed = new HashMap<UUID, Integer>();
    private File configFile;
    private YamlConfiguration config = new YamlConfiguration();
    public API(Slots plugin, Economy econ) {
        this.plugin = plugin;
        this.econ = econ;

        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canUseSlots(Player player) {
        if(slotsUsed.containsKey(player.getUniqueId())) {
            if (slotsUsed.get(player.getUniqueId()) < 5) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void addToSlotsUsed(Player player) {
        if(canUseSlots(player)) {
            if(slotsUsed.containsKey(player.getUniqueId())) {
                slotsUsed.put(player.getUniqueId(), slotsUsed.get(player.getUniqueId()) + 1);
            } else {
                slotsUsed.put(player.getUniqueId(), 1);
            }
        }
    }

    public void clearSlots() {
        slotsUsed.clear();
    }

    public void addInSlots(Player player) {
        if(!inSlots.contains(player.getUniqueId())) inSlots.add(player.getUniqueId());
    }

    public void removeFromSlots(Player player) {
        inSlots.remove(player.getUniqueId());
    }

    public List<UUID> getInSlots() {
        return inSlots;
    }

    public Economy getEcon() {
        return econ;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix")) + " " + ChatColor.WHITE;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void openSlotsGUI(Player player, int money) {
        addInSlots(player);
        double winnings;
        final Random random = new Random();
        int chance = random.nextInt(3);
        Double[] multipliers = {1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0};
        int m = random.nextInt(multipliers.length);
        winnings = money * multipliers[m];
        int div = random.nextInt(5);
        if(div == 1) { winnings = (winnings * .8); }

        ItemStack panel = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta = panel.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "");
        panel.setItemMeta(meta);

        ItemStack panel1 = panel.clone();
        panel1.setDurability((short) 1);
        ItemStack panel2 = panel.clone();
        panel2.setDurability((short) 3);
        ItemStack panel3 = panel.clone();
        panel3.setDurability((short) 4);
        ItemStack panel4 = panel.clone();
        panel4.setDurability((short) 5);
        ItemStack panel5= panel.clone();
        panel5.setDurability((short) 9);
        ItemStack panel6 = panel.clone();
        panel6.setDurability((short) 6);

        final List<ItemStack> panels = Arrays.asList(panel1, panel2, panel3, panel4, panel5, panel6);
        final Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui_name")));
        for(int i = 0; i < 27; i++) {
            inv.setItem(i, panels.get(random.nextInt(panels.size())));
        }
        ItemStack item1 = new ItemStack(Material.DIRT);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName(ChatColor.AQUA + "");
        item1.setItemMeta(meta1);

        ItemStack item2 = new ItemStack(Material.STONE);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName(ChatColor.AQUA + "");
        item2.setItemMeta(meta2);

        ItemStack item3 = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName(ChatColor.AQUA + "");
        item3.setItemMeta(meta3);

        ItemStack item4 = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName(ChatColor.AQUA + "");
        item4.setItemMeta(meta4);

        ItemStack item5 = new ItemStack(Material.REDSTONE);
        ItemMeta meta5 = item5.getItemMeta();
        meta5.setDisplayName(ChatColor.AQUA + "");
        item5.setItemMeta(meta5);

        ItemStack item6 = new ItemStack(Material.DIAMOND);
        ItemMeta meta6 = item6.getItemMeta();
        meta6.setDisplayName(ChatColor.AQUA + "");
        item6.setItemMeta(meta6);

        ItemStack item7 = new ItemStack(Material.DIAMOND);
        ItemMeta meta7 = item7.getItemMeta();
        meta7.setDisplayName(ChatColor.AQUA + "");
        item7.setItemMeta(meta7);

        final List<ItemStack> items = Arrays.asList(item1, item2, item3, item4, item5, item6, item7);
        player.openInventory(inv);
        final Player finalPlayer = player;
        List<Integer> delays = new ArrayList<Integer>();
        int insert = 0;
        for(int i = 0; i < getConfig().getInt("frames"); i++) {
            delays.add(i, i + insert);
            insert += i;
        }
        final DelayCounter counter = new DelayCounter();
        counter.setD(delays);
        final BukkitTask task = new BukkitRunnable() {
            public void run() {
                if(!finalPlayer.getOpenInventory().getTopInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui_name")))) return;
                if(counter.getD().contains(counter.get())) {
                    Inventory inv = finalPlayer.getOpenInventory().getTopInventory();
                    for (int i = 0; i < 27; i++) {
                        inv.setItem(i, panels.get(random.nextInt(panels.size())));
                    }

                    inv.setItem(11, items.get(random.nextInt(items.size())));
                    inv.setItem(13, items.get(random.nextInt(items.size())));
                    inv.setItem(15, items.get(random.nextInt(items.size())));
                    finalPlayer.playSound(finalPlayer.getLocation(), Sound.NOTE_PLING, 25F, 1F);
                }
                counter.increment();
            }
        }.runTaskTimer(plugin, 0L, 1L);
        final double xWinnings = winnings;
        final int xChance = chance;
        new BukkitRunnable() {
            public void run() {
                task.cancel();
                removeFromSlots(finalPlayer);
                if (xChance == 1) {
                    if (finalPlayer.getOpenInventory().getTopInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui_name")))) {
                        ItemStack recurrent = new ItemStack(Material.DIAMOND, 1);
                        ItemMeta recurr = recurrent.getItemMeta();
                        recurr.setDisplayName(ChatColor.GREEN + "$" + xWinnings);
                        recurrent.setItemMeta(recurr);
                        finalPlayer.closeInventory();
                        Inventory inventoryResult = Bukkit.createInventory(null, 9, ChatColor.GREEN + "You win!");
                        for (int i = 0; i < 9; i++) {
                            inventoryResult.setItem(i, panels.get(random.nextInt(panels.size())));
                        }

                        inventoryResult.setItem(4, recurrent);
                        inventoryResult.setItem(2, recurrent);
                        inventoryResult.setItem(6, recurrent);
                        finalPlayer.openInventory(inventoryResult);
                        finalPlayer.playSound(finalPlayer.getLocation(), Sound.ORB_PICKUP, 50F, 1F);
                        getEcon().depositPlayer(finalPlayer, xWinnings);
                    } else {
                        finalPlayer.closeInventory();
                        finalPlayer.sendMessage(getPrefix() + ChatColor.AQUA + "You won $" + ChatColor.WHITE + xWinnings + ChatColor.AQUA + ", Congratulations!");
                    }
                } else {
                    if (finalPlayer.getOpenInventory().getTopInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui_name")))) {
                        ItemStack one = finalPlayer.getOpenInventory().getTopInventory().getItem(11);
                        ItemStack two = finalPlayer.getOpenInventory().getTopInventory().getItem(13);
                        ItemStack three = finalPlayer.getOpenInventory().getTopInventory().getItem(15);
                        finalPlayer.closeInventory();
                        Inventory inventoryResult = Bukkit.createInventory(null, 9, ChatColor.RED + "You lose!");
                        for (int i = 0; i < 9; i++) {
                            inventoryResult.setItem(i, panels.get(random.nextInt(panels.size())));
                        }
                        if (one == two && one == three) {
                            if (one.getType().equals(Material.DIAMOND)) {
                                one.setType(Material.GOLD_INGOT);
                            } else {
                                one.setType(Material.DIAMOND);
                            }
                        }

                        inventoryResult.setItem(4, two);
                        inventoryResult.setItem(2, one);
                        inventoryResult.setItem(6, three);
                        finalPlayer.openInventory(inventoryResult);
                        finalPlayer.playSound(finalPlayer.getLocation(), Sound.ORB_PICKUP, 50F, 1F);
                    } else {
                        finalPlayer.closeInventory();
                        finalPlayer.sendMessage(getPrefix() + ChatColor.AQUA + "You lose :(");
                    }
                }
            }
            //}.runTaskLater(plugin, 190L);
        }.runTaskLater(plugin, delays.get(delays.size() - 1));

    }
}
