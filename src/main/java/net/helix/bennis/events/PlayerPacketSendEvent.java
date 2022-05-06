package net.helix.bennis.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class PlayerPacketSendEvent extends PacketAdapter {

    public PlayerPacketSendEvent(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType) {
        super(plugin, listenerPriority, packetType);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPlayer().sendMessage("Tile entity updated: " + event.getPacket().toString());
//        super.onPacketSending(event);
    }
}
