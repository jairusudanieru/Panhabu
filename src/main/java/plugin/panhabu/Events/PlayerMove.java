package plugin.panhabu.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.PluginFunctions.Prisoners;

public class PlayerMove implements Listener {

   @EventHandler
   public void onPlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      boolean isPrisoner = Prisoners.isPrisoner(player);
      boolean inPrisonCell = Prisoners.inPrisonCell(player);

      Plugin authMeReloaded = Bukkit.getPluginManager().getPlugin("AuthMe");
      if (authMeReloaded != null && !AuthMeApi.getInstance().isAuthenticated(player)) return;

      Location prisonLocation = Configuration.getLocation("locations.prisonLocation");
      if (prisonLocation == null) return;

      if (!isPrisoner && prisonLocation.getWorld() == player.getWorld()) {
         if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) player.removePotionEffect(PotionEffectType.INVISIBILITY);
         if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
         if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) player.removePotionEffect(PotionEffectType.BLINDNESS);
      }

      if (isPrisoner && inPrisonCell) {
         player.setGameMode(GameMode.ADVENTURE);
         player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 1, false, false));
         if (player.hasPotionEffect(PotionEffectType.GLOWING)) player.removePotionEffect(PotionEffectType.GLOWING);
         if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) player.removePotionEffect(PotionEffectType.BLINDNESS);
      } else if (isPrisoner) {
         player.removePotionEffect(PotionEffectType.INVISIBILITY);
         player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 1, false, false));
         player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 1, false, false));
      }
   }

   @EventHandler
   public void onVoidEvent(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      Location spawnLocation = Configuration.getLocation("locations.spawnLocation");
      if (spawnLocation == null) spawnLocation = player.getWorld().getSpawnLocation();
      World lobbyWorld = Configuration.getLocation("locations.spawnLocation").getWorld();
      World playerWorld = player.getWorld();
      if (playerWorld != lobbyWorld) return;
      double playerY = player.getLocation().getY();

      double voidLevel = Configuration.getDouble("config.voidLevel");
      if (playerY <= voidLevel) {
         player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 60, 5, false, false));
         wait(3000);
         player.teleport(spawnLocation);
      }
   }

   public static void wait(int ms) {
      try {
         Thread.sleep(ms);
      } catch(InterruptedException error) {
         Thread.currentThread().interrupt();
      }
   }

}
