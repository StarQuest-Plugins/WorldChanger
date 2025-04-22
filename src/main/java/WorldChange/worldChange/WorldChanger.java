package WorldChange.worldChange;

import Config_Files.CustomConfigFile;
import WorldChange.worldChange.Inventory.PlayersInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class WorldChanger {

    public static void WorldChange(World world, Player player){
        SaveWorldLocation(player);
        Location worldLocation = GetWorldLocation(world,player);
        PlayersInventory playerInventory = new PlayersInventory();
        playerInventory.SavePlayerInventory(player);
        player.teleport(worldLocation);
        playerInventory.LoadPlayerInventory(world,player);
    }

    private static void SaveWorldLocation(Player player) {
        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location")) {
            CustomConfigFile.get().addDefault(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location", player.getLocation());
        }
        CustomConfigFile.get().set(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location",player.getLocation());
        CustomConfigFile.save();
    }

    private static Location GetWorldLocation(World world,Player player) {
        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+world.getName()+"."+"Location")) {
            try {
                CustomConfigFile.get().addDefault(player.getDisplayName()+"."+world.getName()+"."+"Location", Bukkit.getWorld(world.getName()).getSpawnLocation());
            }
            catch (NullPointerException e) {

            }
        }
        return CustomConfigFile.get().getLocation(player.getDisplayName()+"."+world.getName()+"."+"Location");
    }
}
