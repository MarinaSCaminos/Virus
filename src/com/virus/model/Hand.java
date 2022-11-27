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

    public boolean operateHand(Card card) {
        return this.operateHand(card, false, null);
    }

    public boolean operateHand(Card card, DiscardPile discardPile) {
        return this.operateHand(card, true, discardPile);
    }

    /**
     * Precondicion:
     * Es obligatorio que el card que recibe como parametro no sea null
     */
    private boolean operateHand(Card card, boolean discard, DiscardPile discardPile) {
        if (card.equals(this.card)) {
            if (discard) {
                discardPile.addCard(this.card);
            }
            this.card = null;
            return true;
        }
        if (card.equals(this.card2)) {
            if (discard) {
                discardPile.addCard(this.card2);
            }
            this.card2 = null;
            return true;
        }
        if (card.equals(card3)) {
            if (discard) {
                discardPile.addCard(this.card3);
            }
            this.card3 = null;
            return true;
        }
        return false;
    }

    public void refillHand() {
        if (this.card == null) {
            this.card = Deck.getInstance().getCard();
        }
        if (this.card2 == null) {
            this.card2 = Deck.getInstance().getCard();
        }
        if (this.card3 == null) {
            this.card3 = Deck.getInstance().getCard();
        }
    }

    public boolean isHandEmpty() {
        return this.card == null && this.card2 == null && this.card3 == null;
    }

    public List<Card> listCard() {
        List<Card> cards = new ArrayList<>();
        if (card != null) {
            cards.add(card);
        }
        if (card2 != null) {
            cards.add(card2);
        }
        if (card3 != null) {
            cards.add(card3);
        }
        return cards;
    }

    public List<String> getState() {
        List<Card> cards = listCard();

        List<String> list = new ArrayList<>();

        int numb = 0;
        for (Card card : cards) {
            numb++;
            String type;
            String name;
            type = card.getType().getSpanishName();
            if (card instanceof NormalCard) {
                if (card instanceof Organ) {
                    name = "Órgano";
                } else if (card instanceof Medicine) {
                    name = "Medicina";
                } else {
                    name = "Virus";
                }
            } else {
                name = "Tratamiento";
            }
            list.add(String.format(numb + ". " + "%s - " + type, name));
        }
        return list;
    }


}
