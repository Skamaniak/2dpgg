package com.pgg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pgg.inventory.HotBar;

public class UiRenderer {
    private final Batch batch = new SpriteBatch();
    private final OrthographicCamera camera;
    private final HotBar hotBar;


    public UiRenderer(OrthographicCamera camera, HotBar hotBar) {
        this.camera = camera;
        this.hotBar = hotBar;
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        hotBar.render(batch);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
