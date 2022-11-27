package com.virus.controller;

import com.virus.model.Game;

public class Controller {

    private static Controller INSTANCE;
    private final Game game;

    private Controller() {
        this.game = Game.getInstance();
    }

    public static Controller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public void addPlayer(String name) {
        game.addPlayer(name);
    }

    public boolean deletePlayer(String name) {
        return game.deletePlayer(name);
    }

    public boolean validateTotalPlayers() {
        return game.validateTotalPlayers();
    }

    public String getListOfPlayers() {
        return game.getListOfPlayers();
    }

    public void initialize() {
        game.initialize();
    }

    public void nextTurn() {
        game.nextTurn();
    }

    public String getPlayerNameByTurn() {
        return game.getPlayerNameByTurn();
    }

    public boolean existWinner() {
        return game.existWinner();
    }

    public String getWinnerName() {
        return game.getWinnerName();
    }

    public boolean actualHandPlayerIsEmpty() {
        return game.actualHandPlayerIsEmpty();
    }

    public void actualHandPlayerRefill() {
        game.actualHandPlayerRefill();
    }

    public String mapPlayerInTurn() {
        return game.mapPlayerInTurn();
    }

    public String otherPlayersBody(String title) {
        return game.otherPlayersBody(title);
    }

    public String mapCard() {
        return game.mapCard();
    }

    public int getNumberOfCards() {
        return game.getNumberOfCards();
    }

    public String mapOptions(String title) {
        return game.mapOptions(title);
    }

    public boolean isNormalCard(String option) {
        return game.isNormalCard(option);
    }

    public boolean isOrgan(String option) {
        return game.isOrgan(option);
    }

    public boolean isMedicine(String option) {
        return game.isMedicine(option);
    }

    public boolean isTransplant(String option) {
        return game.isTransplant(option);
    }

    public boolean isOrganThief(String option) {
        return game.isOrganThief(option);
    }

    public boolean isContagion(String option) {
        return game.isContagion(option);
    }

    public boolean isLatexGloves(String option) {
        return game.isLatexGloves(option);
    }

    public boolean isMedicalError(String option) {
        return game.isMedicalError(option);
    }

    public boolean addOrganToBody(String option) {
        return game.addOrganToBody(option);
    }

    public boolean addMedicineToBody(String cardPosition, String typeOfOrgan) {
        return game.addMedicineToBody(cardPosition, typeOfOrgan);
    }

    public boolean addVirusToBody(String cardPosition, String typeOfOrgan, int player) {
        return game.addVirusToBody(cardPosition, typeOfOrgan, player);
    }

    public void discardHandCard(String cardPosition) {
        game.discardHandCard(cardPosition);
    }

    public boolean playTransplant(String cardPosition, String organToSwap, String selectedPlayer, String playerOrganToSwap) {
        return game.playTransplant(cardPosition, organToSwap, selectedPlayer, playerOrganToSwap);
    }

    public boolean playOrganThief(String cardPosition, String selectedPlayer, String playerOrganToSteal) {
        return game.playOrganThief(cardPosition, selectedPlayer, playerOrganToSteal);
    }

    public boolean playContagion(String selectedVirus, String playerToInfect, String organToInfect) {
        return game.playContagion(selectedVirus, playerToInfect, organToInfect);
    }

    public boolean canInfect() {
        return game.canInfect();
    }

    public void playLatexGloves(String cardPosition) {
        game.playLatexGloves(cardPosition);
    }

    public void playMedicalError(String cardPosition, String playerToSwapBody) {
        game.playMedicalError(cardPosition, playerToSwapBody);
    }

    public int getPositionPlayerByName(String name) {
        return game.getPositionPlayerByName(name);
    }

    public boolean existPlayer(String name) {
        return game.existPlayer(name);
    }

    public boolean notPlayerInTurn(String name) {
        return game.notPlayerInTurn(name);
    }

}

