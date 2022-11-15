package com.pgg.generation;

import com.pgg.map.Tile;

public class TileGenerator {
    private static final float SCALE = 0.02f;

    private final SimplexNoise_octave simplexNoise;

    public TileGenerator(int seed) {
        simplexNoise = new SimplexNoise_octave(seed);
    }

    public Tile generateTile(float x, float y) {
        float rawNoise = (float) simplexNoise.noise(x * SCALE, y * SCALE);
        if (rawNoise < -0.8) {
            return Tile.DEEP_WATER;
        }
        if (rawNoise < -0.6) {
            return Tile.WATER;
        }
        if (rawNoise < -0.4) {
            return Tile.SHALLOW_WATER;
        }
        if (rawNoise < -0.2) {
            return Tile.BEACH;
        }
        if (rawNoise < 0) {
            return Tile.MARSHLANDS;
        }
        if (rawNoise < 0.2) {
            return Tile.MEADOW;
        }
        if (rawNoise < 0.4) {
            return Tile.GRASS;
        }
        if (rawNoise < 0.6) {
            return Tile.ROCKS;
        }
        if (rawNoise < 0.8) {
            return Tile.MOUNTAIN;
        }
        return Tile.SNOW;
    }
}
