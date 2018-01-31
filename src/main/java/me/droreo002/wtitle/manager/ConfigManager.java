package me.droreo002.wtitle.manager;

import me.droreo002.wtitle.MainPlugin;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {

    private MainPlugin plugin = (MainPlugin) MainPlugin.getPlugin(MainPlugin.class);
    private FileConfiguration MainConfig;
    private File MainConfigFile;
    private static ConfigManager instance;

    private ConfigManager() {

    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            return instance;
        }
        return instance;
    }

    public void setup()
    {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        MainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!MainConfigFile.exists()) {
            try
            {
                MainConfigFile.createNewFile();
                plugin.saveResource("config.yml", true);
            }
            catch (IOException e)
            {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + MainConfigFile + ".", e);
            }
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
    }

    public FileConfiguration getConfig()
    {
        if (MainConfig == null) {
            reloadConfig();
        }
        return MainConfig;
    }

    public void saveConfig()
    {
        if (MainConfig == null) {
            throw new NullArgumentException("Cannot save a non-existant file!");
        }
        try
        {
            MainConfig.save(MainConfigFile);
        }
        catch (IOException e)
        {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + MainConfigFile + ".", e);
        }
    }

    public void reloadConfig()
    {
        MainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!MainConfigFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
        InputStream configData = plugin.getResource("config.yml");
        if (configData != null) {
            MainConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(configData)));
        }
    }
}
