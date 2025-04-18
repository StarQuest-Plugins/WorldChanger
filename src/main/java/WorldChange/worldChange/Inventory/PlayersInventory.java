package WorldChange.worldChange.Inventory;

import Config_Files.CustomConfigFile;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayersInventory {

    public static void SavePlayerInventory(Player player) {
        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Inventory")) {
            CustomConfigFile.get().addDefault(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Inventory",
                    SerializeInventory.itemStackArrayToBase64(player.getInventory().getContents()));
        }
        else CustomConfigFile.get().set(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Inventory",
                SerializeInventory.itemStackArrayToBase64(player.getInventory().getContents()));
        CustomConfigFile.save();

        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Armors")) {
            CustomConfigFile.get().addDefault(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Armors",
                    SerializeInventory.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        }
        else CustomConfigFile.get().set(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Armors",
                SerializeInventory.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        CustomConfigFile.save();
    }

    public static void LoadPlayerInventory(WorldCreator world, Player player) {

        if (!CustomConfigFile.get().contains(player.getDisplayName()+"." + world.name()+"." + "Inventory")) {
            player.getInventory().clear();
        } else {
           try {
               ItemStack[] itemstack = SerializeInventory.itemStackArrayFromBase64(CustomConfigFile.get().getString(player.getDisplayName() + "." + world.name() + "." + "Inventory"));
               player.getInventory().setContents(itemstack);
               player.updateInventory();
           }
           catch (java.io.IOException e) {

           }
        }

        if (!CustomConfigFile.get().contains(player.getDisplayName()+"." + world.name()+"." + "Armors")) {
            player.getInventory().clear();
        } else {
            try {
                ItemStack[] itemstack = SerializeInventory.itemStackArrayFromBase64(CustomConfigFile.get().getString(player.getDisplayName() + "." + world.name() + "." + "Armors"));
                player.getInventory().setArmorContents(itemstack);
                player.updateInventory();
            }
            catch (java.io.IOException e) {

            }
        }
    }
}
