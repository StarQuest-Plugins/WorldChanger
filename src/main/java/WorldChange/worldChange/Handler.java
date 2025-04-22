package WorldChange.worldChange;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Handler implements Listener {
    private final Map<UUID, World> deathWorlds = new HashMap<>();

    /**
     * Единственный способ перемещения между мирами
     * @param event
     */
    @EventHandler
    public void onRightClickSign(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block != null && block.getState() instanceof Sign) {
                SignAction.handleSignClick(event, block);
            }
        }
    }

    @EventHandler
    public void PlayerChangeTheWorld(PlayerChangedWorldEvent event) {
        var player = event.getPlayer();
        var worldName = player.getWorld().getName();

        if (worldName.equals("Creative")) {
            player.setGameMode(GameMode.CREATIVE);
        } else {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getWorld().getName().equalsIgnoreCase("Creative")) {
            deathWorlds.put(player.getUniqueId(), player.getWorld());
        }
    }

    // Устанавливаем точку респавна в том же мире
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (deathWorlds.containsKey(uuid)) {
            World creativeWorld = deathWorlds.get(uuid);
            Location spawnLocation = creativeWorld.getSpawnLocation();
            event.setRespawnLocation(spawnLocation);

            // Не забываем очистить, чтобы не было лишних записей
            deathWorlds.remove(uuid);
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        World currentWorld = player.getWorld();

        if (currentWorld.getName().equalsIgnoreCase("Creative")) {
            // Отменяем телепортацию
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Вы не можете покинуть мир Creative через портал!");
        }
    }
}
