package com.virus.controller;

/* Los metodos estaticos se pueden acceder de manera: "Clase. ...."--> se pueden acceder haicendo una instancia */
/* ArrayList es una clase, List es una interface */
/* BUSCAR: pricipio de superposicion de Liskov*/

import com.virus.model.*;
import com.virus.model.card.*;
import com.virus.model.enums.TypeOfOrgan;
import com.virus.model.enums.TypeOfTreatment;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game INSTANCE;
    private final List<Player> players;
    private final Deck deck;
    private final DiscardPile discardPile;
    private int turn;
    private Player winner;

    private Game(){
        this.players = new ArrayList<>();
        deck = Deck.getInstance();
        deck.mixTheCards();
        discardPile = DiscardPile.getInstance();
        turn = 0;
    }

    public static Game getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(String name){
        players.add(new Player(name));
    }

    public boolean deletePlayer(String name) {
        for(Player player : players) {
            if(player.getName().equals(name)){
                players.remove(player);
                return true;
            }
        }
        return false;
    }

    public boolean validateTotalPlayers() {
        return players.size() > 1 && players.size() < 7;
    }

    public String getListOfPlayers() {
        String listOfPlayers = "";
        for(Player player : players) {
            listOfPlayers+= "\n" + player.getName();
        }
        return listOfPlayers.substring(1);    //Elmina el primer caracter
    }

    /**
    Precondicion: validar la cantidad de jugadores
     */
    public void initialize() {  // asigno la mano a los jugadores

         for(Player player : players) {
             Hand hand = new Hand(deck.getCard(), deck.getCard(), deck.getCard());
             player.setHand(hand);
             player.setBody(new Body());
         }
    }

    public void nextTurn() { //El turno va a ser la posicion en el array de Player
        turn = (turn + 1) % players.size();
    }

    public Player getPlayerByTurn() {
        return this.players.get(turn);
    }

    public String getPlayerNameByTurn() { return this.players.get(turn).getName(); }

    public boolean existWinner() {
        for(Player player : players) {
            Body body = player.getBody();
            if(body.isSavedHeart() && body.isSavedStomach() && body.isSavedBrain() && body.isSavedBone()) {
                this.winner = player;
                return true;
            }
        }
        return false;
    }

    public Player getWinner() {
        return winner;
    }

    public String geWinnerName() { return this.winner.getName(); }


    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*

    public boolean actualHandPlayerIsEmpty() {
        return this.getPlayerByTurn().getHand().isHandEmpty();
    }
    public void actualHandPlayerReFill() {
        this.getPlayerByTurn().getHand().reFillHand();
    }

    // map es programacion es una funcion que convierte una cosa en otra
    private String map(List<List<String>> matrix) {
        String result = "";
        for(List<String> row : matrix) {
            for(String element : row) {
                result += element + "\t";
            }
            result = result + "\n";
        }
        return result;
    }

    public String mapPlayerInTurn() {
        List<List<String>> matrix = this.getPlayerByTurn().getBody().getStateMatrix();
        return this.map(matrix);
    }

    public String otherPlayersBody(String title) {
        Player actual = this.getPlayerByTurn();
        String bodyPlayers = "";
        int i = 0;
        for (Player player : this.getPlayers()) {
            if (!player.equals(actual)) {
                bodyPlayers = bodyPlayers + i + String.format(title, player.getName()) + "\n"
                        + this.map(player.getBody().getStateMatrix()) + "\n";
                i++;
            }

        }
        return bodyPlayers;
    }


    public String mapCard() {
        List<String> list = this.getPlayerByTurn().getHand().getState();
        String result = "";
        for(String card : list) {
            result += card + "\n";
        }
        return result;
    }

    // Recibe una lista de Typos haciendo referencia a cual pila del cuerpo se puede jugar la carta seleccionada
    public String mapOptions(String title) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        String result = "";
        for(int i=0; i<list.size(); i++) {
            result += i + String.format(title, TypeOfOrgan.getNameType(list.get(i))) + "\n";
        }
        return result;
    }


    public boolean isNormalCard(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1) instanceof NormalCard;
    }

    public boolean isOrgan(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1) instanceof Organ;
    }

    public boolean isMedicine(String option) {
       return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1) instanceof Medicine;
    }

    public boolean isVirus(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1) instanceof Virus;
    }

    public boolean isTreatment(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1) instanceof Treatment;
    }

    public boolean isTransplant(String option) {
        return this.isTreatment(option) &&
                ( (Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1)).getType()
                        .equals(TypeOfTreatment.TRANSPLANT);
    }

    public boolean isOrganThief(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1))
                        .getType().equals(TypeOfTreatment.ORGAN_THIEF);
    }

    public boolean isContagion(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1))
                        .getType().equals(TypeOfTreatment.CONTAGION);
    }

    public boolean isLatexGloves(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1))
                        .getType().equals(TypeOfTreatment.LATEX_GLOVES);
    }

    public boolean isMedicalError(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option)-1))
                        .getType().equals(TypeOfTreatment.MEDICAL_ERROR);
    }

    public boolean addOrganToBody(String option) {
        Player player = this.getPlayerByTurn();
        Organ organ = (Organ) player.getHand().listCard().get(Integer.parseInt(option)-1);
        return (player.getBody().addOrgan(organ)) && (player.getHand().operateHand(organ, false));
    }

    public boolean addMedicineToBody(String cardPosition, String typeOfOgan) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan type = list.get(Integer.parseInt(typeOfOgan)-1);
        Player player = this.getPlayerByTurn();
        Medicine medicine = (Medicine) player.getHand().listCard().get(Integer.parseInt(cardPosition)-1);
        return (player.getBody().addCard(medicine, type)) && (player.getHand().operateHand(medicine, false));
    }

    public boolean addVirusToBody(String cardPosition, String typeOfOrgan, int player) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan type = list.get(Integer.parseInt(typeOfOrgan)-1);
        Player playerTurn = this.getPlayerByTurn();
        Player playerToAttack = this.getPlayers().get(player);
        Virus virus = (Virus) playerTurn.getHand().listCard().get(Integer.parseInt(cardPosition)-1);
        return playerToAttack.getBody().addCard(virus, type) && playerTurn.getHand().operateHand(virus, false);
    }

    public void discardHandCard(String cardPosition) {
        Card card = this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(cardPosition)-1);
        this.getPlayerByTurn().getHand().operateHand(card, true);
    }

    public int getPlayerByName(String name) {
        int result = 0;
        int i = 0;
        for (Player player : this.getPlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                result = i;
            }
            i++;
        }
        return result;
    }
}
