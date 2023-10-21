package plugin.panhabu.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.Panhabu;
import plugin.panhabu.PluginFunctions.Prisoners;

public class PlayerJoin implements Listener {

   private final Panhabu plugin = JavaPlugin.getPlugin(Panhabu.class);

   @EventHandler
   public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
      Player player = event.getPlayer();
      Plugin authMeReloaded = Bukkit.getPluginManager().getPlugin("AuthMe");
      if (authMeReloaded == null) return;

      if (!AuthMeApi.getInstance().isAuthenticated(player)) {
         Location authLocation = Configuration.getLocation("locations.authLocation");
         if (authLocation == null) authLocation = player.getWorld().getSpawnLocation();
         event.setSpawnLocation(authLocation);
      }
   }

   @EventHandler
   public void onPlayerLog(PlayerJoinEvent event) {
      event.joinMessage(null);
      Player player = event.getPlayer();
      Plugin authMeReloaded = Bukkit.getPluginManager().getPlugin("AuthMe");
      boolean invisibleOnAuth = Configuration.getBoolean("config.invisibleOnAuth");
      Location authLocation = Configuration.getLocation("locations.authLocation");
      if (authLocation == null) return;

      if (authLocation.getWorld() != player.getLocation().getWorld()) return;
      if (authMeReloaded == null || !invisibleOnAuth) return;
      if (!AuthMeApi.getInstance().isAuthenticated(player)) {
         if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
         if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) player.removePotionEffect(PotionEffectType.BLINDNESS);
         player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 1, false, false));
      }
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      event.joinMessage(null);
      Plugin authMeReloaded = Bukkit.getPluginManager().getPlugin("AuthMe");
      if (authMeReloaded != null) return;

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
