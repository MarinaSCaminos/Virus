package com.virus.model.card;

import com.virus.model.enums.TypeOfTreatment;

public class Treatment extends Card {

    private final TypeOfTreatment type;

    public Treatment(TypeOfTreatment type) {
        this.type = type;
    }

    public TypeOfTreatment getType() {
        return type;
    }

    @Override
    public String getIdentifier() {
        return String.format("%s - %s", Treatment.class.getSimpleName(), type.name());
    }
}
