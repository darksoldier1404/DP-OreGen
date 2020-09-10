package dp.dog.main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.*;

public class Events implements Listener {
    private final OreGen og;

    public Events(OreGen plugin) {
        og = plugin;
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
            og.playerData.put(p.getUniqueId(), pd);
        }
    }


    /*
    플레이어가 블럭을 부숨 -> 위에있던 물이 흐르는 이벤트가 발생함.
    이벤트 -> 블럭이 발생시킨 이벤트라 플레이어가 없음
    - 해당 로케이션에서 가장 가까운 플레이어를 대상으로 지정
    - 최근에 해당 로케이션의 블럭을 부신 플레이어를 대상으로 지정
     */
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
