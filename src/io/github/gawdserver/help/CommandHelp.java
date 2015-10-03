package io.github.gawdserver.help;

import io.github.gawdserver.api.events.Command;
import io.github.gawdserver.api.perms.Permissions;
import io.github.gawdserver.api.player.Sender;
import io.github.gawdserver.api.utils.Chat;
import io.github.gawdserver.api.utils.ColorCodes;
import io.github.gawdserver.help.json.CommandList;
import io.github.gawdserver.help.json.CommandInfo;
import io.github.gawdserver.help.json.PluginList;

/**
 * Created by Vinnie on 2/18/2015.
 */
public class CommandHelp implements Command {

    private void displayCommandInfo(String player, String command, CommandInfo info) {
        // No perm check for console
        if (Sender.CONSOLE.name().equals(player)) {
            Chat.sendMessage(player, command + " - " + info.getDescription());
            Chat.sendMessage(player, "    " + info.getUsage());
            return;
        }
        if (info.getPermission() == null || Permissions.hasPermission(player, info.getPermission())) {
            Chat.sendMessage(player, command + " - " + info.getDescription());
            Chat.sendMessage(player, "    " + ColorCodes.GRAY+ColorCodes.ITALIC+info.getUsage());
        }
    }

    private void pluginHelp(String player, String[] args) {
        Chat.sendMessage(player, ColorCodes.DARK_GREEN+ColorCodes.BOLD+"Commands for plugin " + args[1]);
        CommandList list = PluginList.getCommandList(args[1]);
        for (String command : list.getCommands()) {
            displayCommandInfo(player, command, list.getCommand(command));
        }
    }

    private void commandHelp(String player, String[] args) {
        Chat.sendMessage(player, ColorCodes.DARK_GREEN+ColorCodes.BOLD+"Commands:");
        for (CommandList list : PluginList.getLists()) {
            if (list == null)
                continue;
            for (String command : list.getCommands()) {
                if (command.contains(args[0])) {
                    displayCommandInfo(player, command, list.getCommand(command));
                }
            }
        }
    }

    private void allHelp(String player) {
        // List Plugins
        Chat.sendMessage(player, ColorCodes.DARK_PURPLE+ColorCodes.BOLD+"Plugins:");
        for (String plugin : PluginList.getPlugins()) {
            Chat.sendMessage(player, String.format("-- %s", plugin));
        }

        // List Commands
        Chat.sendMessage(player, ColorCodes.DARK_GREEN+ColorCodes.BOLD+"Commands:");
        for (CommandList list : PluginList.getLists()) {
            for (String command : list.getCommands()) {
                displayCommandInfo(player, command, list.getCommand(command));
            }
        }

    }

    public void onCommand(String player, String[] args) {
        Chat.sendMessage(player, ColorCodes.GOLD+"------ "+ColorCodes.RED+"Command Help"+ColorCodes.GOLD+" ------");
        if (args.length > 0 && args[0].equals("plugin")) {
            if (args.length > 1) {
                if (PluginList.hasList(args[1])) {
                    pluginHelp(player, args);
                } else {
                    Chat.sendMessage(player, ColorCodes.DARK_RED+"This server does not have plugin: " + args[1]);
                }
            } else {
                Chat.sendMessage(player, ColorCodes.DARK_RED+"Use: !help plugin <Plugin Name>");
            }
        } else if (args.length > 0) {
            commandHelp(player, args);
        } else {
            allHelp(player);
        }
        Chat.sendMessage(player, ColorCodes.GOLD+"------------------------");
    }

    @Override
    public void playerCommand(String player, String[] args) {
        if (Permissions.hasPermission(player, "help.use")) {
            onCommand(player, args);
        } else {
            Chat.sendMessage(player, "No permission.");
        }
    }

    @Override
    public void serverCommand(Sender sender, String[] args) {
        onCommand(Sender.CONSOLE.name(), args);
    }

}
