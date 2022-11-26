package com.virus.model.card;

import com.virus.model.enums.TypeOfOrgan;

public class Medicine extends NormalCard {

    public Medicine(TypeOfOrgan type) {
        super(type);
    }

    @Override
    public String getIdentifier() {
        return String.format("%s - %s", Medicine.class.getSimpleName(), super.getType().name());
    }
}
