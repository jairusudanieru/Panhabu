package plugin.panhabu.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import plugin.panhabu.PrisonFunctions.Prisoners;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        boolean isPrisoner = Prisoners.isPlayerInJail(player);
        boolean inPrisonCell = Prisoners.inPrisonCell(player);
        if (!isPrisoner && !inPrisonCell) return;
        event.setCancelled(true);
    }

}
