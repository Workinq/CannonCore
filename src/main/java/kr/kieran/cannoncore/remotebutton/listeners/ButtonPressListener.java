package kr.kieran.cannoncore.remotebutton.listeners;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.ListenerType;
import kr.kieran.cannoncore.utils.listener.CoreListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ButtonPressListener extends CoreListener {

    public ButtonPressListener(Configuration config, ListenerType listenerType) {
        super(config, listenerType);
    }

    @EventHandler
    public void onClickButton(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.STONE_BUTTON && event.getClickedBlock().getType() != Material.WOOD_BUTTON) return;
        if (CannonCore.getInstance().getRemoteButton().getButtonLocations().containsKey(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        Location location = event.getClickedBlock().getLocation();
        CannonCore.getInstance().getRemoteButton().getButtonLocations().put(player.getUniqueId(), location);
        player.sendMessage(Chat.color(config.getConfig().getString("button-saved").replace("{coords}", location.getX() + ", " + location.getY() + ", " + location.getZ())));
    }

}
