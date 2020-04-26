package me.droreo002.wtitle.commands;

import me.droreo002.oreocore.commands.CommandArg;
import me.droreo002.oreocore.commands.CustomCommand;
import me.droreo002.wtitle.WorldGuardTitle;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandArg {

    private WorldGuardTitle plugin;

    public ReloadCommand(WorldGuardTitle plugin, CustomCommand parent) {
        super("reload", parent);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        plugin.getTitleDatabase().load();
        sendMessage(commandSender, "Reloaded!");
        success(commandSender);
    }
}
