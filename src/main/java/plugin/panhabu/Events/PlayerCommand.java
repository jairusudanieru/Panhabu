package plugin.panhabu.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.PluginFunctions.PrisonersFile;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommand implements Listener {

   @EventHandler
   public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
      Player player = event.getPlayer();
      if (!AuthMeApi.getInstance().isAuthenticated(player)) return;
      ConfigurationSection section = PrisonersFile.getFile().getConfigurationSection("prisoners");
      if (section == null) return;

      List<String> playerNames = new ArrayList<>();
      for (String name : section.getKeys(false)) {
         OfflinePlayer target = Bukkit.getOfflinePlayer(name);
         if (!target.hasPlayedBefore()) continue;
         playerNames.add(target.getName());
      }

      if (!playerNames.contains(player.getName())) return;
      event.setCancelled(true);
      player.sendMessage(Configuration.formatString("&cYou can't use Commands while in Prison!"));
   }
}
