package kr.kieran.cannoncore;

import com.comphenix.protocol.ProtocolLibrary;
import kr.kieran.cannoncore.remotebutton.RemoteButtonModule;
import kr.kieran.cannoncore.remotebutton.commands.RemoteButtonCommand;
import kr.kieran.cannoncore.remotebutton.commands.RemoteFireCommand;
import kr.kieran.cannoncore.remotebutton.config.RemoteButtonConfig;
import kr.kieran.cannoncore.remotebutton.listeners.ButtonPressListener;
import kr.kieran.cannoncore.tickcounter.TickCounterModule;
import kr.kieran.cannoncore.tickcounter.commands.TickCounterCommand;
import kr.kieran.cannoncore.tickcounter.config.TickCounterConfig;
import kr.kieran.cannoncore.tickcounter.listeners.TickCounterListeners;
import kr.kieran.cannoncore.tntsound.TNTSoundModule;
import kr.kieran.cannoncore.tntsound.commands.TNTSoundCommand;
import kr.kieran.cannoncore.tntsound.config.TNTSoundConfig;
import kr.kieran.cannoncore.utils.command.BaseCommand;
import kr.kieran.cannoncore.utils.command.CoreCommand;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.configuration.MainConfiguration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import kr.kieran.cannoncore.utils.enums.ConfigType;
import kr.kieran.cannoncore.utils.enums.ListenerType;
import kr.kieran.cannoncore.utils.listener.CoreListener;
import kr.kieran.cannoncore.utils.listener.PlayerListener;
import kr.kieran.cannoncore.xray.XRayModule;
import kr.kieran.cannoncore.xray.commands.XRayCommand;
import kr.kieran.cannoncore.xray.config.XRayConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CannonCore extends JavaPlugin {

    private static CannonCore instance;
    private TNTSoundModule tntSound;
    private XRayModule xray;
    private RemoteButtonModule remoteButton;
    private TickCounterModule tickCounter;
    private Map<ConfigType, Configuration> configurations;

    @Override
    public void onEnable() {
        instance = this;
        loadConfigurations();
        loadListeners();
        loadModules();
        loadCommands();
    }

    @Override
    public void onDisable() {
        unloadModules();
        getServer().getScheduler().cancelTasks(this);
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        instance = null;
    }

    private void loadModules() {
        tntSound = new TNTSoundModule(this);
        xray = new XRayModule(this);
        remoteButton = new RemoteButtonModule(this);
        tickCounter = new TickCounterModule(this);
    }

    private void loadConfigurations() {
        configurations = new HashMap<>();
        configurations.put(ConfigType.MAIN_CONFIG, new MainConfiguration("config.yml"));
        configurations.put(ConfigType.TNTSOUND_CONFIG, new TNTSoundConfig("tntsound.yml"));
        configurations.put(ConfigType.XRAY_CONFIG, new XRayConfig("xray.yml"));
        configurations.put(ConfigType.REMOTEBUTTON_CONFIG, new RemoteButtonConfig("remotebutton.yml"));
        configurations.put(ConfigType.TICKCOUNTER_CONFIG, new TickCounterConfig("tickcounter.yml"));
    }

    private void loadCommands() {
        Map<CommandType, CoreCommand> commands = new HashMap<>();
        commands.put(CommandType.CORE, new BaseCommand(getConfigByName(ConfigType.MAIN_CONFIG), CommandType.CORE));
        commands.put(CommandType.TNTSOUND, new TNTSoundCommand(getConfigByName(ConfigType.TNTSOUND_CONFIG), CommandType.TNTSOUND));
        commands.put(CommandType.XRAY, new XRayCommand(getConfigByName(ConfigType.XRAY_CONFIG), CommandType.XRAY));
        commands.put(CommandType.BUTTON, new RemoteButtonCommand(getConfigByName(ConfigType.REMOTEBUTTON_CONFIG), CommandType.BUTTON));
        commands.put(CommandType.FIRE, new RemoteFireCommand(getConfigByName(ConfigType.REMOTEBUTTON_CONFIG), CommandType.FIRE));
        commands.put(CommandType.TICKCOUNTER, new TickCounterCommand(getConfigByName(ConfigType.TICKCOUNTER_CONFIG), CommandType.TICKCOUNTER));
        for (CommandType type : commands.keySet()) {
            getServer().getPluginCommand(type.getName()).setExecutor(commands.get(type));
        }
    }

    private void loadListeners() {
        Map<ListenerType, CoreListener> listeners = new HashMap<>();
        listeners.put(ListenerType.REMOTE_BUTTON, new ButtonPressListener(getConfigByName(ConfigType.REMOTEBUTTON_CONFIG), ListenerType.REMOTE_BUTTON));
        listeners.put(ListenerType.TICK_COUNTER, new TickCounterListeners(getConfigByName(ConfigType.TICKCOUNTER_CONFIG), ListenerType.TICK_COUNTER));
        listeners.put(ListenerType.PLAYER_LISTENER, new PlayerListener(getConfigByName(ConfigType.MAIN_CONFIG), ListenerType.PLAYER_LISTENER));
        for (CoreListener listener : listeners.values()) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void unloadModules() {
        tntSound.disable();
        xray.disable();
        remoteButton.disable();
        tickCounter.disable();
    }

    public void reloadConfigurations() {
        for (Configuration configuration : configurations.values()) {
            configuration.reload();
        }
    }

    public TNTSoundModule getTntSound() {
        return tntSound;
    }

    public XRayModule getXray() {
        return xray;
    }

    public RemoteButtonModule getRemoteButton() {
        return remoteButton;
    }

    public TickCounterModule getTickCounter() {
        return tickCounter;
    }

    public Configuration getConfigByName(ConfigType type) {
        return configurations.get(type);
    }

    public static CannonCore getInstance() {
        return instance;
    }

}
