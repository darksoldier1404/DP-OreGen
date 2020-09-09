package dp.dog.main;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DPConfig {
    public static OreGen plugin = OreGen.getInstance();
    public static void saveConfig(YamlConfiguration config, String key){
        File fconfig = new File(plugin.getDataFolder(), "config.yml");
        if (!fconfig.exists()) {
            plugin.saveResource("config.yml", false);
        }
    }
}
