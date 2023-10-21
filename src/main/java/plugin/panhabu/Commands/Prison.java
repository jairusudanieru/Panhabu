package plugin.panhabu.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.PluginFunctions.Prisoners;
import plugin.panhabu.PluginFunctions.PrisonersFile;

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
                return numberArgs();
            case 3:
                return stringArgs();
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Location prisonLocation = Configuration.getLocation("locations.prisonLocation");
                if (prisonLocation != null) player.teleport(prisonLocation);
            } else {
                sender.sendMessage(Configuration.formatString("&cPlease provide the player!"));
            }
           return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sender.sendMessage(Configuration.formatString("&cCan't find that player!"));
            return true;
        }

        if (args.length == 1 || args.length == 2) {
            sender.sendMessage(Configuration.formatString("&cPlease provide the time!"));
            return true;
        }

        if (args.length > 3) {
            sender.sendMessage(Configuration.formatString("&cInvalid command usage!"));
            return true;
        }

        int cooldown = cooldown(args[1], args[2]);
        ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners." + target.getName());
        if (section != null) {
            sender.sendMessage(Configuration.formatString("&cPlayer is already in the prison!"));
            return true;
        }

        PrisonersFile.getFile().set("prisoners." + target.getName() + ".cooldown", cooldown);
        PrisonersFile.saveFile();
        Prisoners.addToTeam(target.getName());
        sender.sendMessage(Configuration.formatString("&aAdded player to the prison!"));
        sender.sendMessage(Configuration.formatString("&aPlayer: &r" + target.getName() + " &aCooldown: &r" + cooldown + "s"));

        return true;
    }

    public static List<String> numberArgs() {
        List<String> numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbers.add(Integer.toString(i));
        }
        return numbers;
    }

    public static List<String> stringArgs() {
        List<String> strings = new ArrayList<>();
        strings.add("seconds");
        strings.add("minutes");
        strings.add("hours");
        strings.add("days");
        return strings;
    }

    public static int cooldown(String args1, String args2) {
        int number = 3600;
        try {
            number = Integer.parseInt(args1);
        } catch (NumberFormatException exception) {
            return number;
        }

        switch (args2.toLowerCase()) {
            case "seconds":
                return number;
            case "minutes":
                return number * 60;
            case "hours":
                return number * 60 * 60;
            case "days":
                return number * 60 * 60 * 24;
            default:
                return 3600;
        }
    }

}
