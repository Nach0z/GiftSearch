package net.helix.bennis.util.skins;

import net.helix.bennis.GiftSearchPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

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

    public static SkinPair getRandomFromGroup(String groupName) {
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Getting skins for group " + groupName);
        Set<SkinPair> skinSet = pluginSkins.get(groupName);
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Got skin count: " + skinSet.size());
        int rand = new Random().nextInt(skinSet.size());
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Got rand int: " + rand);
        SkinPair foundPair = (SkinPair) skinSet.toArray()[rand];
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Selected skinpair: " + foundPair.getSkinName());
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Opened: " + foundPair.getOpened());
        GiftSearchPlugin.getPlugin().getLogger().log(Level.SEVERE, "Closed: " + foundPair.getClosed());
        return (SkinPair) skinSet.toArray()[rand];
    }

    public static SkinPair getNamedFromGroup(String groupName, String presentName) {
        Set<SkinPair> matchedSkins = pluginSkins.get(groupName).stream().filter(x -> x.getSkinName() == presentName).collect(Collectors.toCollection(HashSet::new));
        // we only need one, and duplicate skin names would be Bad.
        return (SkinPair) matchedSkins.toArray()[0];
    }

    public static Set<String> getAllGroups() {
        return pluginSkins.keySet();
    }
}
