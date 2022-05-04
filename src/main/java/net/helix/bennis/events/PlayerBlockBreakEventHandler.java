package net.helix.bennis.events;
import net.helix.bennis.util.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
public class PlayerBlockBreakEventHandler implements Listener {

    // once again we want to return from this ASAP and do the fewest operations & checks possible.
    @EventHandler
    public void onEvent(BlockBreakEvent event) {
        // Is it a player head? If not, return out.
        if(event.getBlock().getType() != Material.PLAYER_HEAD && event.getBlock().getType() != Material.PLAYER_WALL_HEAD) return;
        // Since the above check will handle 99.99% of all block breaks that we don't care about, we can be more relaxed moving forward.
        // TODO check block for GiftSearch metadata and cancel the event.
        if(event.getBlock().getMetadata("isGiftBlock").stream().anyMatch(x -> x.asBoolean()))
        {
            event.setCancelled(true); // No player should be able to remove this block. Admin should use the gift tool.
            event.getPlayer().sendMessage(Messages.BLOCK_BREAK_CANCEL);
        } else {
            event.getPlayer().sendMessage(Integer.toString(event.getBlock().getLocation().getBlockX()),
                    Integer.toString(event.getBlock().getLocation().getBlockY()),
                    Integer.toString(event.getBlock().getLocation().getBlockZ()));
        }
    }
}
