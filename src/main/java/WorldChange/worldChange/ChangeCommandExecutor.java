package WorldChange.worldChange;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class ChangeCommandExecutor implements CommandExecutor {

    private final Logger logger = Bukkit.getLogger();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Subcommand not found");
            return false;
        }

        if (sender instanceof Player) {

            boolean existingWorld = false;

            Player player = (Player) sender;

            for(WorldCreator world : WorldChanger.listOfWorlds){
                if(args[0].equals(world.name())) {
                   player.sendMessage("You are teleported in " + world.name());
                   existingWorld = true;

                   WorldChanger.WorldChange(world, player);
                }
            }

            if(!existingWorld) player.sendMessage(args[0] + " not exist ");
        }
        return true;
    }
}
