package plugin.panhabu;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class Configuration {

   private static final Panhabu plugin = JavaPlugin.getPlugin(Panhabu.class);

   public static String getString(String key) {
      String string = plugin.getConfig().getString(key);
      if (string == null) return "";
      return ChatColor.translateAlternateColorCodes('&',string);
   }

   public static Location getLocation(String key) {
      return plugin.getConfig().getLocation(key);
   }

   public static double getDouble(String key) {
      return plugin.getConfig().getDouble(key);
   }

   public static void reloadConfig() {
      plugin.reloadConfig();
   }

   public static void setLocation(String key, Location location) {
      if (key == null || location == null) return;
      plugin.getConfig().set(key, location);
      plugin.saveConfig();
   }

   public static String formatString(String string) {
      if (string == null) return "";
      return ChatColor.translateAlternateColorCodes('&',string);
   }

}
