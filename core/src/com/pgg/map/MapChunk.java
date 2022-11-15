package com.pgg.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.pgg.generation.TileGenerator;

public class MapChunk {
    public static final int COORDINATES_CENTER_CHUNK_X = 0;
    public static final int COORDINATES_CENTER_CHUNK_Y = 0;
    public static final int CHUNK_SIZE = 32;

    private final Tile[][] chunk = new Tile[CHUNK_SIZE][CHUNK_SIZE];
    private final int xOffset;
    private final int yOffset;
    public final int chunkX;
    public final int chunkY;
    public boolean tainted = false;

    public MapChunk(TileGenerator generator, int chunkX, int chunkY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.xOffset = COORDINATES_CENTER_CHUNK_X + Tile.TILE_SIZE * chunkX * CHUNK_SIZE;
        this.yOffset = COORDINATES_CENTER_CHUNK_Y + Tile.TILE_SIZE * chunkY * CHUNK_SIZE;

        generate(generator);
    }

    private void generate(TileGenerator generator) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                chunk[y][x] = generator.generateTile(x + chunkX * CHUNK_SIZE, y + chunkY * CHUNK_SIZE);
            }
        }
    }

    private boolean inCameraFrustum(Rectangle viewBounds, int tileX, int tileY) {
        return !(tileX + Tile.TILE_SIZE < viewBounds.x
                || tileX > viewBounds.x + viewBounds.width
                || tileY + Tile.TILE_SIZE < viewBounds.y
                || tileY > viewBounds.y + viewBounds.height);
    }


    public void render(Batch batch, Rectangle viewBounds) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                int drawX = xOffset + x * Tile.TILE_SIZE;
                int drawY = yOffset + y * Tile.TILE_SIZE;
                if (inCameraFrustum(viewBounds, drawX, drawY)) {
                    batch.draw(chunk[y][x].getTile(), drawX, drawY);
                }
            }
        }
        drawDebug(batch);
    }

    // FIXME debug
    private final BitmapFont font = new BitmapFont();

    private void drawDebug(Batch batch) {
        font.setColor(Color.RED);
        font.draw(batch, "[" + chunkX + ", " + chunkY + "]", xOffset + 10, yOffset + 20);
    }

}
