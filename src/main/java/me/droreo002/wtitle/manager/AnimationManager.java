package me.droreo002.wtitle.manager;

import me.droreo002.wtitle.MainCommand;
import me.droreo002.wtitle.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class AnimationManager {

    public static void sendAnimationTitle(String data, Player player) {
        if (MainPlugin.getConfigManager().getConfig().getBoolean(data+ ".playsound")) {
            Sound sound = Sound.valueOf(MainPlugin.getConfigManager().getConfig().getString(data + ".sound-setting.sound"));
            float vol = (float) MainPlugin.getConfigManager().getConfig().getDouble(data + ".sound-setting.volume");
            float pitch = (float) MainPlugin.getConfigManager().getConfig().getDouble(data + ".sound-setting.pitch");
            player.playSound(player.getLocation(), sound, vol, pitch);
        }
        MainPlugin.getTask().put(player.getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(MainPlugin.getPlugin(MainPlugin.class),
                new AnimationRunnable(player, data), 0L, MainPlugin.getConfigManager().getConfig().getInt(data + ".animationSpeed")));
    }
}
