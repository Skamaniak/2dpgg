package com.pgg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pgg.map.MapChunk;
import com.pgg.player.Player;

import java.util.Collection;

public class GameRenderer {
    private final OrthographicCamera camera;
    private final Player player;
    private final Batch batch = new SpriteBatch();
    private final Rectangle viewBounds = new Rectangle();

    public GameRenderer(OrthographicCamera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    private void updateViewBounds() {
        float width = camera.viewportWidth * camera.zoom;
        float height = camera.viewportHeight * camera.zoom;
        float w = width * Math.abs(camera.up.y) + height * Math.abs(camera.up.x);
        float h = height * Math.abs(camera.up.y) + width * Math.abs(camera.up.x);
        viewBounds.set(camera.position.x - w / 2, camera.position.y - h / 2, w, h);
    }

    public void render(Collection<MapChunk> chunks) {
        updateViewBounds();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        chunks.forEach(c -> c.render(batch, viewBounds));
        player.render(batch);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
