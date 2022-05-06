package net.helix.bennis.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.BlockLocationMemCache;
import net.helix.bennis.util.Constants;
import net.helix.bennis.util.skins.SkinManager;
import net.helix.bennis.util.skins.SkinPair;
import net.helix.bennis.util.skins.SkinURL;
import net.helix.bennis.util.tags.StringTagType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;

import static net.helix.bennis.util.Constants.METADATA_GIFTBLOCK_GROUP;
import static net.helix.bennis.util.Constants.METADATA_IS_GIFTBLOCK;

public class AdminBlockPlaceEventHandler implements Listener {
    // first check should be the block type, second should be whether the player has admin perms. After that check to make sure the main hand is holding the tool.
    @EventHandler
    public void onEvent(BlockPlaceEvent event) throws MalformedURLException {
        if(event.getBlockPlaced().getType() != Material.PLAYER_HEAD && event.getBlockPlaced().getType() != Material.PLAYER_WALL_HEAD) return;
        if(!event.getPlayer().hasPermission(Constants.ADMIN_PERMISSION)) return;
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PLAYER_HEAD) return;
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
        if(!handItem.hasItemMeta()) return;
        if(!handItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_IS_GIFTBLOCK)))
            return;
        String groupName = SkinManager.getRandomGroupName();
//        event.getPlayer().sendMessage("Selected random group: " + groupName);
        if(handItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_GIFTBLOCK_GROUP)))
        {
            StringTagType groupStringTag = new StringTagType();
            groupName = handItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_GIFTBLOCK_GROUP), groupStringTag);
//            event.getPlayer().sendMessage("Overrode default group using tool item group: " + groupName);
        }
        Block headBlock = event.getBlockPlaced();
        MetadataValue mv = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), true);
        MetadataValue mv2 = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), groupName);
        headBlock.setMetadata(METADATA_IS_GIFTBLOCK, mv);
        headBlock.setMetadata(METADATA_GIFTBLOCK_GROUP, mv2);
//        event.getPlayer().sendMessage("placed block metadata set");

        Skull headSkull = (Skull) headBlock.getState(); //.getWorld().getBlockAt(headBlock.getLocation()).getState();
        headBlock.setType(Material.PLAYER_HEAD);
        String skin = SkinManager.getRandomSkinNameFromGroup(groupName);
        PlayerProfile skinProfile = SkinManager.getNewClosedProfile(groupName, skin);
        headSkull.setPlayerProfile(skinProfile);
        headSkull.update();
//        player.sendMessage("Update success: " + headSkull.update(false));
//        event.getPlayer().sendMessage(blockSkull.getMetadata("SkullOwner").toString());
        BlockLocationMemCache.addBlock(event.getBlockPlaced().getLocation(), event.getBlockPlaced());
//        event.getPlayer().sendMessage("Added block to cache");
    }
}
