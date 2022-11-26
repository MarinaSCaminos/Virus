package com.virus.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum TypeOfOrgan {

    HEART("Corazón"),
    STOMACH("Estómago"),
    BRAIN("Cerebro"),
    BONE("Hueso"),
    MULTICOLOR("Multicolor");

    private String spanishName;

    TypeOfOrgan(String spanishName) {
        this.spanishName = spanishName;
    }

    public String getSpanishName() {
        return this.spanishName;
    }

}