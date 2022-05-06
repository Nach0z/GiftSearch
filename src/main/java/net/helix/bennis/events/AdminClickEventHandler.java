package net.helix.bennis.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.*;

public class AdminClickEventHandler implements Listener {
    // event handlers should exit as soon as possible.
    // Checks are handled in the order most likely to quickly exit while performing the fewest operations.
    // i.e. even though the most common kinds of events are usually digging & killing things (left-click events),
    // we don't check for whether it's a right-click action first because all building operations are a right-click-block
    // event. So the first thing we check is, are they using a player head. That should fail nearly always except in
    // very limited situations where someone is doing decor, and they're not going to place thousands of these quickly
    // in that situation.
    @EventHandler
    public void onEvent(PlayerInteractEvent event){
        // if it's not a player head we don't care about it. Return.
        if(event.getItem() == null) return;
        if(event.getItem().getType() != Material.PLAYER_HEAD) return;
        // if they aren't right-clicking a block, return.
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(!event.getItem().hasItemMeta()) return;

        ItemStack playerHead = event.getItem();
        // At this point, once we get here, we're pretty sure that we're actually talking about the GiftSearch admin
        // functions and we've done great filtering so we don't have to be so performance-optimized anymore.

        return;
        // this is the first variable assignment because we don't want to cause a memory leak and we don't want to
        // force GC to work harder with more random floating memory references.
//        Player p = event.getPlayer();
//        if(playerHead.hasItemMeta())
//            p.sendMessage("Admin event - Item data: ", playerHead.getItemMeta().toString());

    }

}
