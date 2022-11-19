package com.pgg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;

public class SceneCamera extends OrthographicCamera {
    private static final int VIEWPORT_WIDTH = 1280;
    private static final int VIEWPORT_HEIGHT = 720;

    public static final float ZOOM_MIN = 0.5f;
    public static final float ZOOM_MAX = 4f;
    public static final float ZOOM_STEP = 0.05f;

    public SceneCamera() {
        super(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    public void updatePosition(float x, float y) {
        boolean updateRequired = position.x != x || position.y != y;
        if (updateRequired) {
            this.position.x = x;
            this.position.y = y;
            this.update();
        }
    }

    private void zoomOut() {
        float currentZoom = zoom;
        float nextZoom = zoom + ZOOM_STEP;
        zoom = nextZoom >= ZOOM_MAX ? ZOOM_MAX : nextZoom;
        if (currentZoom != zoom) {
            update();
        }
    }

    private void zoomIn() {
        float currentZoom = zoom;
        float nextZoom = zoom - ZOOM_STEP;
        zoom = nextZoom <= ZOOM_MIN ? ZOOM_MIN : nextZoom;
        if (currentZoom != zoom) {
            update();
        }
    }

    public void registerInputActions(Keyboard keyboard) {
        keyboard.registerKeyPressAction(Controls.CAMERA_ZOOM_IN, this::zoomIn);
        keyboard.registerKeyPressAction(Controls.CAMERA_ZOOM_OUT, this::zoomOut);
    }
}
