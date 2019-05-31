package kr.kieran.cannoncore.utils.listener;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.ListenerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends CoreListener {

    public PlayerListener(Configuration config, ListenerType listenerType) {
        super(config, listenerType);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleMemoryLeaks(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        handleMemoryLeaks(event.getPlayer());
    }

    private void handleMemoryLeaks(Player player) {
        CannonCore.getInstance().getTntSound().getToggledPlayers().remove(player.getUniqueId());
        CannonCore.getInstance().getXray().getToggledPlayers().remove(player.getUniqueId());
        CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
        CannonCore.getInstance().getTickCounter().getTicks().remove(player.getUniqueId());
    }

}
