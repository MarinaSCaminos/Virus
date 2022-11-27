package com.virus.model.enums;

public enum TypeOfTreatment implements Type {

    TRANSPLANT("Trasplante"),
    ORGAN_THIEF("Ladrón de órganos"),
    CONTAGION("Contagio"),
    LATEX_GLOVES("Guantes de latex"),
    MEDICAL_ERROR("Error médico");

    private final String spanishTreatmentName;

    TypeOfTreatment(String spanishTreatmentName) {
        this.spanishTreatmentName = spanishTreatmentName;
    }

    @Override
    public String getSpanishName() {
        return this.spanishTreatmentName;
    }

}
