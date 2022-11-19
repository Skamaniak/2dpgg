package com.pgg.input;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Keyboard {

    private final Set<KeyPressCallback> keyPressCallbacks = new HashSet<>();
    private final Set<KeyPressCallback> keyJustPressCallbacks = new HashSet<>();

    public void readInputs() {
        invokeCallbacks(keyPressCallbacks, Gdx.input::isKeyPressed);
        invokeCallbacks(keyJustPressCallbacks, Gdx.input::isKeyJustPressed);
    }

    private void invokeCallbacks(Set<KeyPressCallback> callbacks, Predicate<Integer> test) {
        callbacks.forEach(callback -> {
            boolean pressed = callback.controls.getKeys()
                    .stream()
                    .anyMatch(test);

            if (pressed) {
                callback.action.invoke();
            }
        });
    }

    public void registerKeyPressAction(Controls controls, KeyPressAction action) {
        keyPressCallbacks.add(new KeyPressCallback(controls, action));
    }

    public void registerKeyJustPressAction(Controls controls, KeyPressAction action) {
        keyJustPressCallbacks.add(new KeyPressCallback(controls, action));
    }

    @FunctionalInterface
    public interface KeyPressAction {
        void invoke();
    }

    private static class KeyPressCallback {
        private final Controls controls;
        private final KeyPressAction action;

        public KeyPressCallback(Controls controls, KeyPressAction action) {
            this.controls = controls;
            this.action = action;
        }
    }
}
