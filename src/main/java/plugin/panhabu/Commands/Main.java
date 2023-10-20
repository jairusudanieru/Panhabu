package plugin.panhabu.Commands;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main implements TabCompleter, CommandExecutor {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> args1 = new ArrayList<>();
            args1.add("reload");
            return args1;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1 && args[0].equals("reload")) {
            Configuration.reloadConfig();
            sender.sendMessage(Configuration.formatString("&bConfiguration successfully reloaded"));
            return true;
        }
        return true;
    }

}
