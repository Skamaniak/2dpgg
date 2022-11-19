package com.pgg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class UiCamera extends OrthographicCamera {
    private static final int VIEWPORT_WIDTH = 1280;
    private static final int VIEWPORT_HEIGHT = 720;

    public UiCamera() {
        super(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }
}
