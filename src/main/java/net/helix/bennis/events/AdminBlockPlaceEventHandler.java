package net.helix.bennis.events;

import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.Constants;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Set;

public class AdminBlockPlaceEventHandler implements Listener {
    // first check should be the block type, second should be whether the player has admin perms. After that check to make sure the main hand is holding the tool.
    @EventHandler
    public void onEvent(BlockPlaceEvent event) {
//        event.getPlayer().sendMessage("placing block");
        if(event.getBlockPlaced().getType() != Material.PLAYER_HEAD && event.getBlockPlaced().getType() != Material.PLAYER_WALL_HEAD) return;
//        event.getPlayer().sendMessage("bp1");
        if(!event.getPlayer().hasPermission(Constants.ADMIN_PERMISSION)) return;
//        event.getPlayer().sendMessage("bp2");
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PLAYER_HEAD) return;
//        event.getPlayer().sendMessage("bp3");
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
//        event.getPlayer().sendMessage(handItem.getType().toString());
//        event.getPlayer().sendMessage("bp4");
        if(!handItem.hasItemMeta()) return;
//        event.getPlayer().sendMessage("bp5");
//        Set<NamespacedKey> keys = handItem.getItemMeta().getPersistentDataContainer().getKeys();
//        for(NamespacedKey key : keys) {
//            event.getPlayer().sendMessage("existing key: ", key.getNamespace());
//        }
//        event.getPlayer().sendMessage("new key: ", new NamespacedKey(GiftSearchPlugin.getPlugin(), "giftBlock").getNamespace());
        if(!handItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(GiftSearchPlugin.getPlugin(), "giftBlock")))
            return;
//        event.getPlayer().sendMessage("bp6");
        MetadataValue mv = new FixedMetadataValue(GiftSearchPlugin.getPlugin(), true);
        event.getBlockPlaced().setMetadata("isGiftBlock", mv);
//        event.getPlayer().sendMessage("placed block");
    }
}
