package me.droreo002.wtitle.manager;

import java.util.Set;

import com.google.common.collect.Sets;
import com.sk89q.worldguard.protection.flags.*;

public class CustomSetFlag<T> extends SetFlag<T>
{

    public CustomSetFlag(String name, RegionGroup defaultGroup, Flag<T> subFlag) {
        super(name, defaultGroup, subFlag);
    }

    public CustomSetFlag(String name, Flag<T> subFlag) {
        super(name, subFlag);
    }

    @Override
    public Flag<T> getType() {
        return super.getType();
    }

    @Override
    public Set<T> parseInput(FlagContext context) throws InvalidFlagFormat {
        return super.parseInput(context);
    }
}