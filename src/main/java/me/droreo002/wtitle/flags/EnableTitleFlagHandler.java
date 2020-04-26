package me.droreo002.wtitle.flags;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import me.droreo002.wtitle.WorldGuardTitle;
import me.droreo002.wtitle.database.Title;
import me.droreo002.wtitle.database.TitleDatabase;
import me.droreo002.wtitle.database.TitleType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public class EnableTitleFlagHandler extends Handler {

    public static final EnableTitleFlagHandler.Factory FACTORY = new EnableTitleFlagHandler.Factory();

    private EnableTitleFlagHandler(Session session) {
        super(session);
    }

    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        final WorldGuardTitle plugin = WorldGuardTitle.getInstance();
        final TitleDatabase titleDatabase = plugin.getTitleDatabase();

        Title targetTitle = getTitle(titleDatabase, TitleType.ENTER, entered);
        if (targetTitle == null) targetTitle = getTitle(titleDatabase, TitleType.EXIT, exited);

        if (targetTitle != null) {
            Player bukkitPlayer = Bukkit.getPlayer(player.getUniqueId());
            targetTitle.sendTitle(bukkitPlayer);
        }
        return true;
    }

    /**
     * Get title
     *
     * @param regions       The regions
     * @param titleDatabase The title database
     * @return Title
     */
    private Title getTitle(TitleDatabase titleDatabase, TitleType titleType, Set<ProtectedRegion> regions) {
        Title targetTitle = null;
        for (ProtectedRegion reg : regions) {
            StateFlag.State state = reg.getFlag(WorldGuardTitle.ENABLE_TITLE);
            if (state != null && state != StateFlag.State.DENY) {
                targetTitle = titleDatabase.getTitle(titleType, reg.getId());
            }
        }
        return targetTitle;
    }

    public static class Factory extends Handler.Factory<EnableTitleFlagHandler> {
        @Override
        public EnableTitleFlagHandler create(Session session) {
            return new EnableTitleFlagHandler(session);
        }
    }
}
