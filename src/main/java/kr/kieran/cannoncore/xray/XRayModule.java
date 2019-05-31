package kr.kieran.cannoncore.xray;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.xray.wrapper.WrapperPlayServerSpawnEntity;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class XRayModule {

    private CannonCore plugin;
    private Map<UUID, Set<Block>> toggledPlayers;
    private Set<UUID> fullyToggled;

    public XRayModule(CannonCore plugin) {
        this.plugin = plugin;
        this.toggledPlayers = new ConcurrentHashMap<>();
        this.fullyToggled = new HashSet<>();
        loadPacketHandlers();
        plugin.getLogger().info("XRay module successfully loaded.");
    }

    public void disable() {
        if (!toggledPlayers.isEmpty()) toggledPlayers.clear();
        if (!fullyToggled.isEmpty()) fullyToggled.clear();
        toggledPlayers = null;
        fullyToggled = null;
    }

    private void loadPacketHandlers() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, WrapperPlayServerSpawnEntity.TYPE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrapperPlayServerSpawnEntity wrapper = new WrapperPlayServerSpawnEntity(event.getPacket());
                Player player = event.getPlayer();
                if ((wrapper.getEntity(event).getType().equals(EntityType.PRIMED_TNT) || wrapper.getEntity(event).getType().equals(EntityType.FALLING_BLOCK)) && toggledPlayers.containsKey(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.MULTI_BLOCK_CHANGE, PacketType.Play.Server.BLOCK_CHANGE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (fullyToggled.contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        });
    }

    public Map<UUID, Set<Block>> getToggledPlayers() {
        return toggledPlayers;
    }

    public Set<UUID> getFullyToggled() {
        return fullyToggled;
    }

}
