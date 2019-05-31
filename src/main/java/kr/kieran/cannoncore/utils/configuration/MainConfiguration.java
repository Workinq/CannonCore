package kr.kieran.cannoncore.utils.configuration;

public class MainConfiguration extends Configuration {

    public MainConfiguration(String fileName) {
        super(fileName);
    }

    @Override
    public void createConfig() {
        config.set("permission", "core.core");
        config.set("no-permission", "&cYou do not have permission to use this command.");
        config.set("invalid-usage", "&cInvalid command usage: /cannoncore <reload | config set <config> <value>>.");
        config.set("invalid-config", "&cThe configuration file {config} doesn't exist.");
        config.set("invalid-path", "&cThe path {path} doesn't exist in {config}'s configuration file.");
        config.set("config-reloaded", "&7The configuration files have been &creloaded &7successfully.");
        config.set("set-path", "&7Changed &c{path} &7to &c{value} &7for &c{config}&7.");
    }

}
