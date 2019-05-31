package kr.kieran.cannoncore.utils.configuration;

import kr.kieran.cannoncore.utils.interfaces.cDataConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class DataConfiguration implements cDataConfig {

    private Map<String, YamlConfiguration> configurations;
    private Map<String, File> files;
    private String folder;

    public DataConfiguration(String folder) {
        this.folder = folder;
        this.configurations = new HashMap<>();
        this.files = new HashMap<>();
    }

    @Override
    public void loadConfig(String var1) {
        File file = new File(folder, var1 + ".yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            createConfig(file, configuration);
        }
        configurations.put(var1, configuration);
        files.put(var1, file);
    }

    @Override
    public void save(String var1) {
        try {
            configurations.get(var1).save(files.get(var1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        configurations = new HashMap<>();
        files = new HashMap<>();
    }

    @Override
    public YamlConfiguration getConfigByName(String var1) {
        return configurations.get(var1);
    }

    public Map<String, YamlConfiguration> getConfigurations() {
        return configurations;
    }

}
