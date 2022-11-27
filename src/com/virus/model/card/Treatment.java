package com.virus.model.card;

import com.virus.model.enums.TypeOfTreatment;

public class Treatment implements Card {

    private final TypeOfTreatment type;

    public Treatment(TypeOfTreatment type) {
        this.type = type;
    }

    @Override
    public TypeOfTreatment getType() {
        return type;
    }
}
