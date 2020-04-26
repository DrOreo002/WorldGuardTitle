package me.droreo002.wtitle.commands;

import me.droreo002.oreocore.commands.CustomCommand;
import me.droreo002.oreocore.commands.CustomCommandManager;
import me.droreo002.oreocore.utils.bridge.OSound;
import me.droreo002.oreocore.utils.misc.SoundObject;
import me.droreo002.wtitle.WorldGuardTitle;
import org.bukkit.command.CommandSender;

public class WTitleCommand extends CustomCommand {

    public WTitleCommand(WorldGuardTitle plugin) {
        super(plugin, "worldguardtitle", "wtitle");
        setPermission("wtitle.admin", "No permission!");
        setSuccessSound(new SoundObject(OSound.BLOCK_NOTE_BLOCK_PLING));

        addArgument(new ReloadCommand(plugin, this));
        addArgument(new ListCommand(plugin, this));

        CustomCommandManager.registerCommand(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        sendMessage(commandSender, "Made by DrOreo002");
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        super.sendMessage(sender, "&7[&aTitle&7]&f " + message);
    }
}
