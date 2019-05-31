package kr.kieran.cannoncore.utils.listener;

import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.ListenerType;
import kr.kieran.cannoncore.utils.interfaces.cListener;
import org.bukkit.event.Listener;

public class CoreListener implements Listener, cListener {

    protected Configuration config;
    private ListenerType type;
    private boolean enabled;
    private String enabledPath;

    public CoreListener(Configuration config, ListenerType type) {
        this.config = config;
        this.type = type;
        this.enabledPath = type.getEnabledPath();
        this.enabled = config.getConfig().getBoolean(enabledPath);
    }

    @Override
    public void reload(Configuration var1) {
        this.config = var1;
    }

    @Override
    public void toggle() {
        this.enabled = !enabled;
        config.getConfig().set(enabledPath, enabled);
        config.save();
    }

    public ListenerType getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Configuration getConfig() {
        return config;
    }

}
