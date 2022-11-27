package com.virus.model.enums;

public enum TypeOfOrgan implements Type {

    HEART("Corazón"),
    STOMACH("Estómago"),
    BRAIN("Cerebro"),
    BONE("Hueso"),
    MULTICOLOR("Multicolor");

    private final String spanishName;

    TypeOfOrgan(String spanishName) {
        this.spanishName = spanishName;
    }

    public String getSpanishName() {
        return this.spanishName;
    }

}