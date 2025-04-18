package WorldChange.worldChange;

import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignAction {


    public static void TableItteract(PlayerInteractEvent event, Block block){
        Sign sign = (Sign) block.getState();
        String[] ln = sign.getLines();
        Player player = event.getPlayer();
        for (String line : ln) {
             if (line.equalsIgnoreCase("Creative")) {
                 WorldChanger.WorldChange(new WorldCreator("Creative"),player);
            }
            else if (line.equalsIgnoreCase("world")) {
                 WorldChanger.WorldChange(new WorldCreator("world"),player);
            }
             else if (line.equalsIgnoreCase("Save")) {
                 for (int i = 0; i < player.getInventory().getSize(); i++) {
                     WorldChange.playerInventory[i] = player.getInventory().getItem(i);
                 }
                System.out.println("inventory saved");
             }
             else if (line.equalsIgnoreCase("Load")) {
                 for (int i = 0; i < player.getInventory().getSize(); i++) {
                     player.getInventory().setItem(i, WorldChange.playerInventory[i]);
                 }
                 player.updateInventory();
                 System.out.println("inventory loaded");
             }
        }
    }
}
