package com.virus.model.utils;

import java.util.Random;

public class CustomRandom {  //TODO volverlo singleton, metodo getRandom no estatico

    public static int getRandom(int to) {
        Random random = new Random();
        int result = random.nextInt();
        result = result > 0 ? result : -result;
        return result % to;   // modulo no incluye "to"
    }
}
