package com.pgg.generation;

import com.pgg.generation.landscape.LandscapeGradient;
import com.pgg.map.tile.LandscapeTile;
import com.pgg.map.tile.TerrainFeatureTile;

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

    public GenerationResult generateRegion(float x, float y) {
        LandscapeTile landscapeTile = generateLandscapeTile(x, y);
        TerrainFeatureTile terrainFeatureTile = generateTerrainFeatureTile(x, y);

        GenerationResult generationResult = GenerationResult.singleton();
        generationResult.landscapeTile = landscapeTile;
        if (terrainFeatureTile != null && terrainFeatureTile.isCompatible(landscapeTile.biome)) {
            generationResult.terrainFeatureTile = terrainFeatureTile;
        } else {
            generationResult.terrainFeatureTile = null;
        }
        return generationResult;
    }

    private LandscapeTile generateLandscapeTile(float x, float y) {
        float landscapeNoise = getNoiseValue(this.landscapeNoise, x * SCALE, y * SCALE);
        return gradient.getLandscapeTile(landscapeNoise);
    }

    private TerrainFeatureTile generateTerrainFeatureTile(float x, float y) {
        float rawNoise = getNoiseValue(terrainFeaturesNoise, x * SCALE, y * SCALE);
        if (rawNoise < 0.1f) {
            return TerrainFeatureTile.ISLAND;
        }
        if (rawNoise >= 0.25f && rawNoise < 0.3f) {
            return TerrainFeatureTile.DIRT;
        }
        if (rawNoise >= 0.4f && rawNoise < 0.7f) {
            return TerrainFeatureTile.FORREST;
        }
        if (rawNoise >= 0.8f && rawNoise < 0.85f) {
            return TerrainFeatureTile.MOUNTAIN;
        }
        if (rawNoise >= 0.9f && rawNoise < 0.95f) {
            return TerrainFeatureTile.ROCK;
        }
        return null;
    }

    private float getNoiseValue(SimplexNoise_octave noise, float x, float y) {
        return (float) (noise.noise(x, y) + 1) / 2;
    }

    public static class GenerationResult {
        public static GenerationResult INSTANCE = new GenerationResult();
        public LandscapeTile landscapeTile;
        public TerrainFeatureTile terrainFeatureTile;

        private GenerationResult() {
        }

        public static GenerationResult singleton() {
            return INSTANCE;
        }
    }

}
