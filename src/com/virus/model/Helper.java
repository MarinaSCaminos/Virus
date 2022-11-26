package com.virus.model;

import com.virus.model.card.Medicine;
import com.virus.model.card.Organ;
import com.virus.model.card.Treatment;
import com.virus.model.card.Virus;
import com.virus.model.enums.TypeOfOrgan;
import com.virus.model.enums.TypeOfTreatment;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private static Helper INSTANCE;
    private final Map<String, String> cardsDescription; // Map --> Diccionario  (generico)
    // HashMap --> es un diccionario que utiliza un hash por dentro

    private Helper() {
        this.cardsDescription = new HashMap<>();
        this.cardsDescription.put(String.format("%s - %s", Virus.class.getSimpleName(), TypeOfOrgan.BONE.name()),
                "Puede infectar el organo \"Hueso\" ");        //put --> si no existe lo agrega, si existe lo reemplaza
        this.cardsDescription.put(String.format("%s - %s", Virus.class.getSimpleName(), TypeOfOrgan.HEART.name()),
                "Puede infectar el organo \"Corazon\" ");
        this.cardsDescription.put(String.format("%s - %s", Virus.class.getSimpleName(), TypeOfOrgan.BRAIN.name()),
                "Puede infectar el organo \"Cerebro\" ");
        this.cardsDescription.put(String.format("%s - %s", Virus.class.getSimpleName(), TypeOfOrgan.STOMACH.name()),
                "Puede infectar el organo \"Pulmón\" ");
        this.cardsDescription.put(String.format("%s - %s", Virus.class.getSimpleName(), TypeOfOrgan.MULTICOLOR.name()),
                "Puede infectar cualquier organo");

        this.cardsDescription.put(String.format("%s - %s", Organ.class.getSimpleName(), TypeOfOrgan.BONE.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Organ.class.getSimpleName(), TypeOfOrgan.HEART.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Organ.class.getSimpleName(), TypeOfOrgan.BRAIN.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Organ.class.getSimpleName(), TypeOfOrgan.STOMACH.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Organ.class.getSimpleName(), TypeOfOrgan.MULTICOLOR.name()),
                " ");

        this.cardsDescription.put(String.format("%s - %s", Medicine.class.getSimpleName(), TypeOfOrgan.BONE.name()),
                "Puede curar el organo \"Hueso\" ");
        this.cardsDescription.put(String.format("%s - %s", Medicine.class.getSimpleName(), TypeOfOrgan.HEART.name()),
                "Puede curar el organo \"Corazón\" ");
        this.cardsDescription.put(String.format("%s - %s", Medicine.class.getSimpleName(), TypeOfOrgan.BRAIN.name()),
                "Puede curar el organo \"Cerebro\" ");
        this.cardsDescription.put(String.format("%s - %s", Medicine.class.getSimpleName(), TypeOfOrgan.STOMACH.name()),
                "Puede curar el organo \"Pulmón\" ");
        this.cardsDescription.put(String.format("%s - %s", Medicine.class.getSimpleName(), TypeOfOrgan.MULTICOLOR.name()),
                "Puede curar el organo \"Multicolor\" ");

        this.cardsDescription.put(String.format("%s - %s", Treatment.class.getSimpleName(), TypeOfTreatment.MEDICAL_ERROR.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Treatment.class.getSimpleName(), TypeOfTreatment.TRANSPLANT.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Treatment.class.getSimpleName(), TypeOfTreatment.CONTAGION.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Treatment.class.getSimpleName(), TypeOfTreatment.LATEX_GLOVES.name()),
                " ");
        this.cardsDescription.put(String.format("%s - %s", Treatment.class.getSimpleName(), TypeOfTreatment.ORGAN_THIEF.name()),
                " ");


    }

    public static Helper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Helper();
        }
        return INSTANCE;
    }

    public String getCardDescription(String identifier) {
        return cardsDescription.get(identifier);
    }
}
