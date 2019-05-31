package kr.kieran.cannoncore.xray.config;

import kr.kieran.cannoncore.utils.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class XRayConfig extends Configuration {

    public XRayConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void createConfig() {
        List<String> disabledBlocks = new ArrayList<>();
        disabledBlocks.add("REDSTONE");
        disabledBlocks.add("DISPENSER");
        disabledBlocks.add("PISTON_BASE");
        disabledBlocks.add("PISTON_EXTENSION");
        disabledBlocks.add("PISTON_MOVING_PIECE");
        disabledBlocks.add("PISTON_STICKY_BASE");
        disabledBlocks.add("LEVER");
        disabledBlocks.add("WOOD_BUTTON");
        disabledBlocks.add("STONE_BUTTON");
        disabledBlocks.add("REDSTONE_TORCH_OFF");
        disabledBlocks.add("REDSTONE_TORCH_ON");
        disabledBlocks.add("REDSTONE_BLOCK");
        disabledBlocks.add("CARPET");
        disabledBlocks.add("WEB");
        disabledBlocks.add("LADDER");
        disabledBlocks.add("STEP");
        disabledBlocks.add("WOOD_DOUBLE_STEP");
        disabledBlocks.add("WOOD_STEP");
        disabledBlocks.add("DOUBLE_STEP");
        disabledBlocks.add("STONE_SLAB2");
        disabledBlocks.add("DOUBLE_STONE_SLAB2");
        disabledBlocks.add("DIODE");
        disabledBlocks.add("DIODE_BLOCK_OFF");
        disabledBlocks.add("DIODE_BLOCK_ON");
        config.set("permission", "core.xray");
        config.set("no-permission", "&cYou do not have permission to use this command.");
        config.set("not-a-player", "&cOnly in-game players can use this command.");
        config.set("not-a-number", "&c{value} is not a number. Please alter your configuration file.");
        config.set("xray-enabled", "&7You have &aenabled &7XRay.");
        config.set("xray-disabled", "&7You have &cdisabled &7XRay.");
        config.set("radius", 16);
        config.set("permission", "core.xray.toggle");
        config.set("disabled-blocks", disabledBlocks);
    }

}
