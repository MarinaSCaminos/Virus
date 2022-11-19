package com.virus.model.card;

import com.virus.model.enums.TypeOfOrgan;

public class Virus extends NormalCard{

    public Virus(TypeOfOrgan type) {
        super(type);
    }

    @Override // Sobre escribo el metodo de Carta. Me devuelve la clase de carta y el tipo
    public String getIdentifier() {
        return String.format("%s - %s", Virus.class.getSimpleName(), super.getType().name());
    }
}
