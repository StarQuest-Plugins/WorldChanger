package WorldChange.worldChange;

import Config_Files.CustomConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * Данный плагин создает возможность перемещаться между существующими мирами
 * При перемещении в конфиге сохраняется информация о локации и инвентаре игрока,
 * таким образом за каждым миром закрепляется своя позиция и свой инвентарь.
 */
public final class WorldChange extends JavaPlugin {
    private final Logger logger = Bukkit.getLogger();
    private final String pluginName = "WorldChanger";

    @Override
    public void onEnable() {
        logger.info(pluginName + " onEnable");

        getServer().getPluginManager().registerEvents(new Handler(), this);

        // Подгружаем и настраиваем конфиг
        CustomConfigFile.setup(this);

        // Устанавливаем значения по умолчанию
        CustomConfigFile.get().addDefault("Server", "OrlovDev");
        CustomConfigFile.get().addDefault("worlds", List.of("world"));  // Добавляем мир по умолчанию

        // Копируем дефолтные значения и сохраняем конфиг
        CustomConfigFile.get().options().copyDefaults(true);
        CustomConfigFile.save();
    }

    public Plugin GetPlugin(){
        return this;
    }
}
