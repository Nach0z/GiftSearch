package net.helix.bennis.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.skins.SkinManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import static net.helix.bennis.util.Constants.*;

public class PlayerClickEventHandler implements Listener {
    // Just as in the AdminClickEventHandler we should do as much filtering as fast as possible in the fewest operations
    // for optimization, as this hooks into *every* dig/attack/place event by every player on the server.

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        // we literally only care if the block interacted with is a player head that's been placed on the floor
        // or on the wall, technically. If it's not one of these it's not a giftbox so we can return fast.
        // if clickedBlock is null, return immediately because no block means no handle.
        if(event.getClickedBlock() == null || (event.getClickedBlock().getType() != Material.PLAYER_HEAD
                && event.getClickedBlock().getType() != Material.PLAYER_WALL_HEAD)) {
            return;
        }
        // TODO remove this
        Player p = event.getPlayer();
        p.sendMessage("Player event - block data: ", event.getClickedBlock().getBlockData().toString());

        // if it's just a regular-ass player head it shouldn't have data and we shouldn't care.
        if(event.getClickedBlock().getBlockData() == null) return;
        // and finally if it's not a right-click we don't handle it.
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        // and now we can do less optimized operations because we're past the point where we risk running code once per
        // tick for every player.

        String groupName = "christmas";
        String skin = SkinManager.getRandomSkinNameFromGroup(groupName);
        Block headBlock = event.getClickedBlock();
        MetadataValue mv = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), true);
        MetadataValue mv2 = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), groupName);
        MetadataValue mv3 = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), skin);
        headBlock.setMetadata(METADATA_IS_GIFTBLOCK, mv);
        headBlock.setMetadata(METADATA_GIFTBLOCK_GROUP, mv2);
        headBlock.setMetadata(METADATA_GIFTBLOCK_SKIN_NAME, mv3);
//        event.getPlayer().sendMessage("placed block metadata set");

        Skull headSkull = (Skull) headBlock.getState(); //.getWorld().getBlockAt(headBlock.getLocation()).getState();
//        headBlock.setType(Material.PLAYER_HEAD);
        PlayerProfile skinProfile = SkinManager.getNewOpenedProfile(groupName, skin);
        headSkull.setPlayerProfile(skinProfile);
        headSkull.update();



    }
}
