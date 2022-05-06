package net.helix.bennis.util.skins;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.helix.bennis.GiftSearchPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static net.helix.bennis.util.Constants.*;
import java.util.logging.Level;
public class SkinManager {
    private static Map<String, Set<SkinPair>> pluginSkins = new HashMap<String, Set<SkinPair>>();
    public static void init(FileConfiguration config) {
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Starting presents init");
        ConfigurationSection presentSection = config.getConfigurationSection(CONFIG_KEY_PRESENTS);
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Got present section: " + (presentSection == null));
        for(String group : presentSection.getKeys(false)) {
            GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Pulling group: " + group);
            ConfigurationSection groupSection = presentSection.getConfigurationSection(group);
            GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Group section gotten.");
            Set<SkinPair> groupSkins = new HashSet<SkinPair>();
            for(String presentName : groupSection.getKeys(false)) {
                GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Getting present skins for " + presentName);
                groupSkins.add(new SkinPair(groupSection.getConfigurationSection(presentName).getString(CONFIG_KEY_CLOSED), groupSection.getConfigurationSection(presentName).getString(CONFIG_KEY_OPEN), presentName));
            }
            GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Adding presents to group.");
            pluginSkins.put(group, groupSkins);
        }

    }

    public static String getRandomGroupName() {
        return (String) pluginSkins.keySet().toArray()[new Random().nextInt(pluginSkins.size())];
    }

    public static SkinPair getRandomSkinPairFromGroup(String groupName) {
        Set<SkinPair> skinSet = pluginSkins.get(groupName);
        int rand = new Random().nextInt(skinSet.size());
        SkinPair foundPair = (SkinPair) skinSet.toArray()[rand];
        return (SkinPair) skinSet.toArray()[rand];
    }

    public static String getRandomSkinNameFromGroup(String groupName) {
        return getRandomSkinPairFromGroup(groupName).getSkinName();
    }

    public static SkinPair getNamedFromGroup(String groupName, String presentName) {
        Set<SkinPair> matchedSkins = pluginSkins.get(groupName).stream().filter(x -> x.getSkinName() == presentName).collect(Collectors.toCollection(HashSet::new));
        // we only need one, and duplicate skin names would be Bad.
        return (SkinPair) matchedSkins.toArray()[0];
    }

    public static Set<String> getAllGroups() {
        return pluginSkins.keySet();
    }

    public static PlayerProfile getNewClosedProfile(String groupName, String presentName) {
        return getNewPlayerProfile(groupName, presentName, true);
    }

    public static PlayerProfile getNewOpenedProfile(String groupName, String presentName) {
        return getNewPlayerProfile(groupName, presentName, false);
    }


    public static PlayerProfile getNewPlayerProfile(String groupName, String presentName, boolean isClosed) {
        PlayerProfile newProfile = Bukkit.createProfile(UUID.randomUUID());
        try {
            PlayerTextures textures = newProfile.getTextures();
            SkinPair namedPair = getNamedFromGroup(groupName, presentName);
            textures.setSkin(new URL(isClosed ? namedPair.getClosed() : namedPair.getOpened()));
            newProfile.setTextures(textures);
        } catch (MalformedURLException murlex) {
            GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Malformed playerhead URL: ");
        }
        return newProfile;
    }
}
