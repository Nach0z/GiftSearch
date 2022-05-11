package net.helix.bennis.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class UnnamedPacketSendEvent extends PacketAdapter {
    public UnnamedPacketSendEvent(Plugin plugin, PacketType... types) {
        super(plugin, types);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
//        event.getPlayer().sendMessage("§a------------------------------------- BEGIN BLOCK CHANGE ---------------------------");
//        event.getPlayer().sendMessage(
//                "§aBlock Change modifiers: ",
//                event.getPacket().getModifier().toString(),
//                event.getPacket().getModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getModifier().getFields()) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getModifier().getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//        event.getPlayer().sendMessage("§aEntity info:",
//                event.getPacket().getEntityModifier(event.getPlayer().getWorld()).toString(),
//                event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getFields().toArray().length + "");
//        for(Field field : event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getFields()){
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//
//        event.getPlayer().sendMessage("§aEntity Type info:",
//                event.getPacket().getEntityTypeModifier().toString(),
//                event.getPacket().getEntityTypeModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getEntityTypeModifier().getFields()) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getEntityTypeModifier().getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//        event.getPlayer().sendMessage("§a------------------------------------- END BLOCK CHANGE ---------------------------");
    }
}
