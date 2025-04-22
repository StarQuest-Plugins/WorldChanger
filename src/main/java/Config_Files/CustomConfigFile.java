package Config_Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomConfigFile {
    private static File file;
    private static FileConfiguration customFile;

    public static void setup(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "customconfig.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create customconfig.yml");
                e.printStackTrace();
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file");
            e.printStackTrace();
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    // Получение списка миров
    public static List<String> getWorlds() {
        return customFile.getStringList("worlds");
    }

    // Добавление мира в конфиг
    public static void addWorld(String worldName) {
        List<String> worlds = getWorlds();
        if (!worlds.contains(worldName)) {
            worlds.add(worldName);
            customFile.set("worlds", worlds);
            save();
        }
    }

    // Удаление мира из конфиг
    public static void removeWorld(String worldName) {
        List<String> worlds = getWorlds();
        if (worlds.contains(worldName)) {
            worlds.remove(worldName);
            customFile.set("worlds", worlds);
            save();
        }
    }
}
