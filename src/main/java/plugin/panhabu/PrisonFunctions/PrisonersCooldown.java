package plugin.panhabu.PrisonFunctions;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.panhabu.Panhabu;

public class PrisonersCooldown {

   private final static Panhabu plugin = JavaPlugin.getPlugin(Panhabu.class);

   public static void checkCooldown() {
      Bukkit.getScheduler().runTaskTimer(plugin, () -> {
         for (Player player : Bukkit.getOnlinePlayers()) {
            if (isPlayerInJail(player) && player.isOnline()) {
               int cooldown = getRemainingCooldown(player);
               if (cooldown > 0) {
                  setRemainingCooldown(player, cooldown - 1);
               } else {
                  PrisonersFile.getFile().set("prisoners." + player.getName(), null);
                  PrisonersFile.saveFile();
                  Prisoners.removeFromTeam(player.getName());
               }
            }
         }
      }, 0L, 20L);
   }

   private static boolean isPlayerInJail(Player player) {
      ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners");
      if (section == null) return false;
      for (String name : section.getKeys(false)) {
         if (name.equals(player.getName())) return true;
      }
      return false;
   }

   private static int getRemainingCooldown(Player player) {
      return PrisonersFile.getFile().getInt("prisoners." + player.getName() + ".cooldown");
   }

   private static void setRemainingCooldown(Player player, int cooldown) {
      PrisonersFile.getFile().set("prisoners." + player.getName() + ".cooldown", cooldown);
      PrisonersFile.saveFile();
   }

}
