package me.droreo002.wtitle.commands;

import me.droreo002.oreocore.commands.CommandArg;
import me.droreo002.oreocore.commands.CustomCommand;
import me.droreo002.wtitle.WorldGuardTitle;
import me.droreo002.wtitle.inventory.TitleListInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand extends CommandArg {

    private WorldGuardTitle plugin;

    public ListCommand(WorldGuardTitle plugin, CustomCommand parent) {
        super("list", parent);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof Player) {
            new TitleListInventory(plugin.getTitleDatabase()).open((Player) commandSender);
        } else {
            sendMessage(commandSender, "Currently there's " + plugin.getTitleDatabase().getTitles().size() + " registered title. Run this in game to view a gui!");
        }
    }
}
