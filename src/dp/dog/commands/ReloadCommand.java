package dp.dog.commands;

import dp.dog.main.DPConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) {
            sender.sendMessage("권한이 없습니다.");
            return false;
        }
        DPConfig.loadConfig();
        sender.sendMessage("콘피그 리로드 완료.");
        return false;
    }
}
