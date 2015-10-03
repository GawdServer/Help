package io.github.gawdserver.help.json;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Vinnie on 6/22/2015.
 */
public class CommandList {
	private final HashMap<String, CommandInfo> commands;

	public CommandList(HashMap<String, CommandInfo> commands) {
		this.commands = commands;
	}

	public CommandInfo getCommand(String name) {
		return commands.get(name);
	}

	public Set<String> getCommands() {
		return commands.keySet();
	}
}
