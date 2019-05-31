package kr.kieran.cannoncore.xray.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;

public abstract class AbstractPacket {

    PacketContainer handle;

    AbstractPacket(PacketContainer handle, PacketType type) {
        if (handle == null) throw new IllegalArgumentException("Packet handle cannot be null.");
        if (!Objects.equal(handle.getType(), type))
            throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
        this.handle = handle;
    }

    private PacketContainer getHandle() {
        return handle;
    }

    @Deprecated
    public void receivePacket(Player sender) {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender,
                    getHandle());
        } catch (Exception e) {
            throw new RuntimeException("Cannot receive packet.", e);
        }
    }
}