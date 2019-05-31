package kr.kieran.cannoncore.utils.enums;

public enum ListenerType {

    REMOTE_BUTTON("remote-button", ConfigType.MAIN_CONFIG, "remote-button.enabled"),
    TICK_COUNTER("tick-counter", ConfigType.MAIN_CONFIG, "tick-counter.enabled"),
    PLAYER_LISTENER("player-listener", ConfigType.MAIN_CONFIG, "player-listener.enabled");

    private String name;
    private ConfigType configType;
    private String enabledPath;

    ListenerType(String name, ConfigType configType, String enabledPath) {
        this.name = name;
        this.configType = configType;
        this.enabledPath = enabledPath;
    }

    public String getName() {
        return this.name;
    }

    public ConfigType getConfigType() {
        return this.configType;
    }

    public String getEnabledPath() {
        return this.enabledPath;
    }

}
