package net.helix.bennis.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class TileEntityPacketSendEvent extends PacketAdapter {

    public TileEntityPacketSendEvent(Plugin plugin, ListenerPriority listenerPriority, PacketType packetType) {
        super(plugin, listenerPriority, packetType);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
//        event.getPlayer().sendMessage("Tile entity updated: " + event.getPacket().toString());
//        event.getPlayer().sendMessage(event.getPacket().getClass().getName()); // so far we know it's a PacketContainer

//        // SO FAR:
//        // 1 field
//        // StructureModifier
//        // field "c" is a NBTTagCompound
//        // Contains the skullOwner field? but in a fucking weird way.
//        event.getPlayer().sendMessage("§aNbtModifier info:");
//        event.getPlayer().sendMessage(event.getPacket().getNbtModifier().toString());
//        event.getPlayer().sendMessage(event.getPacket().getNbtModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getNbtModifier().getFields() ) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getNbtModifier().getValues()) {
//            event.getPlayer().sendMessage(value == null ? "" : value.toString());
//        }
//
//
//        // SO FAR:
//        // 3 fields
//        // Structure Modifier
//        // field "a" is a BlockPosition
//        // field "b" is a TileEntityTypes
//        // field "c" is a NBTTagCompound
//        // "a" has the coords
//        // "b" is a handle for the tile entity type?
//        // "c" is a handle for the SkulLowner.
//        // "c" is probably what we need to be second - so we need to modify the Structures array?
//        event.getPlayer().sendMessage("§aStructures info:");
//        event.getPlayer().sendMessage(event.getPacket().getStructures().toString());
//        event.getPlayer().sendMessage(event.getPacket().getStructures().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getStructures().getFields() ) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getStructures().getValues()) {
//            event.getPlayer().sendMessage(value.toString());
//        }
//
//        event.getPlayer().sendMessage("§aBlockPosition info:",
//                event.getPacket().getBlockPositionModifier().toString(),
//                event.getPacket().getBlockPositionModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getBlockPositionModifier().getFields()) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getBlockPositionModifier().getValues()) {
//            event.getPlayer().sendMessage(value.toString());
//        }
//        event.getPlayer().sendMessage("§a------------------------------------- BEGIN TILE ENTITY UPDATE ---------------------------");
//        event.getPlayer().sendMessage("§aModifier info:",
//            event.getPacket().getModifier().toString(),
//            event.getPacket().getModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getModifier().getFields()) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getModifier().getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//        event.getPlayer().sendMessage("§aEntity info:",
//            event.getPacket().getEntityModifier(event.getPlayer().getWorld()).toString(),
//            event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getFields().toArray().length + "");
//        for(Field field : event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getFields()){
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getEntityModifier(event.getPlayer().getWorld()).getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//
//        event.getPlayer().sendMessage("§aEntity Type info:",
//            event.getPacket().getEntityTypeModifier().toString(),
//            event.getPacket().getEntityTypeModifier().getFields().toArray().length + "");
//        for(Field field : event.getPacket().getEntityTypeModifier().getFields()) {
//            event.getPlayer().sendMessage(field.toString());
//        }
//        for(var value : event.getPacket().getEntityTypeModifier().getValues()) {
//            event.getPlayer().sendMessage(value == null ? "NULL VALUE" : value.toString());
//        }
//        FURNACE
//        event.getPlayer().sendMessage("§a------------------------------------- END TILE ENTITY UPDATE ---------------------------");

    }
}
