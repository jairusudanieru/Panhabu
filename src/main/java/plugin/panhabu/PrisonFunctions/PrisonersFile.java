package plugin.panhabu.PrisonFunctions;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class PrisonersFile {

   private static File file;
   private static FileConfiguration prisonersFile;

   public static void checkFile() {
      Plugin plugin = Bukkit.getPluginManager().getPlugin("Panhabu");
      if (plugin == null) return;
      file = new File(plugin.getDataFolder(), "prisoners.yml");

      if (!file.exists()) {
         try {
            boolean fileCreated = file.createNewFile();
            if (!fileCreated) throw new IOException("Unable to create file at specified path.");
         } catch (IOException exception) {
            Bukkit.getLogger().info("Unable to create prisoners.yml");
         }
      }
      prisonersFile = YamlConfiguration.loadConfiguration(file);
   }

   public static FileConfiguration getFile() {
      return prisonersFile;
   }

   public static void saveFile() {
      try {
         prisonersFile.save(file);
      } catch (IOException exception) {
         Bukkit.getLogger().info("Unable to save prisoners.yml");
      }
   }

   public static void reloadFile() {
      prisonersFile = YamlConfiguration.loadConfiguration(file);
   }

}
