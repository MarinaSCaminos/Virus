package com.virus.model.card;

import com.virus.model.enums.TypeOfOrgan;

// Cartas agrupadas por color (Organos, Virus, Medicina)
public abstract class NormalCard implements Card {

    private final TypeOfOrgan type;

    public NormalCard(TypeOfOrgan type) {
        this.type = type;
    }

    @Override
    public TypeOfOrgan getType() {
        return type;
    }
}
