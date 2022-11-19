package com.pgg.input;

import static com.badlogic.gdx.Input.Keys.*;

import java.util.Set;

public enum Controls {
    PLAYER_MOVE_LEFT(A, LEFT),
    PLAYER_MOVE_RIGHT(D, RIGHT),
    PLAYER_MOVE_UP(W, UP),
    PLAYER_MOVE_DOWN(S, DOWN),

    CAMERA_ZOOM_IN(E),
    CAMERA_ZOOM_OUT(Q),

    INVENTORY_SELECT_SLOT_1(NUM_1, NUMPAD_1),
    INVENTORY_SELECT_SLOT_2(NUM_2, NUMPAD_2),
    INVENTORY_SELECT_SLOT_3(NUM_3, NUMPAD_3),
    INVENTORY_SELECT_SLOT_4(NUM_4, NUMPAD_4),
    INVENTORY_SELECT_SLOT_5(NUM_5, NUMPAD_5),
    INVENTORY_SELECT_SLOT_6(NUM_6, NUMPAD_6),
    INVENTORY_SELECT_SLOT_7(NUM_7, NUMPAD_7),
    INVENTORY_SELECT_SLOT_8(NUM_8, NUMPAD_8),
    INVENTORY_SELECT_SLOT_9(NUM_9, NUMPAD_9),
    INVENTORY_SELECT_SLOT_10(NUM_0, NUMPAD_0),

    DEBUG_ENABLED(F1),
    DEBUG_PRINT_INFO(F2);

    private final Set<Integer> keys;

    Controls(Integer... keys) {
        this.keys = Set.of(keys);
    }

    public Set<Integer> getKeys() {
        return keys;
    }


}
