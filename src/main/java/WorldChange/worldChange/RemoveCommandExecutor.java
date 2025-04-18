package WorldChange.worldChange;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RemoveCommandExecutor implements CommandExecutor {

    private final Logger logger = Bukkit.getLogger();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Subcommand not found");
                return false;
            }

            if (sender instanceof Player) {
                WorldChanger.RemoveWorldFromBase(args[0], ((Player) sender).getPlayer());
            }
            return true;
        }
}
