package net.helix.bennis.events;

import net.helix.bennis.util.BlockLocationMemCache;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class PlayerMoveEventHandler implements Listener {


    // for optimization we're only going to fire the event when the player crosses into a new chunk.
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().equals(event.getTo().getChunk())) return;
        event.getPlayer().sendMessage(String.format("Moved from chunk %s to %s", event.getFrom().getChunk().toString(), event.getTo().getChunk().toString()));
        Set<Block> nearbyGifts = BlockLocationMemCache.getBlocksWithinChunkRadius(event.getTo().getChunk(), 8);
        nearbyGifts.forEach(x -> event.getPlayer().sendMessage("Found gift at " + x.getLocation().toString()));
    }


}
