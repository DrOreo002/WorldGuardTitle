package me.droreo002.wtitle;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.session.SessionManager;
import lombok.Getter;
import me.droreo002.oreocore.DependedPluginProperties;
import me.droreo002.oreocore.OreoCore;
import me.droreo002.wtitle.commands.WTitleCommand;
import me.droreo002.wtitle.database.TitleDatabase;
import me.droreo002.wtitle.flags.EnableTitleFlagHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardTitle extends JavaPlugin {

    public final static StateFlag ENABLE_TITLE = new StateFlag("enable-title", true);

    @Getter
    private static WorldGuardTitle instance;
    @Getter
    private WTitleCommand wTitleCommand;
    @Getter
    private WorldGuard worldGuard;
    @Getter
    private FlagRegistry flagRegistry;
    @Getter
    private SessionManager sessionManager;
    @Getter
    private TitleDatabase titleDatabase;

    /**
     * WorldGuard tell to use onLoad instead of onEnable
     */
    @Override
    public void onLoad() {
        instance = this;
        worldGuard = WorldGuard.getInstance();
        flagRegistry = worldGuard.getFlagRegistry();

        flagRegistry.register(ENABLE_TITLE);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        sessionManager = worldGuard.getPlatform().getSessionManager();

        sessionManager.registerHandler(EnableTitleFlagHandler.FACTORY, null);
        wTitleCommand = new WTitleCommand(this);
        titleDatabase = new TitleDatabase(this);

        saveResource("config.yml", false);

        OreoCore.getInstance().dependPlugin(this, DependedPluginProperties.builder().build());
    }
}
