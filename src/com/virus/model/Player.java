package com.virus.model;

import java.util.Objects;

public class Player {

    private final String name;
    private Hand hand;
    private Body body;

    public Player(String name) {
        this.name = name;
    }

    public void playMedicalErrorCard(Player player) {
        Body thisBody = this.body;
        this.body = player.getBody();
        player.setBody(thisBody);
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equalsIgnoreCase(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
