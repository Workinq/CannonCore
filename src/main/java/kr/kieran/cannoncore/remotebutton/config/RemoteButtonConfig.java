package kr.kieran.cannoncore.remotebutton.config;

import kr.kieran.cannoncore.utils.configuration.Configuration;

public class RemoteButtonConfig extends Configuration {

    public RemoteButtonConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void createConfig() {
        config.set("button-permission", "core.button");
        config.set("fire-permission", "core.fire");
        config.set("no-permission", "&cYou do not have permission to use this command.");
        config.set("not-a-player", "&cOnly in-game players can use this command.");
        config.set("invalid-usage", "&cInvalid command usage: /button <reset | fire | hold [duration]>");
        config.set("button-reset", "&7You have &creset &7your button.");
        config.set("no-button-set", "&cYou do not have a button selected.");
        config.set("no-button", "&cThe button doesn't exist.");
        config.set("button-fired", "&7Triggered button at &c{coords} &7for &c{time}s &7.");
        config.set("button-on", "&7The button is now &aon&7.");
        config.set("button-off", "&7The button is now &coff&7.");
        config.set("not-a-number", "&c{var} is not a number.");
        config.set("button-saved", "&7Saved button at &c{coords}&7.");
    }

}
