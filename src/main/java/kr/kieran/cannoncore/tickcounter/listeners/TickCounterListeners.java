package kr.kieran.cannoncore.tickcounter.listeners;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.ListenerType;
import kr.kieran.cannoncore.utils.listener.CoreListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Diode;

public class TickCounterListeners extends CoreListener {

    public TickCounterListeners(Configuration config, ListenerType listenerType) {
        super(config, listenerType);
    }

    @EventHandler
    public void onClickDiode(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.AIR) return;
        if (!CannonCore.getInstance().getTickCounter().getItemUtils().isTickCounter(event.getPlayer().getItemInHand()))
            return;
        Action action = event.getAction();
        Player player = event.getPlayer();
        event.setCancelled(true);
        if (action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR) {
            CannonCore.getInstance().getTickCounter().getTicks().put(player.getUniqueId(), 0);
            player.sendMessage(Chat.color(config.getConfig().getString("ticks-reset")));
            return;
        }
        Block block = event.getClickedBlock();
        boolean isDiode = block.getType().toString().contains("DIODE");
        boolean isRepeater = block.getType().toString().contains("REPEATER");
        if (!isDiode && !isRepeater) return;
        Diode diode = (Diode) block.getState().getData();
        if (!CannonCore.getInstance().getTickCounter().getTicks().containsKey(player.getUniqueId())) {
            CannonCore.getInstance().getTickCounter().getTicks().put(player.getUniqueId(), 0);
        }
        int delay = diode.getDelay();
        int totalDelay = CannonCore.getInstance().getTickCounter().getTicks().get(player.getUniqueId()) + delay;
        CannonCore.getInstance().getTickCounter().getTicks().put(player.getUniqueId(), totalDelay);
        player.sendMessage(Chat.color(config.getConfig().getString("tick-selection").replace("{ticks}", String.valueOf(totalDelay))));
    }

}
