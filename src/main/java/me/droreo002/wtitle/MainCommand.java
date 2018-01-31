package me.droreo002.wtitle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Made by DrOreo002");
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender instanceof Player) {
                    Player player = (Player)sender;
                    if (!player.hasPermission("wtitle.admin")) {
                        player.sendMessage("No Permission.");
                        return false;
                    }
                    MainPlugin.getConfigManager().reloadConfig();
                    player.sendMessage("Reloaded the config.yml");
                    return false;
                }
                sender.sendMessage("Reloaded the config.yml");
                MainPlugin.getConfigManager().reloadConfig();
            }
            return false;
        }
        return false;
    }
}
