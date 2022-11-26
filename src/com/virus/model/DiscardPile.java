package com.virus.model;

import com.virus.model.card.Card;

import java.util.Stack;

public class DiscardPile {

    private static DiscardPile INSTANCE;
    private final Stack<Card> stack;

    private DiscardPile() {
        stack = new Stack<>();
    }

    public static DiscardPile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiscardPile();
        }
        return INSTANCE;
    }

    public Card getCard() {
        return stack.pop();
    }

    public Stack<Card> getStack() {
        return stack;
    }

    public void addCard(Card card) {
        stack.add(card);
    }

    public boolean empty() {
        return stack.empty();
    }

}
