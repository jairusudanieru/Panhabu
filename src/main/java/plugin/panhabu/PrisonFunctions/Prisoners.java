package plugin.panhabu.PrisonFunctions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import plugin.panhabu.Configuration;

public class Prisoners {

   public static void addToTeam(String playerName) {
      Team team = team();
      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
      if (offlinePlayer.isOnline()) {
         Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
         if (player == null) return;
         Location prisonLocation = Configuration.getLocation("locations.prisonLocation");
         if (prisonLocation == null) prisonLocation = player.getWorld().getSpawnLocation();
         player.teleport(prisonLocation);
      }

      if (team.hasEntry(playerName)) return;
      team.addEntry(playerName);
   }

   public static void removeFromTeam(String playerName) {
      Team team = team();
      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
      if (offlinePlayer.isOnline()) {
         Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
         if (player == null) return;
         Location spawnLocation = Configuration.getLocation("locations.spawnLocation");
         if (spawnLocation == null) spawnLocation = player.getWorld().getSpawnLocation();
         player.teleport(spawnLocation);
      }

      if (!team.hasEntry(playerName)) return;
      team.removeEntry(playerName);
   }

   public static boolean isPlayerInJail(Player player) {
      ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners");
      if (section == null) return false;
      for (String name : section.getKeys(false)) {
         if (name.equals(player.getName())) return true;
      }
      return false;
   }

   public static boolean inPrisonCell(Player player) {
      Location prisonLocation1 = Configuration.getLocation("prison.pos1");
      Location prisonLocation2 = Configuration.getLocation("prison.pos2");

      Location playerLocation = player.getLocation();
      World prisonWorld = prisonLocation1.getWorld();
      World playerWorld = playerLocation.getWorld();

      double x = playerLocation.getX();
      double y = playerLocation.getY();
      double z = playerLocation.getZ();

      if (!prisonWorld.equals(playerWorld)) return false;
      double minX = Math.min(prisonLocation1.getX(), prisonLocation2.getX());
      double maxX = Math.max(prisonLocation1.getX(), prisonLocation2.getX());
      double minY = Math.min(prisonLocation1.getY(), prisonLocation2.getY());
      double maxY = Math.max(prisonLocation1.getY(), prisonLocation2.getY());
      double minZ = Math.min(prisonLocation1.getZ(), prisonLocation2.getZ());
      double maxZ = Math.max(prisonLocation1.getZ(), prisonLocation2.getZ());
      return (x >= minX && x <= maxX) && (y >= minY && y <= maxY) && (z >= minZ && z <= maxZ);
   }

   public static Team team() {
      Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
      String teamName = Configuration.getString("prison.teamName");
      Team team = scoreboard.getTeam(teamName);

      if (team == null) {
         Team newTeam = scoreboard.registerNewTeam("prisoner");
         newTeam.setCanSeeFriendlyInvisibles(true);
         team = newTeam;
      }

      return team;
   }

}
