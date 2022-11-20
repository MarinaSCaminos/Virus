package com.virus.model;

import java.util.Objects;

public class Player { // TODO agregar el modificador de nombre en jugadores

    private String name;
    private Hand hand;
    private Body body;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void setBody(Body body){
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Hand getHand() {
        return hand;
    }

    public Body getBody() {
        return body;
    }

    public void playMedicalErrorCard(Player player) {
        Body thisBody = this.getBody();
        this.setBody(player.getBody());
        player.setBody(thisBody);
    }
}
