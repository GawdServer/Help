package io.github.gawdserver.help.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vinnie on 6/22/2015.
 */
public class PluginList {
	private static final HashMap<String, CommandList> plugins = new HashMap<>();

	public static void add(String plugin, CommandList list) {
		plugins.put(plugin, list);
	}

	public static boolean hasList(String plugin) {
		return plugins.containsKey(plugin);
	}

	public static CommandList getCommandList(String plugin) {
		return plugins.get(plugin);
	}

	public static Set<String> getPlugins() {
		return plugins.keySet();
	}

	public static Collection<CommandList> getLists() {
		return plugins.values();
	}

	public static Set<Map.Entry<String, CommandList>> getPluginSet() {
		return plugins.entrySet();
	}
}
