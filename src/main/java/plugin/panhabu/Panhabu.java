package plugin.panhabu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.panhabu.Commands.*;
import plugin.panhabu.Events.*;
import plugin.panhabu.PluginFunctions.Configuration;
import plugin.panhabu.PluginFunctions.Prisoners;
import plugin.panhabu.PluginFunctions.PrisonersFile;

import java.util.Objects;

public final class Panhabu extends JavaPlugin {

    public Configuration panhabuConfiguration;
    public Prisoners panhabuPrisoners;
    public PrisonersFile panhabuPrisonersFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();
        saveDefaultConfig();
        PrisonersFile.checkFile();
        PrisonersFile.getFile().options().copyDefaults(true);
        PrisonersFile.saveFile();
        Prisoners.checkCooldown();
        Prisoners.checkBossBar();
        Bukkit.getLogger().info("[Panhabu] Plugin Enabled Successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("[Panhabu] Plugin Disabled Successfully!");
    }

    public void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("panhabu")).setExecutor(new Main());
        Objects.requireNonNull(Bukkit.getPluginCommand("panhabu")).setTabCompleter(new Main());
        Objects.requireNonNull(Bukkit.getPluginCommand("prison")).setExecutor(new Prison());
        Objects.requireNonNull(Bukkit.getPluginCommand("prison")).setTabCompleter(new Prison());
        Objects.requireNonNull(Bukkit.getPluginCommand("release")).setExecutor(new Release());
        Objects.requireNonNull(Bukkit.getPluginCommand("release")).setTabCompleter(new Release());
        Objects.requireNonNull(Bukkit.getPluginCommand("setprison")).setExecutor(new SetPrison());
        Objects.requireNonNull(Bukkit.getPluginCommand("setprison")).setTabCompleter(new SetPrison());
        Objects.requireNonNull(Bukkit.getPluginCommand("setauth")).setExecutor(new SetAuth());
        Objects.requireNonNull(Bukkit.getPluginCommand("setauth")).setTabCompleter(new SetAuth());
        Objects.requireNonNull(Bukkit.getPluginCommand("setspawn")).setExecutor(new SetSpawn());
        Objects.requireNonNull(Bukkit.getPluginCommand("setspawn")).setTabCompleter(new SetSpawn());
        Objects.requireNonNull(Bukkit.getPluginCommand("spawn")).setExecutor(new Spawn());
        Objects.requireNonNull(Bukkit.getPluginCommand("spawn")).setTabCompleter(new Spawn());
    }

    public void registerEvents() {
        Plugin authMePlugin = Bukkit.getPluginManager().getPlugin("AuthMe");
        if (authMePlugin != null) Bukkit.getPluginManager().registerEvents(new PlayerLogin(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommand(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(),this);
    }

}
