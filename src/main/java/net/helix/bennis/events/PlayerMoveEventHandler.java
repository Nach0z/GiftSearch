package net.helix.bennis.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.BlockLocationMemCache;
import net.helix.bennis.util.skins.SkinManager;
import net.helix.bennis.util.tags.BooleanTagType;
import net.helix.bennis.util.tags.StringTagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;


import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.helix.bennis.util.Constants.*;

public class PlayerMoveEventHandler implements Listener {


    // for optimization we're only going to fire the event when the player crosses into a new chunk.
    @EventHandler
    public void onEvent(PlayerMoveEvent event) throws InvocationTargetException {
        if(event.getFrom().getChunk().equals(event.getTo().getChunk())) return;
        event.getPlayer().sendMessage(String.format("Moved from chunk %s to %s", event.getFrom().getChunk().toString(), event.getTo().getChunk().toString()));
        Set<Block> nearbyGifts = BlockLocationMemCache.getBlocksWithinChunkRadius(event.getTo().getChunk(), 8);
        //nearbyGifts.forEach(x -> event.getPlayer().sendMessage("Found gift at " + x.getLocation().toString()));
        for(Block nearbyBlock : nearbyGifts) {
            if(nearbyBlock == null) continue;
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
//
//            PlayerProfile openedProfile = SkinManager.getNewOpenedProfile(groupName, skinName);
//            Skull headSkull = (Skull) presentBlock.getState(); //.getWorld().getBlockAt(headBlock.getLocation()).getState();
//            presentBlock.setType(Material.PLAYER_HEAD);
//            headSkull.setPlayerProfile(openedProfile);
//            headSkull.update();
            ItemStack toolItem = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta toolMeta = (SkullMeta)toolItem.getItemMeta();
//            PersistentDataContainer container = toolMeta.getPersistentDataContainer();
//            container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_IS_GIFTBLOCK), new BooleanTagType(), true);
//            container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_GIFTBLOCK_GROUP), new StringTagType(), groupName);
//            container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_GIFTBLOCK_SKIN_NAME), new StringTagType(), skinName);
            PlayerProfile textureProfile = SkinManager.getNewOpenedProfile("christmas", "gold");
            toolMeta.setPlayerProfile(textureProfile);
            toolItem.setItemMeta(toolMeta);



            NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(MinecraftReflection.getBukkitItemStack(toolItem)));
            PacketContainer skinContainer = new PacketContainer(PacketType.Play.Server.TILE_ENTITY_DATA);
            skinContainer.getNbtModifier().write(0, nbt);
            skinContainer.getBlockPositionModifier().write(0, new BlockPosition(nearbyBlock.getLocation().toVector()));
//            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), blockContainer, true);
            ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), skinContainer, true);
            event.getPlayer().sendMessage("Server packet sent");
//            presentBlock.getState().update();
        }
    }
}
