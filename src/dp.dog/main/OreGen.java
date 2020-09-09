package dp.dog.main;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class OreGen extends JavaPlugin implements Listener {
    public static OreGen plugin;
    public Events events;
    public YamlConfiguration config;
    public static OreGen getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        events = new Events(plugin);
        plugin.getServer().getPluginManager().registerEvents(plugin, plugin);
        plugin.getServer().getPluginManager().registerEvents(events, plugin);
        final File fconfig = new File(getDataFolder(), "config.yml");
        if (!fconfig.exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }
}
