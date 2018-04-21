package me.droreo002.wtitle.manager;


import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.LocationFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import me.droreo002.wtitle.MainPlugin;
import me.droreo002.wtitle.utils.FlagUtils;
import me.droreo002.wtitle.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SendTitleOnExitFlag extends Handler {

    public static final Factory FACTORY = new Factory();
    public static class Factory extends Handler.Factory<SendTitleOnExitFlag>
    {
        @Override
        public SendTitleOnExitFlag create(Session session)
        {
            return new SendTitleOnExitFlag(session);
        }
    }


    private SendTitleOnExitFlag(Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        for (ProtectedRegion reg : exited) {
            StateFlag.State state = reg.getFlag(FlagUtils.TITLE_ON_EXIT);
            if (state != null) {
                 ConfigManager man = MainPlugin.getConfigManager();
                 ConfigurationSection sec = man.getConfig().getConfigurationSection("Titles-Exit");
                 if (sec == null) {
                     return true;
                 }
                 for (String s : sec.getKeys(false)) {
                     if (s.equalsIgnoreCase(reg.getId())) {
                         String title = man.getConfig().getString("Titles-Exit." + s + ".title");
                         String sub_title = man.getConfig().getString("Titles-Exit." + s + ".sub-title");
                         int fade_in = man.getConfig().getInt("Titles-Exit." + s + ".fade-in");
                         int fade_out = man.getConfig().getInt("Titles-Exit." + s + ".fade-out");
                         int stay = man.getConfig().getInt("Titles-Exit." + s + ".stay");
                         TitleManager.sendFullTitle(player, fade_in, fade_out, stay, title, sub_title);

                         if (man.getConfig().getBoolean("Titles-Exit." + s + ".playsound")) {
                             Sound sound = Sound.valueOf(man.getConfig().getString("Titles-Exit." + s + ".sound-setting.sound"));
                             float vol = (float) man.getConfig().getDouble("Titles-Exit." + s + ".sound-setting.volume");
                             float pitch = (float) man.getConfig().getDouble("Titles-Exit." + s + ".sound-setting.pitch");
                             player.playSound(player.getLocation(), sound, vol, pitch);
                         }
                     }
                 }
            }
        }
        return true;
    }
}
