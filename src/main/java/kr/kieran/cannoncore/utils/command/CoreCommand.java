package kr.kieran.cannoncore.utils.command;

import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import kr.kieran.cannoncore.utils.interfaces.cCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CoreCommand implements CommandExecutor, cCommand {

    protected Configuration config;
    private CommandType commandName;

    public CoreCommand(Configuration config, CommandType commandName) {
        this.config = config;
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(config.getConfig().getString(commandName.getPermissionPath()))) {
            sender.sendMessage(Chat.color(config.getConfig().getString(commandName.getNoPermissionMsgPath())));
            return false;
        }
        execute(sender, command, args);
        return true;
    }

    @Override
    public void reload(Configuration var1) {
        this.config = var1;
    }


}
