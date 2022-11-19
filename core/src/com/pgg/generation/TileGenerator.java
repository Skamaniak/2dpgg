package com.pgg.generation;

import com.pgg.generation.landscape.Biome;
import com.pgg.generation.landscape.LandscapeGradient;
import com.pgg.map.tile.*;

import java.util.Random;

public class TileGenerator {
    private static final float SCALE = 0.02f;

    private final SimplexNoise_octave landscapeNoise;
    private final SimplexNoise_octave terrainFeaturesNoise;
    private final LandscapeGradient gradient = new LandscapeGradient();

    public TileGenerator(Random random) {
        landscapeNoise = new SimplexNoise_octave(random.nextInt());
        terrainFeaturesNoise = new SimplexNoise_octave(random.nextInt());
    }

    public Region loadRegion(float x, float y) {
        GenerationResult result = generate(x, y);

        Region region = Region.singleton();
        region.landscapeTile = result.landscapeTile;
        if (result.terrainFeature != null) {
            int surrounding = getFeatureTerrainSurrounding(x, y, result.terrainFeature);
            region.terrainFeatureTile = result.terrainFeature.getTile(surrounding);
        } else {
            region.terrainFeatureTile = null;
        }

        return region;
    }

    private GenerationResult generate(float x, float y) {
        LandscapeTile landscapeTile = generateLandscapeTile(x, y);
        TerrainFeature terrainFeature = generateTerrainFeature(landscapeTile.biome, x, y);

        return new GenerationResult(landscapeTile, terrainFeature);
    }

    public int getFeatureTerrainSurrounding(float x, float y, TerrainFeature feature) {
        int surrounding = TessellatedTileSet.NO_NEIGHBOUR;
        GenerationResult top = generate(x, y + 1);
        GenerationResult left = generate(x - 1, y);
        GenerationResult right = generate(x + 1, y);
        GenerationResult bottom = generate(x, y - 1);

        surrounding += computeSurroundingMask(feature, top.terrainFeature, TessellatedTileSet.TOP_NEIGHBOUR);
        surrounding += computeSurroundingMask(feature, left.terrainFeature, TessellatedTileSet.LEFT_NEIGHBOUR);
        surrounding += computeSurroundingMask(feature, right.terrainFeature, TessellatedTileSet.RIGHT_NEIGHBOUR);
        surrounding += computeSurroundingMask(feature, bottom.terrainFeature, TessellatedTileSet.BOTTOM_NEIGHBOUR);
        return surrounding;
    }

    private int computeSurroundingMask(TerrainFeature featureTile, TerrainFeature neighbourTile, int increment) {
        return neighbourTile != null && featureTile == neighbourTile ? increment : 0;
    }

    private LandscapeTile generateLandscapeTile(float x, float y) {
        float landscapeNoise = getNoiseValue(this.landscapeNoise, x * SCALE, y * SCALE);
        return gradient.getLandscapeTile(landscapeNoise);
    }

    private TerrainFeature generateTerrainFeature(Biome biome, float x, float y) {
        float rawNoise = getNoiseValue(terrainFeaturesNoise, x * SCALE, y * SCALE);
        TerrainFeature feature = null;
        if (rawNoise < 0.1f) {
            feature = TerrainFeature.ISLAND;
        } else if (rawNoise >= 0.25f && rawNoise < 0.3f) {
            feature = TerrainFeature.DIRT;
        } else if (rawNoise >= 0.4f && rawNoise < 0.6f) {
            feature = TerrainFeature.FORREST;
        } else if (rawNoise >= 0.65f && rawNoise < 0.7f) {
            feature = TerrainFeature.PEBBLES;
        } else if (rawNoise >= 0.8f && rawNoise < 0.85f) {
            feature = TerrainFeature.MOUNTAIN;
        } else if (rawNoise >= 0.9f && rawNoise < 0.95f) {
            feature = TerrainFeature.ROCK;
        } else if (rawNoise >= 0.95f){
            feature = TerrainFeature.FLOWERS;
        }
        if (feature != null && feature.isCompatible(biome)) {
            return feature;
        }
        return null;
    }

    private float getNoiseValue(SimplexNoise_octave noise, float x, float y) {
        return (float) (noise.noise(x, y) + 1) / 2;
    }

    public static class Region {
        public static Region INSTANCE = new Region();
        public LandscapeTile landscapeTile;
        public TerrainFeatureTile terrainFeatureTile;

        private Region() {
        }

        public static Region singleton() {
            return INSTANCE;
        }
    }

    private static class GenerationResult {
        public LandscapeTile landscapeTile;
        public TerrainFeature terrainFeature;

        private GenerationResult(LandscapeTile landscapeTile, TerrainFeature terrainFeature) {
            this.landscapeTile = landscapeTile;
            this.terrainFeature = terrainFeature;
        }
    }

}
