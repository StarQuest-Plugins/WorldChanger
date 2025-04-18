package WorldChange.worldChange.Inventory;

import Config_Files.CustomConfigFile;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayersInventory {

    private static String getInventoryPath(Player player, String worldName, String section) {
        return player.getDisplayName() + "." + worldName + "." + section;
    }

    private static void saveInventorySection(Player player, String worldName, String section, ItemStack[] contents) {
        String path = getInventoryPath(player, worldName, section);
        String serialized = SerializeInventory.itemStackArrayToBase64(contents);

        if (!CustomConfigFile.get().contains(path)) {
            CustomConfigFile.get().addDefault(path, serialized);
        } else {
            CustomConfigFile.get().set(path, serialized);
        }

        CustomConfigFile.save();
    }

    public static void SavePlayerInventory(Player player) {
        String worldName = player.getWorld().getName();

        saveInventorySection(player, worldName, "Inventory", player.getInventory().getContents());
        saveInventorySection(player, worldName, "Armors", player.getInventory().getArmorContents());
    }

    public void SaveEmptyInventory(Player player) {
        String worldName = player.getWorld().getName(); // используем имя мира, а не player.getWorld()
        saveInventorySection(player, worldName, "Inventory", new ItemStack[0]);
        saveInventorySection(player, worldName, "Armors", new ItemStack[0]);
    }

    public static void LoadPlayerInventory(WorldCreator world, Player player) {
        String worldName = world.name();

        try {
            String invPath = getInventoryPath(player, worldName, "Inventory");
            if (CustomConfigFile.get().contains(invPath)) {
                ItemStack[] contents = SerializeInventory.itemStackArrayFromBase64(CustomConfigFile.get().getString(invPath));
                player.getInventory().setContents(contents);
            } else {
                player.getInventory().clear();
            }

            String armorPath = getInventoryPath(player, worldName, "Armors");
            if (CustomConfigFile.get().contains(armorPath)) {
                ItemStack[] armors = SerializeInventory.itemStackArrayFromBase64(CustomConfigFile.get().getString(armorPath));
                player.getInventory().setArmorContents(armors);
            }

            player.updateInventory();

        } catch (Exception e) {
            e.printStackTrace(); // Можно логировать или вывести сообщение
        }
    }
}
