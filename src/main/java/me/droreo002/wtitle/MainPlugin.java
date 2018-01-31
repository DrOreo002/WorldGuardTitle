package me.droreo002.wtitle;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.droreo002.wtitle.manager.ConfigManager;
import me.droreo002.wtitle.manager.SendTitleOnEnterFlag;
import me.droreo002.wtitle.manager.SendTitleOnExitFlag;
import me.droreo002.wtitle.manager.TitleManager;
import me.droreo002.wtitle.utils.FlagUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin {

    private TitleManager titleManager;
    private static WorldGuardPlugin worldGuardPlugin;
    private static ConfigManager configManager;

    @Override
    public void onLoad() {
        worldGuardPlugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
        worldGuardPlugin.getFlagRegistry().register(FlagUtils.TITLE_ON_EXIT);
        worldGuardPlugin.getFlagRegistry().register(FlagUtils.TITLE_ON_ENTER);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        titleManager = new TitleManager();
        configManager = ConfigManager.getInstance();
        configManager.setup();
        worldGuardPlugin.getSessionManager().registerHandler(SendTitleOnExitFlag.FACTORY, null);
        worldGuardPlugin.getSessionManager().registerHandler(SendTitleOnEnterFlag.FACTORY, null);
        Bukkit.getPluginCommand("worldguardtitle").setExecutor(new MainCommand());
    }

    public TitleManager getTitleManager() {
        return titleManager;
    }

    public static WorldGuardPlugin getWorldGuardPlugin() {
        return worldGuardPlugin;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
