package dp.dog.main;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class DPConfig {
    public static OreGen plugin = OreGen.getInstance();
    public static void saveConfig(){
        try {
            plugin.config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        final File fconfig = new File(plugin.getDataFolder(), "config.yml");
        if (!fconfig.exists()) {
            plugin.saveResource("config.yml", false);
        }
        plugin.config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        for (String key : plugin.config.getConfigurationSection("").getKeys(false)) {
            HashSet<Tuple<String, Double>> hs = new HashSet<>();
            for (String block : plugin.config.getConfigurationSection(key + ".Blocks").getKeys(false)) {
                hs.add(new Tuple<>(block, plugin.config.getDouble(key+".Blocks."+block)));
            }
            plugin.blocks.put(key, hs);
        }
        System.out.println("콘피그 설정 리로드 완료");
    }

    public static void savePlayerData(String key, YamlConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder()+"/Players", key+".dog"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
