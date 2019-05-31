package kr.kieran.cannoncore.utils.interfaces;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface cDataConfig {

    void createConfig(File var1, YamlConfiguration var2);

    YamlConfiguration getConfigByName(String var1);

    void save(String var1);

    void loadConfig(String var1);

    void reload();

}
