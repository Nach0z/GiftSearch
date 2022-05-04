package net.helix.bennis;
import co.aikar.commands.PaperCommandManager;
import kr.entree.spigradle.annotations.PluginMain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.helix.bennis.commands.GiftSearchCommands;
import net.helix.bennis.events.AdminClickEventHandler;
import net.helix.bennis.events.PlayerClickEventHandler;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
// import net.silthus.template.integrations.vault.VaultProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

@PluginMain
public class GiftSearchPlugin  extends JavaPlugin implements Listener {
    private static GiftSearchPlugin instance;
    private PaperCommandManager commandManager;

    public static GiftSearchPlugin getPlugin() {
        return instance;
    }

    public GiftSearchPlugin (){
        instance = this;
    }

    public GiftSearchPlugin(
            JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        instance = this;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AdminClickEventHandler.onEvent(event);
        PlayerClickEventHandler.onEvent(event);
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.SEVERE, "entered onEnable");
        saveDefaultConfig();
        getLogger().log(Level.SEVERE, "savedDefaultConfig");
        setupCommands();
        getLogger().log(Level.SEVERE, "setupCommands");

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().log(Level.SEVERE, "registeredEvents");
    }

    private void setupCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("help");

        loadCommandLocales(commandManager);
        getLogger().log(Level.SEVERE, "registering...");
        commandManager.registerCommand(new GiftSearchCommands());
        getLogger().log(Level.SEVERE, "... registered");
    }

    private void loadCommandLocales(PaperCommandManager commandManager) {
        try {
            saveResource("lang_en.yaml", true);
            commandManager.getLocales().setDefaultLocale(Locale.ENGLISH);
            commandManager.getLocales().loadYamlLanguageFile("lang_en.yaml", Locale.ENGLISH);
            // this will detect the client locale and use it where possible
            commandManager.usePerIssuerLocale(true);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe("Failed to load language config 'lang_en.yaml': " + e.getMessage());
            e.printStackTrace();
        }
    }
}