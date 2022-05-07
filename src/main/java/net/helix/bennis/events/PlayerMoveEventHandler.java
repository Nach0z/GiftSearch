package net.helix.bennis.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.BlockLocationMemCache;
import net.helix.bennis.util.skins.SkinManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.helix.bennis.util.Constants.*;

public class PlayerMoveEventHandler implements Listener {


    // for optimization we're only going to fire the event when the player crosses into a new chunk.
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().equals(event.getTo().getChunk())) return;
        event.getPlayer().sendMessage(String.format("Moved from chunk %s to %s", event.getFrom().getChunk().toString(), event.getTo().getChunk().toString()));
        Set<Block> nearbyGifts = BlockLocationMemCache.getBlocksWithinChunkRadius(event.getTo().getChunk(), 8);
        //nearbyGifts.forEach(x -> event.getPlayer().sendMessage("Found gift at " + x.getLocation().toString()));
        for(Block nearbyBlock : nearbyGifts) {
            Block presentBlock = nearbyBlock.getWorld().getBlockAt(nearbyBlock.getLocation());
            String groupName = null;
            String skinName = null;
            var isGiftblockMeta = presentBlock.getMetadata(METADATA_IS_GIFTBLOCK).stream()
                    .filter(x -> x.getOwningPlugin().equals(GiftSearchPlugin.getPlugin()))
                    .findFirst();
            if(!isGiftblockMeta.isPresent() || !isGiftblockMeta.get().asBoolean()) return; // no giftblock? no change.
            event.getPlayer().sendMessage("Found a giftblock");
            var giftGroupMeta = presentBlock.getMetadata(METADATA_GIFTBLOCK_GROUP).stream()
                    .filter(x -> x.getOwningPlugin().equals(GiftSearchPlugin.getPlugin()))
                    .findFirst();
            if(!giftGroupMeta.isPresent()) return; // if we don't have a group name we can't get the skin.
            else {
                groupName = giftGroupMeta.get().asString();
                event.getPlayer().sendMessage("Found a group");
            }
            var skinNameMeta = presentBlock.getMetadata(METADATA_GIFTBLOCK_SKIN_NAME).stream()
                    .filter(x -> x.getOwningPlugin().equals(GiftSearchPlugin.getPlugin()))
                    .findFirst();
            if(!skinNameMeta.isPresent()) return; // can't switch skins without a skin name either.
            else {
                skinName = skinNameMeta.get().asString();
                event.getPlayer().sendMessage("Found a skin name");
            }

            PlayerProfile openedProfile = SkinManager.getNewOpenedProfile(groupName, skinName);
            Skull headSkull = (Skull) presentBlock.getState(); //.getWorld().getBlockAt(headBlock.getLocation()).getState();
            presentBlock.setType(Material.PLAYER_HEAD);
            headSkull.setPlayerProfile(openedProfile);
            headSkull.update();
        }
    }
}
