package kr.kieran.cannoncore.tntsound.commands;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.command.CoreCommand;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TNTSoundCommand extends CoreCommand {

    public TNTSoundCommand(Configuration config, CommandType type) {
        super(config, type);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Chat.color(config.getConfig().getString("not-a-player")));
            return;
        }

        Player player = (Player) sender;
        if (CannonCore.getInstance().getTntSound().getToggledPlayers().contains(player.getUniqueId())) {
            CannonCore.getInstance().getTntSound().getToggledPlayers().remove(player.getUniqueId());
            player.sendMessage(Chat.color(config.getConfig().getString("tntsound-disabled")));
            return;
        }
        CannonCore.getInstance().getTntSound().getToggledPlayers().add(player.getUniqueId());
        player.sendMessage(Chat.color(config.getConfig().getString("tntsound-enabled")));
    }

}
