package WorldChange.worldChange;

import Config_Files.CustomConfigFile;
import WorldChange.worldChange.Inventory.PlayersInventory;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldChanger {

    public static List<WorldCreator> listOfWorlds = new ArrayList<>();

    public static void WorldCreation(){
        listOfWorlds.add(new WorldCreator("world"));
        listOfWorlds.add(new WorldCreator("Creative"));

        for (WorldCreator worldCreator : listOfWorlds) {
            Bukkit.createWorld(worldCreator);
        }
    }

    public static void WorldChange(WorldCreator world, Player player){
        SaveWorldLocation(player);
        Location worldLocation = GetWorldLocation(world,player);
        PlayersInventory playerInventory = new PlayersInventory();
        playerInventory.SavePlayerInventory(player);
        player.teleport(worldLocation);
        playerInventory.LoadPlayerInventory(world,player);
    }

    public static void AddWorldToBase(String worldName, Player player){
        if (!CustomConfigFile.get().contains("World." + worldName)) {
            CustomConfigFile.get().addDefault("World." + worldName, worldName);
            player.sendMessage("World " + worldName + " has been added");
        }else player.sendMessage("World " + worldName + " has already been added");
        CustomConfigFile.save();
    }

    public static void RemoveWorldFromBase(String worldName, Player player){
        if (!CustomConfigFile.get().contains("World." + worldName)) {
            player.sendMessage("World " + worldName + " doesn't exist");
        } else {
            CustomConfigFile.get().set("World." + worldName, null);
            player.sendMessage("World " + worldName + " has been removed");
        }
        CustomConfigFile.save();
    }

    private static void SaveWorldLocation(Player player) {
        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location")) {
            CustomConfigFile.get().addDefault(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location", player.getLocation());
        }
        CustomConfigFile.get().set(player.getDisplayName()+"."+player.getWorld().getName()+"."+"Location",player.getLocation());
        CustomConfigFile.save();
    }

    private static Location GetWorldLocation(WorldCreator world,Player player) {
        if (!CustomConfigFile.get().contains(player.getDisplayName()+"."+world.name()+"."+"Location")) {
            try {
                CustomConfigFile.get().addDefault(player.getDisplayName()+"."+world.name()+"."+"Location", Bukkit.getWorld(world.name()).getSpawnLocation());
            }
            catch (NullPointerException e) {

            }
        }
        return CustomConfigFile.get().getLocation(player.getDisplayName()+"."+world.name()+"."+"Location");
    }
}
