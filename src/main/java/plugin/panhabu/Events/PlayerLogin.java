package plugin.panhabu.Events;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
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

   @EventHandler
   public void onAuthLogin(LoginEvent event) {
      Player player = event.getPlayer();
      Location authLocation = Configuration.getLocation("locations.authLocation");
      if (authLocation == null) return;
      if (authLocation.getWorld() != player.getLocation().getWorld()) return;
      if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) player.removePotionEffect(PotionEffectType.INVISIBILITY);
      if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
      if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) player.removePotionEffect(PotionEffectType.BLINDNESS);
   }

}
