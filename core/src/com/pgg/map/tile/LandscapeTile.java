package com.pgg.map.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pgg.generation.landscape.Biome;

public class LandscapeTile implements Tile {
    public final Biome biome;
    private final TextureRegion texture;
    private final int elevation;

    public LandscapeTile(Biome biome, int elevation, TextureRegion texture) {
        this.biome = biome;
        this.elevation = elevation;
        this.texture = texture;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public float getSpeedModifier() {
        return biome.speedModifier;
    }

    @Override
    public String toString() {
        return "LandscapeTile{" +
                "biome=" + biome +
                ", elevation=" + elevation +
                '}';
    }
}
