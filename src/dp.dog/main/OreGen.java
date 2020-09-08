package dp.dog.main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OreGen extends JavaPlugin implements Listener {
    public static OreGen plugin;

    public static OreGen getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        plugin.getServer().getPluginManager().registerEvents(plugin, plugin);
    }
}
