package net.helix.bennis.commands;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.MessageType;
import co.aikar.commands.annotation.*;
import co.aikar.locales.MessageKey;
import com.destroystokyo.paper.Namespaced;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.helix.bennis.GiftSearchPlugin;
import net.helix.bennis.util.skins.SkinManager;
import net.helix.bennis.util.tags.BooleanTagType;
import net.helix.bennis.util.tags.StringTagType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerTextures;

import java.util.UUID;

import static net.helix.bennis.util.Constants.*;
import static net.helix.bennis.util.Messages.*;

@CommandAlias("giftsearch")
public class GiftSearchCommands extends BaseCommand     {
    static MessageKey key(String key) {
        return MessageKey.of(ACF_BASE_KEY + "." + key);
    }

    @Subcommand("tool")
    @CommandPermission(ADMIN_PERMISSION)
    public void tool(@Flags("self") Player player, String groupName) {
        if(groupName == null)
            groupName = SkinManager.getRandomGroupName();
        if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            player.sendMessage(MESSAGE_PREFIX + COLOR_PROBLEM + "Can't give item - command must be used with an empty main hand.");
            return;
        }
        ItemStack toolItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta toolMeta = (SkullMeta)toolItem.getItemMeta();
        PersistentDataContainer container = toolMeta.getPersistentDataContainer();
        container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_IS_GIFTBLOCK), new BooleanTagType(), true);
        container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), METADATA_GIFTBLOCK_GROUP), new StringTagType(), groupName);
        PlayerProfile textureProfile = GiftSearchPlugin.getPlugin().getServer().createProfile(UUID.randomUUID());

        // TODO make a SkinManager class and use getPlugin().getConfig() to pull out "present.closed" and "present.open" skin values
        // this is a static skin used for the tool for now.
        ProfileProperty textureProperty = new ProfileProperty("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNlZDg3OTlkMDdiOGUyZGEyNTU3YzNmMDU5OGZkYWVkOTQ0Mzc2ODI2ZjFkMmNlYjY3MGZkNjUxYjJjZDE2NiJ9fX0=");
        textureProfile.setProperty(textureProperty);
        toolMeta.setPlayerProfile(textureProfile);
        toolItem.setItemMeta(toolMeta);
        player.getInventory().setItemInMainHand(toolItem);
    }

    @Subcommand("groups")
    @CommandPermission(ADMIN_PERMISSION)
    public void groups(@Flags("self") Player player) {
        player.sendMessage(MESSAGE_PREFIX + "Available gift search groups:");
        SkinManager.getAllGroups().forEach(x -> player.sendMessage(LIST_ITEM_PREFIX + x));
    }

}
