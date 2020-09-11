package dp.dog.commands;

import dp.dog.main.OreGen;
import dp.dog.main.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;

public class InfoCommand implements CommandExecutor {
    private static OreGen og = OreGen.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("플레이어만 사용 가능한 명령어 입니다.");
            return false;
        }
        Player p = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 27, "§1OreGen 확률");
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
        ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta im = is.getItemMeta();
        String group = og.playerData.get(p.getUniqueId()).getString("CurrentGroup");
        im.setDisplayName("§6그룹: §a" + group);
        HashSet<Tuple<String, Double>> tuple = og.blocks.get(group);
        ArrayList<String> lore = new ArrayList<>();
        double total = 0;
        for (Tuple<String, Double> t : tuple) {
            total += t.b();
        }
        for (Tuple<String, Double> t : tuple) {
            lore.add("§b" + t.a() + ": §e" + t.b() + ", " + String.format("%.2f", t.b() / total * 100) + "%");
        }
        im.setLore(lore);
        is.setItemMeta(im);
        inv.setItem(13, is);
        p.openInventory(inv);
        return false;
    }
}
