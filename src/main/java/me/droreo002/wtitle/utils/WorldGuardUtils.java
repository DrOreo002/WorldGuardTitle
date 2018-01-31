package me.droreo002.wtitle.utils;

import com.sk89q.worldguard.LocalPlayer;
import me.droreo002.wtitle.MainPlugin;
import org.bukkit.entity.Player;

public class WorldGuardUtils {

    public static LocalPlayer wrapPlayer(Player player)
    {
        return MainPlugin.getWorldGuardPlugin().wrapPlayer(player);
    }
}
