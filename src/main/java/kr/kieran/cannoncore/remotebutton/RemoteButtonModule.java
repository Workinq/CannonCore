package kr.kieran.cannoncore.remotebutton;

import kr.kieran.cannoncore.CannonCore;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RemoteButtonModule {

    private Map<UUID, Location> buttonLocations;

    public RemoteButtonModule(CannonCore plugin) {
        this.buttonLocations = new HashMap<>();
        plugin.getLogger().info("RemoteButton module successfully loaded.");
    }

    public void disable() {
        if (!buttonLocations.isEmpty()) buttonLocations.clear();
        buttonLocations = null;
    }

    public Map<UUID, Location> getButtonLocations() {
        return buttonLocations;
    }

}
