package kr.kieran.cannoncore.tickcounter;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.tickcounter.utils.TickCounterItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TickCounterModule {

    private TickCounterItem itemUtils;
    private Map<UUID, Integer> ticks;

    public TickCounterModule(CannonCore plugin) {
        itemUtils = new TickCounterItem();
        ticks = new HashMap<>();
        plugin.getLogger().info("TickCounter module successfully loaded.");
    }

    public void disable() {
        if (!ticks.isEmpty()) ticks.clear();
        ticks = null;
    }

    public TickCounterItem getItemUtils() {
        return itemUtils;
    }

    public Map<UUID, Integer> getTicks() {
        return ticks;
    }

}
