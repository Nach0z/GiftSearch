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
import net.helix.bennis.util.tags.BooleanTagType;
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

@CommandAlias("giftsearch")
public class GiftSearchCommands extends BaseCommand     {
    static MessageKey key(String key) {
        return MessageKey.of(ACF_BASE_KEY + "." + key);
    }

    @Subcommand("tool")
    @CommandPermission(ADMIN_PERMISSION)
    public void tool(@Flags("self") Player player) {
        if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
            player.sendMessage("Can't give item - command must be used with an empty main hand.");
            return;
        }
        ItemStack toolItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta toolMeta = (SkullMeta)toolItem.getItemMeta();
        PersistentDataContainer container = toolMeta.getPersistentDataContainer();
        container.set(new NamespacedKey(GiftSearchPlugin.getPlugin(), "giftBlock"), new BooleanTagType(), true);
        PlayerProfile textureProfile = GiftSearchPlugin.getPlugin().getServer().createProfile(UUID.fromString("d998969d-fa16-4612-a85d-bebb39171447"));

        // TODO make a SkinManager class and use getPlugin().getConfig() to pull out "present.closed" and "present.open" skin values
        ProfileProperty textureProperty = new ProfileProperty("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNlZDg3OTlkMDdiOGUyZGEyNTU3YzNmMDU5OGZkYWVkOTQ0Mzc2ODI2ZjFkMmNlYjY3MGZkNjUxYjJjZDE2NiJ9fX0=");
        textureProfile.setProperty(textureProperty);
        toolMeta.setPlayerProfile(textureProfile);
        toolItem.setItemMeta(toolMeta);
        player.getInventory().setItemInMainHand(toolItem);
    }

}
