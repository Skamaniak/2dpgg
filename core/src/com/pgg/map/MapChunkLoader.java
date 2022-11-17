package com.pgg.map;

import com.pgg.generation.TileGenerator;
import com.pgg.map.tile.Tile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapChunkLoader {
    private final int loadSurroundings;
    private final int loadDistance;
    private final Map<String, MapChunk> loadedChunks = new HashMap<>();
    private final TileGenerator tileGenerator;

    public MapChunkLoader(TileGenerator generator, int loadSurroundings, int loadDistance) {
        this.tileGenerator = generator;

        this.loadSurroundings = loadSurroundings;
        this.loadDistance = loadDistance;
    }

    public Collection<MapChunk> getChunks(int x, int y) {
        int chunkX = getChunkCoordinate(x);
        int chunkY = getChunkCoordinate(y);

        unloadUnnecessaryChunks(chunkX, chunkY);
        ensureSurroundingChunksLoaded(chunkX, chunkY);

        return loadedChunks.values();
    }

    public MapChunk getChunkAt(int x, int y) {
        int chunkX = getChunkCoordinate(x);
        int chunkY = getChunkCoordinate(y);

        return loadedChunks.get(chunkId(chunkX, chunkY));
    }

    private void ensureSurroundingChunksLoaded(int chunkX, int chunkY) {
        String chunkId;
        for (int x = chunkX - loadSurroundings; x < chunkX + loadSurroundings + 1; x++) {
            for (int y = chunkY - loadSurroundings; y < chunkY + loadSurroundings + 1; y++) {
                chunkId = chunkId(x, y);
                if (!loadedChunks.containsKey(chunkId)) {
                    loadedChunks.put(chunkId, new MapChunk(tileGenerator, x, y));
                }
            }
        }
    }

    private void unloadUnnecessaryChunks(int chunkX, int chunkY) {
        int xMin = chunkX - loadDistance;
        int xMax = chunkX + loadDistance;

        int yMin = chunkY - loadDistance;
        int yMax = chunkY + loadDistance;

        loadedChunks.entrySet().removeIf(entry -> {
            MapChunk chunk = entry.getValue();
            if (chunk.tainted) {
                return false;
            }
            return chunk.chunkX < xMin
                    || chunk.chunkX  > xMax
                    || chunk.chunkY  < yMin
                    || chunk.chunkY  > yMax;
        });
    }

    private String chunkId(int chunkX, int chunkY) {
        return chunkX + "," + chunkY;
    }

    private int getChunkCoordinate(float playerCoordinate) {
        return (int) Math.floor(playerCoordinate / (MapChunk.CHUNK_SIZE * Tile.TILE_SIZE));
    }
}
