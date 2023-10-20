package plugin.panhabu.PrisonFunctions;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.panhabu.Configuration;
import plugin.panhabu.Panhabu;

import java.util.HashMap;
import java.util.Map;

public class PrisonersCooldown {

   private final static Panhabu plugin = JavaPlugin.getPlugin(Panhabu.class);
   private final static Map<Player, BossBar> bossBarMap = new HashMap<>();

   public static void checkCooldown() {
      Bukkit.getScheduler().runTaskTimer(plugin, () -> {
         for (Player player : Bukkit.getOnlinePlayers()) {
            if (Prisoners.isPlayerInJail(player) && Prisoners.inPrisonCell(player) && player.isOnline()) {
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

   public static void checkBossBar() {
      if (!Configuration.getBoolean("config.prisonBossBar")) return;
      String title = "Prison Time: ";
      Bukkit.getScheduler().runTaskTimer(plugin, () -> {
         for (Player player : Bukkit.getOnlinePlayers()) {
            if (Prisoners.isPlayerInJail(player) && player.isOnline()) {
               int cooldown = getRemainingCooldown(player);
               BossBar bossBar = bossBarMap.get(player);
               if (bossBar == null) {
                  bossBar = Bukkit.createBossBar(title + "s", BarColor.WHITE, BarStyle.SEGMENTED_10);
                  bossBar.addPlayer(player);
                  bossBarMap.put(player, bossBar);
               }

               if (cooldown == 0) {
                  bossBar.removePlayer(player);
                  bossBarMap.remove(player);
               } else {
                  bossBar.setTitle(title + cooldown + " seconds");
               }
            } else {
               BossBar bossBar = bossBarMap.get(player);
               if (bossBar == null) return;
               bossBar.removePlayer(player);
               bossBarMap.remove(player);
            }
         }
      }, 0L, 20L);
   }

   public static int getRemainingCooldown(Player player) {
      return PrisonersFile.getFile().getInt("prisoners." + player.getName() + ".cooldown");
   }

   public static void setRemainingCooldown(Player player, int cooldown) {
      PrisonersFile.getFile().set("prisoners." + player.getName() + ".cooldown", cooldown);
      PrisonersFile.saveFile();
   }

}
