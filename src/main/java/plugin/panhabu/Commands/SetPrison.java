package plugin.panhabu.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.panhabu.PluginFunctions.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SetPrison implements TabCompleter, CommandExecutor {

   @Override
   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
      List<String> subcommands = new ArrayList<>();
      subcommands.add("spawn");
      subcommands.add("pos1");
      subcommands.add("pos2");
      return subcommands;
   }

   @Override
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      Player player = (Player) sender;
      Location prisonLocation = player.getLocation();
      if (args.length != 1) {
         player.sendMessage(Configuration.formatString("&cInvalid command usage!"));
         return true;
      }

      if (args[0].equals("spawn")) {
         Configuration.setLocation("locations.prisonLocation", prisonLocation);
         player.sendMessage(Configuration.formatString("&aPrison Location has been set!"));
         return true;
      }

      double x = player.getLocation().getX();
      double y = player.getLocation().getY();
      double z = player.getLocation().getZ();

      if (args[0].equals("pos1")) {
         Configuration.setLocation("prison.pos1", player.getLocation());
         player.sendMessage(Configuration.formatString("&aPrison Pos1 set to " + x + " " + y + " " + z));
         return true;
      }

      if (args[0].equals("pos2")) {
         Configuration.setLocation("prison.pos2", player.getLocation());
         player.sendMessage(Configuration.formatString("&aPrison Pos2 set to " + x + " " + y + " " + z));
         return true;
      }

      player.sendMessage(Configuration.formatString("&cInvalid command usage!"));
      return true;
   }

}
