package dp.dog.main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.*;

public class Events implements Listener {
    private final OreGen og;

    public Events(OreGen plugin) {
        og = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getView().getTitle().contains("OreGen 확률")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        File data = new File(og.getDataFolder() + "/Players", p.getUniqueId() + ".dog");
        if (!data.exists()) {
            new File(og.getDataFolder() + "/Players/" + p.getUniqueId() + ".dog");
            YamlConfiguration pd = YamlConfiguration.loadConfiguration(new File(og.getDataFolder() + "/Players", p.getUniqueId() + ".dog"));
            pd.set("CurrentGroup", "default");
            og.playerData.put(p.getUniqueId(), pd);
            DPConfig.savePlayerData(p.getUniqueId().toString(), pd);
        }else {
            YamlConfiguration pd = YamlConfiguration.loadConfiguration(new File(og.getDataFolder() + "/Players", p.getUniqueId() + ".dog"));
            for(String key : og.blocks.keySet()) {
                if(p.hasPermission(key+".dog")) {
                    pd.set("CurrentGroup", key);
                }
            }
            og.playerData.put(p.getUniqueId(), pd);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location loc = b.getLocation().clone();
        loc.setY(loc.getY()-1);
        if(loc.getWorld().getBlockAt(loc).getType() == Material.OAK_FENCE) {
            og.recentPlayer.put(e.getBlock().getLocation(), p);
        }
    }

    @EventHandler
    public void bfte(BlockFromToEvent e) {
        if (e.getToBlock().getType() == Material.AIR && e.getToBlock().getRelative(e.getFace()).getType() == Material.OAK_FENCE) {
            e.setCancelled(true);
            Block b = e.getToBlock();
            Player p = null;
            String group;
            if (og.recentPlayer.containsKey(b.getLocation())) {
                p = og.recentPlayer.get(b.getLocation());
                group = og.playerData.get(p.getUniqueId()).get("CurrentGroup").toString();
            }else {
                group = "default";
            }
            List<Material> blocks = new ArrayList<>();
            HashSet<Tuple<String, Double>> tuple = og.blocks.get(group);
            Map<Material, Double> map = new HashMap<>();
            for (Tuple<String, Double> t : tuple) {
                map.put(Material.getMaterial(t.a()), t.b());
            }
            b.setType(Utils.getWeightedRandom(map));
        }
    }
}
