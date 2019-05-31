package kr.kieran.cannoncore.remotebutton.commands;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.remotebutton.enums.ButtonPressType;
import kr.kieran.cannoncore.remotebutton.utils.RemoteButton;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.command.CoreCommand;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoteButtonCommand extends CoreCommand {

    public RemoteButtonCommand(Configuration config, CommandType type) {
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
            player.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
            return;
        }
        switch (args[0]) {
            case "reset":
                if (CannonCore.getInstance().getRemoteButton().getButtonLocations().containsKey(player.getUniqueId())) {
                    CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
                    player.sendMessage(Chat.color(config.getConfig().getString("button-reset")));
                    break;
                }
                player.sendMessage(Chat.color(config.getConfig().getString("no-button-set")));
                break;
            case "fire":
                if (!CannonCore.getInstance().getRemoteButton().getButtonLocations().containsKey(player.getUniqueId())) {
                    player.sendMessage(Chat.color(config.getConfig().getString("no-button-set")));
                    break;
                }
                try {
                    new RemoteButton(CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId()), ButtonPressType.NORMAL, 0.0);
                } catch (Exception e) {
                    player.sendMessage(Chat.color(config.getConfig().getString("no-button")));
                    CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
                    break;
                }
                Location fireLocation = CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId());
                player.sendMessage(Chat.color(config.getConfig().getString("button-fired").replace("{coords}", fireLocation.getX() + ", " + fireLocation.getY() + ", " + fireLocation.getZ()).replace("{time}", String.valueOf(0.0))));
                break;
            case "hold":
                if (!CannonCore.getInstance().getRemoteButton().getButtonLocations().containsKey(player.getUniqueId())) {
                    player.sendMessage(Chat.color(config.getConfig().getString("no-button-set")));
                    break;
                }
                if (args.length == 1) {
                    try {
                        new RemoteButton(CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId()), ButtonPressType.HOLD, 0.0);
                    } catch (Exception e) {
                        player.sendMessage(Chat.color(config.getConfig().getString("no-button")));
                        CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
                        break;
                    }
                    if (RemoteButton.isHeld()) {
                        player.sendMessage(Chat.color(config.getConfig().getString("button-on")));
                        break;
                    }
                    player.sendMessage(Chat.color(config.getConfig().getString("button-off")));
                    break;
                }
                double duration;
                try {
                    duration = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Chat.color(config.getConfig().getString("not-a-number").replace("{var}", args[1])));
                    break;
                }
                duration = Math.round(duration * 20) / 20;
                try {
                    new RemoteButton(CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId()), ButtonPressType.HOLD, duration);
                } catch (Exception e) {
                    player.sendMessage(Chat.color(config.getConfig().getString("no-button")));
                    CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
                    break;
                }
                Location holdLocation = CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId());
                player.sendMessage(Chat.color(config.getConfig().getString("button-fired").replace("{coords}", holdLocation.getX() + ", " + holdLocation.getY() + ", " + holdLocation.getZ()).replace("{time}", String.valueOf(duration))));
                break;
            default:
                player.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
                break;
        }
    }

}
