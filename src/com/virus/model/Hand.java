package com.virus.model;

import com.virus.model.card.*;
import com.virus.model.enums.TypeOfOrgan;
import com.virus.model.enums.TypeOfTreatment;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private Card card;
    private Card card2;
    private Card card3;


    public Hand(Card card, Card card2, Card card3) {
        this.card = card;
        this.card2 = card2;
        this.card3 = card3;
    }

    public void discardHand() {
        this.card = null;
        this.card2 = null;
        this.card3 = null;
    }

    // TODO separar funcion descartar en Game
    public boolean operateHand(Card card, boolean discard) {
        if(card.equals(this.card)) {
            if(discard) {
                discardCard(this.card);
            }
            this.card = null;
            return true;
        }else if(card.equals(this.card2)) {
            if(discard) {
                discardCard(this.card2);
            }
            this.card2 = null;
            return true;
        }else if(card.equals(card3)) {
            if(discard) {
                discardCard(this.card3);
            }
            this.card3 = null;
            return true;
        }
        return false;
    }

    private void discardCard(Card card) {
        DiscardPile.getInstance().addCard(card);
    }

    public void reFillHand() {
        if(this.card == null) {
            this.card = Deck.getInstance().getCard();
        }
        if(this.card2 == null) {
            this.card2 = Deck.getInstance().getCard();
        }
        if(this.card3 == null) {
            this.card3 = Deck.getInstance().getCard();
        }
    }

    public boolean isHandEmpty() {
        return this.card == null && this.card2 == null && this.card3 == null;
    }

    public List<Card> listCard() {
        List<Card> cards = new ArrayList<>();
        if(card != null) {
            cards.add(card);
        }
        if(card2 != null) {
            cards.add(card2);
        }
        if(card3 != null) {
            cards.add(card3);
        }
        return cards;
    }

    public List<String> getState() {
        List<Card> cards = listCard();

        List<String> list = new ArrayList<>();

        int numb = 0;
        for(Card card : cards) {  //TODO crear un diccionario
            numb++;
            if(card != null) {      // No va a suceder
                if (card instanceof Organ) {
                    String type = "";
                    if (((Organ) card).getType().equals(TypeOfOrgan.HEART)) {
                        type = "Heart";
                    }
                    if (((Organ) card).getType().equals(TypeOfOrgan.STOMACH)) {
                        type = "Stomach";
                    }
                    if (((Organ) card).getType().equals(TypeOfOrgan.BRAIN)) {
                        type = "Brain";
                    }
                    if (((Organ) card).getType().equals(TypeOfOrgan.BONE)) {
                        type = "Bone";
                    }
                    if (((Organ) card).getType().equals(TypeOfOrgan.MULTICOLOR)) {
                        type = "Multicolor";
                    }
                    String state = numb +  ". " + "Organo - " + type;
                    list.add(state);
                } else if (card instanceof Medicine) {
                    String type = "";
                    if (((Medicine) card).getType().equals(TypeOfOrgan.HEART)) {
                        type = "Heart";
                    }
                    if (((Medicine) card).getType().equals(TypeOfOrgan.STOMACH)) {
                        type = "Stomach";
                    }
                    if (((Medicine) card).getType().equals(TypeOfOrgan.BRAIN)) {
                        type = "Brain";
                    }
                    if (((Medicine) card).getType().equals(TypeOfOrgan.BONE)) {
                        type = "Bone";
                    }
                    if (((Medicine) card).getType().equals(TypeOfOrgan.MULTICOLOR)) {
                        type = "Multicolor";
                    }
                    String state = numb +  ". " + "Medicine - " + type;
                    list.add(state);
                } else if (card instanceof Virus) {
                    String type = "";
                    if (((Virus) card).getType().equals(TypeOfOrgan.HEART)) {
                        type = "Heart";
                    }
                    if (((Virus) card).getType().equals(TypeOfOrgan.STOMACH)) {
                        type = "Stomach";
                    }
                    if (((Virus) card).getType().equals(TypeOfOrgan.BRAIN)) {
                        type = "Brain";
                    }
                    if (((Virus) card).getType().equals(TypeOfOrgan.BONE)) {
                        type = "Bone";
                    }
                    if (((Virus) card).getType().equals(TypeOfOrgan.MULTICOLOR)) {
                        type = "Multicolor";
                    }
                    String state = numb +  ". " + "Virus - " + type;
                    list.add(state);
                } else if (card instanceof Treatment) {
                    String type = "";
                    if (((Treatment) card).getType().equals(TypeOfTreatment.TRANSPLANT)) {
                        type = "Transplant";
                    }
                    if (((Treatment) card).getType().equals(TypeOfTreatment.ORGAN_THIEF)) {
                        type = "Organ Thief";
                    }
                    if (((Treatment) card).getType().equals(TypeOfTreatment.CONTAGION)) {
                        type = "Contagion";
                    }
                    if (((Treatment) card).getType().equals(TypeOfTreatment.LATEX_GLOVES)) {
                        type = "Latex Gloves";
                    }
                    if (((Treatment) card).getType().equals(TypeOfTreatment.MEDICAL_ERROR)) {
                        type = "Medical Error";
                    }
                    String state = numb +  ". " + "Treatment - " + type;
                    list.add(state);
                }
            }
        }
            return list;
    }



}
