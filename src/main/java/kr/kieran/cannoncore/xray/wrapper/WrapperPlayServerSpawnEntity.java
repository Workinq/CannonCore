package kr.kieran.cannoncore.xray.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class WrapperPlayServerSpawnEntity extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY;

    public WrapperPlayServerSpawnEntity(PacketContainer packet) {
        super(packet, TYPE);
    }

    private Entity getEntity(World world) {
        return handle.getEntityModifier(world).read(0);
    }

    public Entity getEntity(PacketEvent event) {
        return getEntity(event.getPlayer().getWorld());
    }

    public int getType() {
        return handle.getIntegers().read(6);
    }

    public void setType(int value) {
        handle.getIntegers().write(6, value);
    }

}