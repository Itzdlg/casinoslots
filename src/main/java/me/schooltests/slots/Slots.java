package me.schooltests.slots;

import me.schooltests.slots.commands.CommandSlots;
import me.schooltests.slots.events.InvClickHandler;
import me.schooltests.slots.events.InvCloseHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Slots extends JavaPlugin {
    private Economy econ = null;
    private static Slots plugin;
    private API api;

    @Override
    public void onEnable() {
        this.plugin = this;
        if(!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        api = new API(this, econ);

        getCommand("slots").setExecutor(new CommandSlots());
        getServer().getPluginManager().registerEvents(new InvCloseHandler(), this);
        getServer().getPluginManager().registerEvents(new InvClickHandler(), this);

        new BukkitRunnable() {
            public void run() {
                getServer().getLogger().info("[CasinoSlots] Resetting slot cooldowns!");
                api.clearSlots();
            }
        }.runTaskTimer(plugin, 0L, (long) (86400 * 20));
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    public static Slots getInstance() {
        return plugin;
    }

    public API getAPI() {
        return api;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
