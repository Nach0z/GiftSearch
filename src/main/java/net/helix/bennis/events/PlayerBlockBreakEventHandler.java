package net.helix.bennis.events;
import net.helix.bennis.util.BlockLocationMemCache;
import net.helix.bennis.util.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

import static net.helix.bennis.util.Constants.ADMIN_PERMISSION;
import static net.helix.bennis.util.Constants.METADATA_IS_GIFTBLOCK;

public class PlayerBlockBreakEventHandler implements Listener {

    // once again we want to return from this ASAP and do the fewest operations & checks possible.
    @EventHandler
    public void onEvent(BlockBreakEvent event) {
        // Is it a player head? If not, return out.
        if(event.getBlock().getType() != Material.PLAYER_HEAD && event.getBlock().getType() != Material.PLAYER_WALL_HEAD) return;
        // Since the above check will handle 99.99% of all block breaks that we don't care about, we can be more relaxed moving forward.
        // TODO check block for GiftSearch metadata and cancel the event.

        if(event.getBlock().getMetadata(METADATA_IS_GIFTBLOCK).stream().anyMatch(x -> x.asBoolean()))
        {
            if(event.getPlayer().hasPermission(ADMIN_PERMISSION)) {
                // we don't want to interrupt admin breaking blocks actually
                BlockLocationMemCache.removeBlock(event.getBlock().getLocation());
                // returning without cancelling will let the block break normally. We still need to remove it from the
                // block memcache though.
                return;
            } else {
                event.setCancelled(true); // No player should be able to remove this block. Admin should use the gift tool.
                event.getPlayer().sendMessage(Messages.BLOCK_BREAK_CANCEL);
            }
        }
    }
}
