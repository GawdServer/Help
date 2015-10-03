package io.github.gawdserver.help.json;

/**
 * Created by Vinnie on 6/22/2015.
 */
public class CommandInfo {
	private final String description;
	private final String usage;
	private final String permission;

	public CommandInfo(String description, String usage, String permission) {
		this.description = description;
		this.usage = usage;
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}
}
