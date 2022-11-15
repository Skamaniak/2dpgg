package com.pgg;

import com.pgg.generation.SimplexNoise_octave;

import java.util.Random;

public class Test {
    private static long SEED = 12345;

    public static void main(String[] args) {
        int r = 86;
        int g = 125;
        int b = 70;

        System.out.println(((float) r / 255) + " " + ((float) g / 255) + " " + ((float) b / 255));

    }
}
