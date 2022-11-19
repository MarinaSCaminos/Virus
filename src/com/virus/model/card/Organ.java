package com.virus.model.card;

import com.virus.model.enums.TypeOfOrgan;

public class Organ extends NormalCard {

    public Organ(TypeOfOrgan type) {
        super(type);
    }

    @Override
    public String getIdentifier() {
        return String.format("%s - %s", Organ.class.getSimpleName(), super.getType().name());
    }
}
