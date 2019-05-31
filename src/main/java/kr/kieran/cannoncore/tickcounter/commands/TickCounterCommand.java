package kr.kieran.cannoncore.tickcounter.commands;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.command.CoreCommand;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TickCounterCommand extends CoreCommand {

    public TickCounterCommand(Configuration config, CommandType type) {
        super(config, type);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.color(config.getConfig().getString("not-a-player")));
            return;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Chat.color(config.getConfig().getString("own-inventory-full")));
                return;
            }
            player.getInventory().addItem(CannonCore.getInstance().getTickCounter().getItemUtils().tickCounter(1));
            player.sendMessage(Chat.color(config.getConfig().getString("received-item").replace("{amount}", String.valueOf(1))));
            return;
        }
        if (args.length == 1) {
            player.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
            return;
        }
        if (args.length == 2) {
            addItem(player, args[1], 1);
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(Chat.color(config.getConfig().getString("not-a-number").replace("{var}", args[2])));
            return;
        }
        addItem(player, args[1], amount);
    }

    private void addItem(Player player, String targetName, int amount) {
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            player.sendMessage(Chat.color(config.getConfig().getString("invalid-player").replace("{player}", targetName)));
            return;
        }
        if (target.getInventory().firstEmpty() == -1) {
            player.sendMessage(Chat.color(config.getConfig().getString("inventory-full").replace("{player}", target.getName())));
            return;
        }
        target.getInventory().addItem(CannonCore.getInstance().getTickCounter().getItemUtils().tickCounter(amount));
        player.sendMessage(Chat.color(config.getConfig().getString("given-item").replace("{player}", target.getName()).replace("{amount}", String.valueOf(amount))));
        target.sendMessage(Chat.color(config.getConfig().getString("received-item").replace("{amount}", String.valueOf(amount))));
    }

}
