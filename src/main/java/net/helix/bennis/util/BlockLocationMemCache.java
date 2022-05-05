package net.helix.bennis.util;

import com.google.common.collect.Streams;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.javatuples.Pair;

import org.bukkit.Location;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockLocationMemCache {
    private static Map<Location, Block> allGifts = new HashMap<Location, Block>();
    private static Map<Pair<Integer, Integer>, Set<Location>> giftChunks = new HashMap<Pair<Integer, Integer>, Set<Location>>();
    private static int renderRadius = 0;// = GiftSearchPlugin.getPlugin().getServer().getViewDistance();
    public static void setRenderRadius(int radius) {
        renderRadius = radius;
    }
    public static void addBlock(Location location, Block block) {
        allGifts.put(location, block);
        Pair<Integer, Integer> blockChunkLocation = new Pair<>(location.getChunk().getX(), location.getChunk().getZ());

        if(!giftChunks.containsKey(blockChunkLocation))
            giftChunks.put(blockChunkLocation, new HashSet<Location>());
        giftChunks.get(blockChunkLocation).add(location);
    }
    public static void addBlocks(Map<Location, Block> blocks) {
        allGifts = Stream.concat(allGifts.entrySet().stream(), blocks.entrySet().stream()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
        );
    }

    public static void removeBlock(Location location) {
        allGifts.remove(location);
    }

    public static Set<Block> getBlocksWithinChunkRadius(Chunk playerChunk) {
        return getBlocksWithinChunkRadius(playerChunk, renderRadius); // default to the server render distance
    }

    private static boolean isContained(Map.Entry<Pair<Integer, Integer>, Set<Location>> target, int minX, int maxX, int minZ, int maxZ) {
        int targetX = target.getKey().getValue0();
        int targetZ = target.getKey().getValue1();
        return (minX < targetX && targetX < maxX) && (minZ < targetZ && targetZ < maxZ);
    }
    public static Set<Block> getBlocksWithinChunkRadius(Chunk playerChunk, int chunkRadius)
    {
        int targetXMin = playerChunk.getX() - chunkRadius;
        int targetXMax = playerChunk.getX() + chunkRadius;
        int targetZMin = playerChunk.getZ() - chunkRadius;
        int targetZMax = playerChunk.getZ() + chunkRadius;
        // FYI this is a square radius distance, not a cylinder or sphere.
        // return all the Blocks that are somewhere inside the square radius we care about
        Stream<Location> allLocations = new HashSet<Location>().stream();
        Set<Set<Location>> locationsNested = giftChunks.entrySet().stream().filter(x -> isContained(x, targetXMin, targetXMax, targetZMin, targetZMax))
                .map(x -> x.getValue()).collect(Collectors.toCollection(HashSet::new));
        for(Set<Location> subSet : locationsNested) {
            allLocations = Streams.concat(allLocations, subSet.stream());
        }
        return (Set<Block>) allLocations.map(x -> allGifts.get(x)).collect(Collectors.toCollection(HashSet::new));
    }
}
