package plugin.panhabu.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.PluginFunctions.Prisoners;
import plugin.panhabu.PluginFunctions.PrisonersFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Release implements TabCompleter, CommandExecutor {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners");
        List<String> playerNames = new ArrayList<>();
        if (args.length != 1 || section == null) return Collections.emptyList();
        for (String name : section.getKeys(false)) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
            if (!target.hasPlayedBefore()) continue;
            playerNames.add(target.getName());
        }
        return playerNames;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Configuration.formatString("&cPlease provide the player!"));
            return true;
        }

        if (args.length != 1) return true;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sender.sendMessage(Configuration.formatString("&cCan't find that player!"));
            return true;
        }

        ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners." + target.getName());
        if (section == null) {
            sender.sendMessage(Configuration.formatString("&cPlayer is not in the prison!"));
            return true;
        }

        PrisonersFile.getFile().set("prisoners." + target.getName(), null);
        PrisonersFile.saveFile();
        Prisoners.removeFromTeam(target.getName());
        sender.sendMessage(Configuration.formatString("&aReleased player from the prison!"));
        return true;
    }

}
