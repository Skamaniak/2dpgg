package com.pgg;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.pgg.input.Controls;
import com.pgg.input.Keyboard;

public class Debug {
    public static boolean DEBUG_ENABLED = false;
    public static final BitmapFont DEBUG_FONT = new BitmapFont();

    public static void registerInputActions(Keyboard keyboard) {
        keyboard.registerKeyJustPressAction(Controls.DEBUG_ENABLED, () -> DEBUG_ENABLED = !DEBUG_ENABLED);
    }
}
