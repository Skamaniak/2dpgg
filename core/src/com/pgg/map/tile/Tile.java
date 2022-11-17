package com.pgg.map.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Tile {
    int TILE_SIZE = 32;

    TextureRegion getTexture();

    float getSpeedModifier();
}