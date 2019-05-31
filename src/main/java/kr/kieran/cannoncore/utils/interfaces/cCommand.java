package kr.kieran.cannoncore.utils.interfaces;

import kr.kieran.cannoncore.utils.configuration.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface cCommand {

    void reload(Configuration var1);

    void execute(CommandSender sender, Command command, String[] args);

}
