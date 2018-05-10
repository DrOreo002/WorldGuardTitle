package me.droreo002.wtitle.manager;

import me.droreo002.wtitle.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class AnimationRunnable implements Runnable {

    private Player player;
    private int max;
    private int elapsed;
    private List<String> titles;
    private List<String> subtitles;
    private String data;
    private int fadeOut;

    public AnimationRunnable(Player player, String data) {
        this.player = player;
        this.titles = MainPlugin.getConfigManager().getConfig().getStringList(data + ".animation-title");
        this.subtitles = MainPlugin.getConfigManager().getConfig().getStringList(data + ".animation-subtitle");
        this.max = titles.size() - 1;
        this.elapsed = 0;
        this.data = data;
        this.fadeOut = MainPlugin.getConfigManager().getConfig().getInt(data + ".fade-out");
    }

    @Override
    public void run() {
        if (elapsed >= max) {
            stop();
        }
        String subtitle = subtitles.get(elapsed);
        TitleManager.sendFullTitle(player, 0, 20, fadeOut, titles.get(elapsed), subtitle);
        elapsed++;
    }

    private void stop() {
        if (MainPlugin.getTask().containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(MainPlugin.getTask().get(player.getUniqueId()));
            MainPlugin.getTask().remove(player.getUniqueId());
        }
    }
}
