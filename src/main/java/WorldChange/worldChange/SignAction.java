package WorldChange.worldChange;

import Config_Files.CustomConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class SignAction {

    public static void handleSignClick(PlayerInteractEvent event, Block block) {
        Sign sign = (Sign) block.getState();
        Player player = event.getPlayer();

        for (String line : sign.getLines()) {
            String worldName = line.trim();

            // Пропускаем пустые строки
            if (worldName.isEmpty()) continue;

            // Проверяем, если мир в списке в конфиге
            List<String> validWorlds = CustomConfigFile.getWorlds();
            if (validWorlds.contains(worldName)) {
                World targetWorld = Bukkit.getWorld(worldName);

                // Загружаем мир, если он не был загружен
                if (targetWorld == null) {
                    targetWorld = Bukkit.getServer().createWorld(new WorldCreator(worldName));
                }

                if (targetWorld != null) {
                    WorldChanger.WorldChange(targetWorld, player);
                    return;
                }
            }
        }

        player.sendMessage("Мир не найден. Убедитесь, что имя указано на табличке.");
    }
}

