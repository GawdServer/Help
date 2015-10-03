package io.github.gawdserver.help;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.github.gawdserver.api.plugin.Plugin;
import io.github.gawdserver.api.plugin.PluginDir;
import io.github.gawdserver.api.plugin.PluginInfo;
import io.github.gawdserver.help.json.CommandList;
import io.github.gawdserver.help.json.PluginList;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vinnie on 2/18/2015.
 */
public class Help implements Plugin {
    private static final Logger logger = Logger.getLogger("Help");

    public static void searchPlugin(File pluginJar) {
        try {
            URL url = new URL("file", null, pluginJar.getAbsolutePath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            InputStream pluginJson = classLoader.getResourceAsStream("plugin.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pluginJson));
            PluginInfo pluginInfo = new Gson().fromJson(reader, PluginInfo.class);
            if (pluginInfo.getName() == null || pluginInfo.getVersion() == null || pluginInfo.getMainClass() == null) {
                logger.log(Level.SEVERE, "Invalid plugin.json for plugin {0}", pluginJar.getName());
                return;
            }
            InputStream helpJson = classLoader.getResourceAsStream("help.json");
            reader = new BufferedReader(new InputStreamReader(helpJson));
            CommandList commandList = new Gson().fromJson(reader, CommandList.class);
            PluginList.add(pluginInfo.getName(), commandList);
            logger.log(Level.INFO, "Loaded help for plugin {0}", pluginInfo.getName());
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No help.json for plugin {0}", pluginJar.getName());
        } catch (JsonSyntaxException e) {
            logger.log(Level.SEVERE, "Error parsing help.json for plugin {0}", pluginJar.getName());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error reading plugin " + pluginJar.getName(), ex);
        }
    }

    @Override
    public void startup() {
        logger.info("Searching plugins for help.json...");
        File[] pluginFiles = PluginDir.getPluginDir().listFiles();
        for (File pluginFile : pluginFiles) {
            // Only accept .jar files
            if (pluginFile.isDirectory() || !pluginFile.getName().endsWith(".jar")) {
                continue;
            }
            searchPlugin(pluginFile);
        }
    }

    @Override
    public void shutdown() {}
}
