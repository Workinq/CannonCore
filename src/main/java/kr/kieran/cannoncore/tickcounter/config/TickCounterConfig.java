package kr.kieran.cannoncore.tickcounter.config;

import kr.kieran.cannoncore.utils.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class TickCounterConfig extends Configuration {

    public TickCounterConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void createConfig() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Right/left click repeaters");
        lore.add("&7to count their total ticks.");
        config.set("permission", "core.tickcounter");
        config.set("no-permission", "&cYou do not have permission to use this command.");
        config.set("not-a-player", "&cOnly in-game players can use this command.");
        config.set("invalid-usage", "&cInvalid command usage: /tickcounter [give <player> [amount]].");
        config.set("own-inventory-full", "&cYour inventory is full.");
        config.set("inventory-full", "&c{player}'s inventory is full.");
        config.set("invalid-player", "&cCould not find player {player}.");
        config.set("not-a-number", "&c{var} is not a number.");
        config.set("received-item", "&7You received &c{amount}x &7tick counter.");
        config.set("given-item", "&7You gave &c{player} {amount}x &7tick counter(s).");
        config.set("ticks-reset", "&7You have &creset &7your tick selection.");
        config.set("tick-selection", "&7Total ticks: &c{ticks}&7.");
        config.set("item.material", "BLAZE_ROD");
        config.set("item.name", "&cTick Counter");
        config.set("item.lore", lore);
        config.set("item.enchanted", false);
    }

}
