package dp.dog.main;

import dp.dog.commands.InfoCommand;
import dp.dog.commands.ReloadCommand;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class OreGen extends JavaPlugin implements Listener {
    public static OreGen plugin;
    public Events events;
    public YamlConfiguration config;
    public Map<String, HashSet<Tuple<String, Double>>> blocks = new HashMap<>();
    public Map<Location, Player> recentPlayer = new HashMap<>();
    public Map<UUID, YamlConfiguration> playerData = new HashMap<>();

    public static OreGen getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        events = new Events(plugin);
        plugin.getServer().getPluginManager().registerEvents(plugin, plugin);
        plugin.getServer().getPluginManager().registerEvents(events, plugin);
        plugin.getCommand("drl").setExecutor(new ReloadCommand());
        plugin.getCommand("dog").setExecutor(new InfoCommand());
        DPConfig.loadConfig();
    }

    @Override
    public void onDisable() {
        DPConfig.saveConfig();
    }
}
