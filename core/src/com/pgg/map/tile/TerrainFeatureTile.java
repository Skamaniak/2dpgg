package com.pgg.map.tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.generation.landscape.Biome;

import java.util.EnumSet;
import java.util.Set;

public enum TerrainFeatureTile implements Tile {
    DIRT(0, 1f, EnumSet.of(Biome.BEACH, Biome.MEADOW)),
    FORREST(1, 0.8f, EnumSet.of(Biome.MEADOW, Biome.GRASS)),
    ISLAND(2, 1.3f, EnumSet.of(Biome.WATER)),
    MOUNTAIN(3, 0.8f, EnumSet.of(Biome.GRASS, Biome.HILLS)),
    ROCK(4, 0.4f, EnumSet.of(Biome.HILLS, Biome.HIGH_HILLS, Biome.SNOW));

    private static final TextureRegion TERRAIN_FEATURES_TILESET = new TextureRegion(new Texture("tileset-terrain-features.png"));
    private static final TextureRegion[] TERRAIN_FEATURES = TERRAIN_FEATURES_TILESET.split(Tile.TILE_SIZE, Tile.TILE_SIZE)[0];

    private final int position;
    private final float speedModifier;
    private final Set<Biome> compatibleBiomes;

    TerrainFeatureTile(int position, float speedModifier, Set<Biome> compatibleBiomes) {
        this.position = position;
        this.speedModifier = speedModifier;
        this.compatibleBiomes = compatibleBiomes;
    }

    @Override
    public TextureRegion getTexture() {
        return TERRAIN_FEATURES[position];
    }

    @Override
    public float getSpeedModifier() {
        return speedModifier;
    }

    public boolean isCompatible(Biome biome) {
        return compatibleBiomes.contains(biome);
    }
}
