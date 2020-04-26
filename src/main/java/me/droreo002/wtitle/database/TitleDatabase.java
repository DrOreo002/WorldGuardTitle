package me.droreo002.wtitle.database;

import lombok.Getter;
import me.droreo002.oreocore.database.object.DatabaseFlatFile;
import me.droreo002.wtitle.WorldGuardTitle;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TitleDatabase extends DatabaseFlatFile implements Iterable<Title> {

    @Getter
    private final List<Title> titles = new ArrayList<>();

    public TitleDatabase(WorldGuardTitle plugin) {
        super(plugin, new File(plugin.getDataFolder(), "titles"), true);
        load();
    }

    /**
     * Load all titles
     * will also clear the cache if called when
     * cache is not empty
     */
    public void load() {
        if (!getDataCaches().isEmpty()) getDataCaches().clear();
        init();
        for (DataCache dataCache : getDataCaches()) {
            titles.add(new Title(dataCache.getDataFileName(), dataCache.getConfig()));
        }
    }

    @Override
    public void addDefaults(FileConfiguration fileConfiguration) {
        fileConfiguration.set("Data", "{}");
    }

    /**
     * Get title by name
     *
     * @param titleName The title name
     * @return Title
     */
    @Nullable
    public Title getTitle(String titleName) {
        return this.titles.stream().filter(t -> t.getName().equalsIgnoreCase(titleName)).findAny().orElse(null);
    }

    /**
     * Get title by title type and region
     *
     * @param titleType The title type
     * @param region    The region
     * @return Title
     */
    @Nullable
    public Title getTitle(TitleType titleType, String region) {
        return this.titles.stream().filter(t -> t.getRegion().equalsIgnoreCase(region) && t.getTitleType() == titleType).findAny().orElse(null);
    }

    @NotNull
    @Override
    public Iterator<Title> iterator() {
        return this.titles.iterator();
    }
}
