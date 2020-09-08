package dp.dog.main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Random;

public class Events implements Listener {
    private OreGen og;
    Material[] blocks = {Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE};
    public Events(OreGen plugin) {
        og = plugin;
    }

    @EventHandler
    public void bfte(BlockFromToEvent e) {
        if(e.getToBlock().getType() == Material.AIR && e.getToBlock().getRelative(e.getFace()).getType() == Material.OAK_FENCE) {
            e.setCancelled(true);
            Block b = e.getToBlock();
            int i = (int) (Math.random()* blocks.length);
            b.setType(blocks[i]);
        }
    }
}
