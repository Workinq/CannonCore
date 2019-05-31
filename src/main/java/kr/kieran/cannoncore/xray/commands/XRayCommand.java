package kr.kieran.cannoncore.xray.commands;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.command.CoreCommand;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class XRayCommand extends CoreCommand {

    public XRayCommand(Configuration config, CommandType type) {
        super(config, type);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.color(config.getConfig().getString("not-a-player")));
            return;
        }
        Player player = (Player) sender;
        if (CannonCore.getInstance().getXray().getToggledPlayers().containsKey(player.getUniqueId())) {
            CannonCore.getInstance().getXray().getFullyToggled().remove(player.getUniqueId());
            CannonCore.getInstance().getXray().getToggledPlayers().get(player.getUniqueId()).forEach(block -> block.getState().update(true));
            CannonCore.getInstance().getXray().getToggledPlayers().remove(player.getUniqueId());
            player.sendMessage(Chat.color(config.getConfig().getString("xray-disabled")));
            return;
        }
        enableXray(player);
        player.sendMessage(Chat.color(config.getConfig().getString("xray-enabled")));
    }

    private void enableXray(Player player) {
        List<String> disabledBlocks = config.getConfig().getStringList("disabled-blocks");
        Location start = player.getLocation();
        int radius;
        try {
            radius = Integer.parseInt(config.getConfig().getString("radius"));
        } catch (NumberFormatException e) {
            player.sendMessage(Chat.color(config.getConfig().getString("not-a-number").replace("{value}", config.getConfig().getString("radius"))));
            return;
        }
        CompletableFuture.supplyAsync(() -> {
            Set<Block> blocks = new HashSet<>();
            for (double x = start.getX() - radius; x <= start.getX() + radius; x++) {
                for (double y = start.getY() - radius; y <= start.getY() + radius; y++) {
                    for (double z = start.getZ() - radius; z <= start.getZ() + radius; z++) {
                        Block block = new Location(player.getWorld(), x, y, z).getBlock();
                        if (block.getType() == Material.AIR) continue;
                        if (disabledBlocks.contains(block.getType().name())) continue;
                        blocks.add(block);
                    }
                }
            }
            CannonCore.getInstance().getXray().getToggledPlayers().put(player.getUniqueId(), blocks);
            return blocks;
        }).whenCompleteAsync((blocks, throwable) -> blocks.forEach(block -> player.sendBlockChange(block.getLocation(), Material.BARRIER, (byte) 1))).thenRun(() -> CannonCore.getInstance().getXray().getFullyToggled().add(player.getUniqueId()));
    }

}
