package com.pgg.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pgg.player.Player;

public class Keyboard {

    public static final float ZOOM_MIN = 0.5f;
    public static final float ZOOM_MAX = 20f;
    public static final float ZOOM_STEP = 0.1f;

    public boolean moveCamera(OrthographicCamera camera) {
        float zoom = stepZoom(camera.zoom);

        if (zoom != camera.zoom) {
            camera.zoom = zoom;
            return true;
        }
        return false;
    }

    public void readInputs(Player player) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.accelerateLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.accelerateRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.accelerateDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)  || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.accelerateUp();
        }
    }

    private float stepZoom(float zoom) {
        float nextZoom;
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            nextZoom = zoom + ZOOM_STEP;
            return nextZoom >= ZOOM_MAX ? ZOOM_MAX : nextZoom;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            nextZoom = zoom - ZOOM_STEP;
            return nextZoom <= ZOOM_MIN ? ZOOM_MIN : nextZoom;
        }
        return zoom;
    }
}
