package com.pgg.map.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TerrainFeatureTile implements Tile {

    private final TextureRegion texture;
    private final float speedModifier;

    public TerrainFeatureTile(TextureRegion texture, float speedModifier) {
        this.texture = texture;
        this.speedModifier = speedModifier;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public float getSpeedModifier() {
        return speedModifier;
    }

}
