package WorldChange.worldChange;

import WorldChange.worldChange.Inventory.PlayersInventory;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class Handler implements Listener {

    @EventHandler
    public void PlayerChangeTheWorld(PlayerChangedWorldEvent event) {
        var player = event.getPlayer();
        var worldName = player.getWorld().getName();

        WorldCreator world = new WorldCreator(worldName);
        PlayersInventory playerInventory = new PlayersInventory();
        playerInventory.LoadPlayerInventory(world, player);

        if (worldName.equals("world")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        else if (worldName.equals("Creative")) {
            player.setGameMode(GameMode.CREATIVE);
        }
    }

    @EventHandler
    public void PKMOnBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.OAK_SIGN || block.getType() == Material.OAK_WALL_SIGN)
                SignAction.TableItteract(event,block);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayersInventory playerInventory = new PlayersInventory();
        playerInventory.SaveEmptyInventory(player); // Сохраняем текущее состояние
    }
}
