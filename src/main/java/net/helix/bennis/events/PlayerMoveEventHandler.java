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

import static net.helix.bennis.util.Constants.METADATA_GIFTBLOCK_GROUP;
import static net.helix.bennis.util.Constants.METADATA_IS_GIFTBLOCK;

public class PlayerMoveEventHandler implements Listener {


    // for optimization we're only going to fire the event when the player crosses into a new chunk.
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().equals(event.getTo().getChunk())) return;
        event.getPlayer().sendMessage(String.format("Moved from chunk %s to %s", event.getFrom().getChunk().toString(), event.getTo().getChunk().toString()));
        Set<Block> nearbyGifts = BlockLocationMemCache.getBlocksWithinChunkRadius(event.getTo().getChunk(), 8);
        //nearbyGifts.forEach(x -> event.getPlayer().sendMessage("Found gift at " + x.getLocation().toString()));
        String groupName = SkinManager.getRandomGroupName();
        for(Block presentBlock : nearbyGifts) {
            List<MetadataValue> metadatas = presentBlock.getMetadata(METADATA_GIFTBLOCK_GROUP);
            var giftblockGroup = metadatas.stream().filter(x -> x.getOwningPlugin().equals(GiftSearchPlugin.getPlugin())).findFirst();
            if(giftblockGroup.isPresent())
                groupName = giftblockGroup.get().asString();
            /*
            Skull headSkull = (Skull) presentBlock.getState(); //.getWorld().getBlockAt(headBlock.getLocation()).getState();
            presentBlock.setType(Material.PLAYER_HEAD);
            String skin = SkinManager.getRandomSkinNameFromGroup(groupName);
            PlayerProfile skinProfile = SkinManager.getNewOpenedProfile(groupName, skin);
            headSkull.setPlayerProfile(skinProfile);
            headSkull.update();
             */

            event.getPlayer().sendMessage(presentBlock.getMetadata(METADATA_IS_GIFTBLOCK).toString());
            event.getPlayer().sendMessage(presentBlock.getMetadata(METADATA_GIFTBLOCK_GROUP).toString());
            event.getPlayer().sendMessage(presentBlock.getWorld().getBlockAt(presentBlock.getLocation()).getMetadata(METADATA_IS_GIFTBLOCK).toString());
            event.getPlayer().sendMessage(presentBlock.getWorld().getBlockAt(presentBlock.getLocation()).getMetadata(METADATA_GIFTBLOCK_GROUP).toString());
        }
    }
}
