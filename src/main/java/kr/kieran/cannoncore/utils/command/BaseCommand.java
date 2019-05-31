package kr.kieran.cannoncore.utils.command;

import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.CommandType;
import kr.kieran.cannoncore.utils.enums.ConfigType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand extends CoreCommand {

    public BaseCommand(Configuration config, CommandType type) {
        super(config, type);
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
            return;
        }
        switch (args[0]) {
            case "reload":
                CannonCore.getInstance().reloadConfigurations();
                sender.sendMessage(Chat.color(config.getConfig().getString("config-reloaded")));
                break;
            case "config":
                if (args.length < 3) {
                    sender.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
                    break;
                }
                String configName = args[1].toUpperCase() + "_CONFIG";
                Configuration configuration;
                try {
                    configuration = CannonCore.getInstance().getConfigByName(ConfigType.valueOf(configName));
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(Chat.color(config.getConfig().getString("invalid-config").replace("{config}", args[1])));
                    break;
                }
                if (configuration.getConfig().getString(args[2]) == null) {
                    sender.sendMessage(Chat.color(config.getConfig().getString("invalid-path").replace("{path}", args[2])).replace("{config}", args[1]));
                    break;
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    builder.append(args[i]).append(" ");
                }
                String newVal = builder.toString().trim();
                configuration.getConfig().set(args[2], newVal);
                configuration.save();
                sender.sendMessage(Chat.color(config.getConfig().getString("set-path").replace("{path}", args[2])).replace("{value}", newVal).replace("{config}", args[1]));
                break;
            default:
                sender.sendMessage(Chat.color(config.getConfig().getString("invalid-usage")));
                break;
        }
    }

}
