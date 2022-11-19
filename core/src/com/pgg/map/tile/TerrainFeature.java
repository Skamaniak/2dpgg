package com.pgg.map.tile;

import com.pgg.generation.landscape.Biome;

import java.util.EnumSet;
import java.util.Set;

public enum TerrainFeature {

    DIRT("terrain/tileset-dirt.png", 1f, EnumSet.of(Biome.BEACH, Biome.MEADOW)),
    FLOWERS("terrain/tileset-flowers.png", 1.1f, EnumSet.of(Biome.MEADOW)),
    FORREST("terrain/tileset-forrest.png",0.5f, EnumSet.of(Biome.MEADOW, Biome.GRASS)),
    ISLAND("terrain/tileset-island.png",1.2f, EnumSet.of(Biome.WATER)),
    MOUNTAIN("terrain/tileset-mountain.png",0.5f, EnumSet.of(Biome.GRASS, Biome.HILLS)),
    PEBBLES("terrain/tileset-pebbles.png", 0.9f, EnumSet.of(Biome.GRASS, Biome.HILLS)),
    ROCK("terrain/tileset-rock.png",0.4f, EnumSet.of(Biome.HILLS, Biome.HIGH_HILLS, Biome.SNOW));

    private final Set<Biome> compatibleBiomes;
    private final TessellatedTileSet<TerrainFeatureTile> tileSet;

    TerrainFeature(String tilesetAsset, float speedModifier, Set<Biome> compatibleBiomes) {
        this.compatibleBiomes = compatibleBiomes;
        this.tileSet = new TessellatedTileSet<>(tilesetAsset, t -> new TerrainFeatureTile(t, speedModifier));
    }

    public TerrainFeatureTile getTile(int surroundings) {
        return tileSet.getTile(surroundings);
    }

    public boolean isCompatible(Biome biome) {
        return compatibleBiomes.contains(biome);
    }
}
