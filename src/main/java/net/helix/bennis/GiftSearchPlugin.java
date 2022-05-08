package net.helix.bennis;
import co.aikar.commands.PaperCommandManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import kr.entree.spigradle.annotations.PluginMain;
import net.helix.bennis.commands.GiftSearchCommands;
import net.helix.bennis.events.*;
import net.helix.bennis.util.BlockLocationMemCache;
import net.helix.bennis.util.skins.SkinManager;
// import net.silthus.template.integrations.vault.VaultProvider;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
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

    /* @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AdminClickEventHandler.onEvent(event);
        PlayerClickEventHandler.onEvent(event);
    }*/

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupCommands();

        registerEvents();
        BlockLocationMemCache.setRenderRadius(getServer().getViewDistance());
        SkinManager.init(getConfig());

        registerPacketListener();

    }

    private void registerPacketListener() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new TileEntityPacketSendEvent(this, ListenerPriority.NORMAL, PacketType.Play.Server.TILE_ENTITY_DATA));
        manager.addPacketListener(new UnnamedPacketSendEvent(this, PacketType.Play.Server.BLOCK_CHANGE ));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new AdminClickEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakEventHandler(), this);
        getServer().getPluginManager().registerEvents(new AdminBlockPlaceEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveEventHandler(), this);
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