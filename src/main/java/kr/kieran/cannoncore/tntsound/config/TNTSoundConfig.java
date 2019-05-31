package kr.kieran.cannoncore.tntsound.config;

import kr.kieran.cannoncore.utils.configuration.Configuration;

public class TNTSoundConfig extends Configuration {

    public TNTSoundConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void createConfig() {
        config.set("permission", "core.tntsound");
        config.set("no-permission", "&cYou do not have permission to use this command.");
        config.set("not-a-player", "&cOnly in-game players can use this command.");
        config.set("tntsound-enabled", "&7You have &cdisabled &7TNT sounds.");
        config.set("tntsound-disabled", "&7You have &aenabled &7TNT sounds.");
        config.set("permission", "core.tntsounds.toggle");
    }
}
