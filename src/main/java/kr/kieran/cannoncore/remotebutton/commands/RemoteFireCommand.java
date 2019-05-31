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

public class RemoteFireCommand extends CoreCommand {

    public RemoteFireCommand(Configuration config, CommandType type) {
        super(config, type);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.color(config.getConfig().getString("not-a-player")));
            return;
        }
        Player player = (Player) sender;
        if (!CannonCore.getInstance().getRemoteButton().getButtonLocations().containsKey(player.getUniqueId())) {
            player.sendMessage(Chat.color(config.getConfig().getString("no-button-set")));
            return;
        }
        try {
            new RemoteButton(CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId()), ButtonPressType.NORMAL, 0.0);
        } catch (Exception e) {
            player.sendMessage(Chat.color(config.getConfig().getString("no-button")));
            CannonCore.getInstance().getRemoteButton().getButtonLocations().remove(player.getUniqueId());
            return;
        }
        Location location = CannonCore.getInstance().getRemoteButton().getButtonLocations().get(player.getUniqueId());
        player.sendMessage(Chat.color(config.getConfig().getString("button-fired").replace("{coords}", location.getX() + ", " + location.getY() + ", " + location.getZ()).replace("{time}", String.valueOf(0.0))));
    }
    
}
