package me.droreo002.wtitle.manager;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import me.droreo002.wtitle.MainPlugin;
import me.droreo002.wtitle.utils.FlagUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

public class SendTitleOnEnterFlag extends Handler {

    public static final SendTitleOnEnterFlag.Factory FACTORY = new SendTitleOnEnterFlag.Factory();
    public static class Factory extends Handler.Factory<SendTitleOnEnterFlag>
    {
        @Override
        public SendTitleOnEnterFlag create(Session session)
        {
            return new SendTitleOnEnterFlag(session);
        }
    }

    private SendTitleOnEnterFlag(Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        for (ProtectedRegion reg : entered) {
            StateFlag.State state = reg.getFlag(FlagUtils.TITLE_ON_ENTER);
            if (state != null) {
                ConfigManager man = MainPlugin.getConfigManager();
                ConfigurationSection sec = man.getConfig().getConfigurationSection("Titles-Enter");
                if (sec == null) {
                    return true;
                }
                for (String s : sec.getKeys(false)) {
                    if (s.equalsIgnoreCase(reg.getId())) {
                        if (man.getConfig().getBoolean("Titles-Enter." + s + ".useAnimation")) {
                            AnimationManager.sendAnimationTitle("Titles-Enter." + s, player);
                            continue;
                        }
                        String title = man.getConfig().getString("Titles-Enter." + s + ".title");
                        String sub_title = man.getConfig().getString("Titles-Enter." + s + ".sub-title");
                        int fade_in = man.getConfig().getInt("Titles-Enter." + s + ".fade-in");
                        int fade_out = man.getConfig().getInt("Titles-Enter." + s + ".fade-out");
                        int stay = man.getConfig().getInt("Titles-Enter." + s + ".stay");
                        TitleManager.sendFullTitle(player, fade_in, fade_out, stay, title, sub_title);

                        if (man.getConfig().getBoolean("Titles-Enter." + s + ".playsound")) {
                            Sound sound = Sound.valueOf(man.getConfig().getString("Titles-Enter." + s + ".sound-setting.sound"));
                            float vol = (float) man.getConfig().getDouble("Titles-Enter." + s + ".sound-setting.volume");
                            float pitch = (float) man.getConfig().getDouble("Titles-Enter." + s + ".sound-setting.pitch");
                            player.playSound(player.getLocation(), sound, vol, pitch);
                        }
                    }
                }
            }
        }
        return true;
    }
}
