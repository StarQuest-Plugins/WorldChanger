package WorldChange.worldChange;

import Config_Files.CustomConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WorldChange extends JavaPlugin {
    private final Logger logger = Bukkit.getLogger();
    private final String pluginName = "WorldChanger";

    public static ItemStack[] playerInventory;

    @Override
    public void onEnable() {
        logger.info(pluginName + " onEnable");

        WorldChanger.WorldCreation();

        getServer().getPluginManager().registerEvents(new Handler(), this);

        var changeCommandName = "worldchange";
        var changeCommand = getCommand(changeCommandName);
        if (changeCommand == null) {
            var errorMessage = pluginName + " command '" + changeCommandName + "' not found";
            logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        changeCommand.setExecutor(new ChangeCommandExecutor());
        // poolCommand.setTabCompleter(new PoolTabCompleter());

        var addCommandName = "worldadd";
        var addCommand = getCommand(addCommandName);
        if (addCommand == null) {
            var errorMessage = pluginName + " command '" + addCommandName + "' not found";
            logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        addCommand.setExecutor(new AddCommandExecutor());
        // poolCommand.setTabCompleter(new PoolTabCompleter());

        var removeCommandName = "worldremove";
        var removeCommand = getCommand(removeCommandName);
        if (removeCommand == null) {
            var errorMessage = pluginName + " command '" + removeCommandName + "' not found";
            logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        removeCommand.setExecutor(new RemoveCommandExecutor());
        // poolCommand.setTabCompleter(new PoolTabCompleter());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        CustomConfigFile.setup(this);
        CustomConfigFile.get().addDefault("Server","OrlovDev");
        CustomConfigFile.get().options().copyDefaults(true);
        CustomConfigFile.save();
    }

    public Plugin GetPlugin(){
        return this;
    }
}
