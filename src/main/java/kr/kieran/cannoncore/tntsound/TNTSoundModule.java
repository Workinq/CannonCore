package kr.kieran.cannoncore.tntsound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import kr.kieran.cannoncore.CannonCore;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TNTSoundModule {

    private CannonCore plugin;
    private Set<UUID> toggledPlayers;

    public TNTSoundModule(CannonCore plugin) {
        this.plugin = plugin;
        this.toggledPlayers = new HashSet<>();
        loadPacketHandler();
        plugin.getLogger().info("TNTSoundModule module successfully loaded.");
    }

    private void loadPacketHandler() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                String contents = event.getPacket().getStrings().read(0);
                if ((contents.contains("tnt") || contents.contains("explode")) && toggledPlayers.contains(event.getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        });
    }

    public void disable() {
        if (!toggledPlayers.isEmpty()) toggledPlayers.clear();
        toggledPlayers = null;
    }

    public Set<UUID> getToggledPlayers() {
        return toggledPlayers;
    }

}
