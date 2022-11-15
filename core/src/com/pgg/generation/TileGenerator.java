package com.pgg.generation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.generation.gradient.LandscapeGradient;

public class TileGenerator {
    private static final float SCALE = 0.02f;

    private final SimplexNoise_octave simplexNoise;
    private final LandscapeGradient gradient = new LandscapeGradient();

    public TileGenerator(int seed) {
        simplexNoise = new SimplexNoise_octave(seed);
    }

    public TextureRegion generateTile(float x, float y) {
        float rawNoise = (float) (simplexNoise.noise(x * SCALE, y * SCALE) + 1) / 2;
        return gradient.getTexture(rawNoise);
    }

}
