package plugin.panhabu.Events;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import plugin.panhabu.Configuration;
import plugin.panhabu.Panhabu;
import plugin.panhabu.PrisonFunctions.Prisoners;

public class PlayerLogin implements Listener {

   private final Panhabu plugin = JavaPlugin.getPlugin(Panhabu.class);

   @EventHandler
   public void onPlayerLogin(LoginEvent event) {
      Player player = event.getPlayer();
      Team prisoner = Prisoners.team();

      Location prisonLocation = Configuration.getLocation("locations.prisonLocation");
      Location spawnLocation = Configuration.getLocation("locations.spawnLocation");

      Bukkit.getScheduler().runTaskLater(plugin, () -> {
         if (prisoner.hasEntry(player.getName())) {
            if (prisonLocation != null) player.teleport(prisonLocation);
         } else {
            if (spawnLocation != null) player.teleport(spawnLocation);
         }
      }, 1L);
   }

}
