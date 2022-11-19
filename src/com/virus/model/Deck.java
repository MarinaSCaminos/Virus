package com.virus.model;

import com.virus.controller.Game;
import com.virus.model.card.*;
import com.virus.model.enums.TypeOfOrgan;
import com.virus.model.enums.TypeOfTreatment;
import com.virus.model.utils.CustomRandom;

import java.net.PortUnreachableException;
import java.util.Stack;

public class Deck {

    private static Deck INSTANCE;
    private Stack<Card> stack;

    //Crea el mazo de cartas --> Constructor privado (singleton)
    private Deck() {
        stack = new Stack<>();

        stack.add(new Organ(TypeOfOrgan.MULTICOLOR));
        for(int i = 0; i<5; i++) {
            stack.add(new Organ(TypeOfOrgan.HEART));
            stack.add(new Organ(TypeOfOrgan.STOMACH));
            stack.add(new Organ(TypeOfOrgan.BONE));
            stack.add(new Organ(TypeOfOrgan.BRAIN));
        }

        stack.add(new Virus(TypeOfOrgan.MULTICOLOR));
        for(int i = 0; i<4; i++) {
            stack.add(new Virus(TypeOfOrgan.HEART));
            stack.add(new Virus(TypeOfOrgan.STOMACH));
            stack.add(new Virus(TypeOfOrgan.BONE));
            stack.add(new Virus(TypeOfOrgan.BRAIN));
        }

        stack.add(new Medicine(TypeOfOrgan.MULTICOLOR));
        for(int i = 0; i<4; i++) {
            stack.add(new Medicine(TypeOfOrgan.HEART));
            stack.add(new Medicine(TypeOfOrgan.STOMACH));
            stack.add(new Medicine(TypeOfOrgan.BONE));
            stack.add(new Medicine(TypeOfOrgan.BRAIN));
        }

        stack.add(new Treatment(TypeOfTreatment.CONTAGION));
        stack.add(new Treatment(TypeOfTreatment.CONTAGION));
        stack.add(new Treatment(TypeOfTreatment.LATEX_GLOVES));
        stack.add(new Treatment(TypeOfTreatment.MEDICAL_ERROR));
        for(int i = 0; i<3; i++) {
            stack.add(new Treatment(TypeOfTreatment.ORGAN_THIEF));
            stack.add(new Treatment(TypeOfTreatment.TRANSPLANT));
        }

    }

    // Mezcla el mazo
    public void mixTheCards() {  //Collections.shuffle(mazoDeCartas);
        Stack<Card> aux = new Stack<>();

        while(!this.stack.empty()) {
            int randomIndex = CustomRandom.getRandom(stack.size());
            Card card = stack.get(randomIndex);
            aux.add(card);
            stack.remove(randomIndex);
        }

        while(!aux.empty()) {
            stack.add(aux.pop());
        }
    }

    public void reFillDeck() {
        if(this.stack.empty()) {
            this.stack = DiscardPile.getInstance().getStack();    //TODO confirmar si esta bien
            DiscardPile.getInstance().getStack().clear();
        }
    }

    /**
     * Precondicion: validar que no este vacio
     */
    public Card getCard(){
        return stack.pop();
    }

    public boolean empty() {
        return stack.empty();
    }

    public static Deck getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Deck();
        }
        return INSTANCE;
    }
}
