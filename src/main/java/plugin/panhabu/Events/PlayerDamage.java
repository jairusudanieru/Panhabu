package plugin.panhabu.Events;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import plugin.panhabu.Configuration;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        World spawnWorld = Configuration.getLocation("locations.spawnLocation").getWorld();
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        if (player.getWorld() == spawnWorld) {
            event.setCancelled(true);
        }
    }

}
