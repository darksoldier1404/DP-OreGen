package dp.dog.main;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OreGen extends JavaPlugin implements Listener {
    public static OreGen plugin;
    public Events events;
    public YamlConfiguration config;
    public Map<String, HashSet<Tuple<String, Integer>>> blocks = new HashMap<>();

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
        DPConfig.loadConfig();
        for (String key : config.getConfigurationSection("").getKeys(false)) {
            HashSet<Tuple<String, Integer>> hs = new HashSet<>();
            for (String block : config.getConfigurationSection(key + ".Blocks").getKeys(false)) {
                hs.add(new Tuple<>(block, config.getInt(key+".Blocks."+block)));
            }
            blocks.put(key, hs);
        }
    }

    @Override
    public void onDisable() {
        DPConfig.saveConfig();
    }
}
