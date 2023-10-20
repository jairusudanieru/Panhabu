package plugin.panhabu.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.Configuration;
import plugin.panhabu.PrisonFunctions.PrisonCommand;
import plugin.panhabu.PrisonFunctions.Prisoners;
import plugin.panhabu.PrisonFunctions.PrisonersFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prison implements TabCompleter, CommandExecutor {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        switch (args.length) {
            case 1:
                List<String> args1 = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    args1.add(player.getName());
                }
                return args1;
            case 2:
                return PrisonCommand.numberArgs();
            case 3:
                return PrisonCommand.stringArgs();
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location prisonLocation = Configuration.getLocation("locations.prisonLocation");
                if (prisonLocation != null) player.teleport(prisonLocation);
            } else {
                sender.sendMessage(Configuration.formatString("&cPlease provide the player!"));
            }
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(Configuration.formatString("&cPlease provide the time!"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sender.sendMessage(Configuration.formatString("&cCan't find that player!"));
            return true;
        }

        if (args.length == 3) {
            int cooldown = PrisonCommand.cooldown(args[1], args[2]);
            ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners." + target.getName());
            if (section != null) {
                sender.sendMessage(Configuration.formatString("&cPlayer is already in the prison!"));
                return true;
            }

            PrisonersFile.getFile().set("prisoners." + target.getName() + ".cooldown", cooldown);
            PrisonersFile.saveFile();
            Prisoners.addToTeam(target.getName());
            sender.sendMessage(Configuration.formatString("&aAdded player to the prison!"));
        }

        return true;
    }

}
