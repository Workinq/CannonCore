package kr.kieran.cannoncore.utils.configuration;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.interfaces.cConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Configuration implements cConfig {

    private String fileName;
    private File file;
    protected YamlConfiguration config;

    public Configuration(String fileName) {
        this.fileName = fileName;
        loadConfig();
    }

    @Override
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadConfig() {
        file = new File(CannonCore.getInstance().getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            createConfig();
            save();
        }
    }

    @Override
    public void reload() {
        loadConfig();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

}
