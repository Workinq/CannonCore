package kr.kieran.cannoncore.remotebutton.utils;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.remotebutton.enums.ButtonPressType;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RemoteButton {

    private BlockStateDirection FACING;
    private static BlockStateBoolean POWERED;
    private static Location location;

    public RemoteButton(Location location, ButtonPressType type, double duration) {
        FACING = BlockStateDirection.of("facing");
        POWERED = BlockStateBoolean.of("powered");
        RemoteButton.location = location;
        if (type == ButtonPressType.NORMAL) {
            pressRaw(false, 0.0);
        }
        if (type == ButtonPressType.HOLD) {
            holdRaw(duration);
        }
    }

    public boolean isPressed() {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        IBlockData blockData = world.getType(blockPosition);
        return blockData.get(POWERED);
    }

    private int getPressDuration() {
        if (location.getBlock().getType() == Material.STONE_BUTTON) {
            return 20;
        }
        if (location.getBlock().getType() == Material.WOOD_BUTTON) {
            return 30;
        }
        return 0;
    }

    public boolean press() throws InterruptedException {
        Future<Boolean> future = Bukkit.getScheduler().callSyncMethod(CannonCore.getInstance(), () -> pressRaw(false, 0.0));
        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean pressRaw(boolean customDur, double dur) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition blockposition = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData iblockdata = world.getType(blockposition);
        Block block = iblockdata.getBlock();
        if (iblockdata.get(POWERED)) {
            return true;
        }
        if (hasCurrent(world, blockposition, iblockdata)) return true;
        world.setTypeAndData(blockposition, iblockdata.set(POWERED, true), 3);
        world.b(blockposition, blockposition);
        world.makeSound(blockposition.getX() + 0.5, blockposition.getY() + 0.5, blockposition.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        this.c(world, blockposition, iblockdata.get(FACING), block);
        if (customDur) {
            world.a(blockposition, block, (int) Math.round(dur * 20.0));
        }
        world.a(blockposition, block, this.getPressDuration());
        return true;
    }

    private void holdRaw(double duration) {
        if (duration >= 0.05) {
            this.pressRaw(true, duration);
            return;
        }
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition blockposition = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData iblockdata = world.getType(blockposition);
        Block block = iblockdata.getBlock();
        if (hasCurrent(world, blockposition, iblockdata)) return;
        if (iblockdata.get(POWERED)) {
            world.setTypeAndData(blockposition, iblockdata.set(POWERED, false), 3);
        } else {
            world.setTypeAndData(blockposition, iblockdata.set(POWERED, true), 3);
        }
        world.b(blockposition, blockposition);
        world.makeSound(blockposition.getX() + 0.5, blockposition.getY() + 0.5, blockposition.getZ() + 0.5, "random.click", 0.3f, 0.6f);
        this.c(world, blockposition, iblockdata.get(FACING), block);
        world.a(blockposition, block);
    }

    private boolean hasCurrent(World world, BlockPosition blockposition, IBlockData iblockdata) {
        boolean powered = iblockdata.get(POWERED);
        org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
        int old = powered ? 15 : 0;
        int current = powered ? 0 : 15;
        BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, old, current);
        world.getServer().getPluginManager().callEvent(eventRedstone);
        return eventRedstone.getNewCurrent() > 0 == powered;
    }

    private void c(World world, BlockPosition blockposition, EnumDirection enumdirection, Block block) {
        world.applyPhysics(blockposition, block);
        world.applyPhysics(blockposition.shift(enumdirection.opposite()), block);
    }

    public static boolean isHeld() {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition blockposition = new BlockPosition(location.getX(), location.getY(), location.getZ());
        IBlockData iblockdata = world.getType(blockposition);
        return iblockdata.get(POWERED);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        RemoteButton.location = location;
    }

}
