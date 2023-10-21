package plugin.panhabu.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.PluginFunctions.Configuration;

import java.util.Collections;
import java.util.List;

public class Spawn implements TabCompleter, CommandExecutor {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        Location spawnLocation = Configuration.getLocation("locations.spawnLocation");

        if (args.length != 0) {
            player.sendMessage(Configuration.formatString("&cInvalid command usage!"));
            return true;
        }

        if (spawnLocation == null) spawnLocation = player.getWorld().getSpawnLocation();
        player.teleport(spawnLocation);
        return true;
    }
}