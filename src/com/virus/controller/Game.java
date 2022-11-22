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

    private List<Player> getOtherPlayersByTurn() {
        List<Player> list = new ArrayList<>();
        for(Player player : this.players) {
            if(!player.equals(this.getPlayerByTurn())) {
                list.add(player);
            }
        }
        return list;
    }

    public String getPlayerNameByTurn() { return this.players.get(turn).getName(); }

    public boolean existWinner() {
        List<TypeOfOrgan> type = TypeOfOrgan.getListType();
        Body body = this.getPlayerByTurn().getBody();
        int healthyOrgan = 0;
        for(int i=0; i<type.size(); i++) {
            if(body.isHealthy(type.get(i))) {
                healthyOrgan++;
            }
        }
        if(healthyOrgan>3) {
            this.winner = this.getPlayerByTurn();
            return true;
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
                result += element;
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
        for (Player player : this.getPlayers()) {
            if (!player.equals(actual)) {
                bodyPlayers = bodyPlayers + String.format(title, player.getName()) + "\n"
                        + this.map(player.getBody().getStateMatrix()) + "\n";
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

    public int getNumberOfCards() {
        return this.getPlayerByTurn().getHand().getState().size();
    }

    // Recibe una lista de Typos haciendo referencia a cual pila del cuerpo se puede jugar la carta seleccionada
    public String mapOptions(String title) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        String result = "";
        for(int i=0; i<list.size(); i++) {
            result += i+1 + String.format(title, TypeOfOrgan.getNameType(list.get(i))) + "\n";
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
        return (player.getBody().addOrgan(organ)) && (player.getHand().operateHand(organ, false)); //
    }

    public boolean addMedicineToBody(String cardPosition, String typeOfOrgan) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan type = list.get(Integer.parseInt(typeOfOrgan)-1);
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

    public boolean playTransplant(String cardPosition, String organToSwap, String selectedPlayer, String playerOrganToSwap) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan type = list.get(Integer.parseInt(organToSwap)-1);
        TypeOfOrgan otherPlayerType = list.get(Integer.parseInt(playerOrganToSwap)-1);
        Player playerToSwap = this.getPlayers().get(this.getPlayerByName(selectedPlayer));

        if(this.getPlayerByTurn().getBody().playTransplantCard(type, playerToSwap, otherPlayerType)) {
            this.discardHandCard(cardPosition);
            return true;
        }
        return false;
    }

    public boolean playOrganThief(String cardPosition, String selectedPlayer, String playerOrganToSteal) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan otherPlayerType = list.get(Integer.parseInt(playerOrganToSteal)-1);
        Player playerToSteal = this.getPlayers().get(this.getPlayerByName(selectedPlayer));

        if(this.getPlayerByTurn().getBody().playOrganThiefCard(playerToSteal, otherPlayerType)) {
            this.discardHandCard(cardPosition);
            return true;
        }
        return false;
    }

    public boolean playContagion(String selectedVirus, String playerToInfect, String organToInfect) {
        List<TypeOfOrgan> list = TypeOfOrgan.getListType();
        TypeOfOrgan virusType = list.get(Integer.parseInt(selectedVirus)-1);
        Player player = this.getPlayers().get(this.getPlayerByName(playerToInfect));
        TypeOfOrgan organType = list.get(Integer.parseInt(organToInfect)-1);

        return this.getPlayerByTurn().getBody().playContagionCard(virusType, player, organType);
    }

    public boolean canInfect() {
        Player playerInTurn = this.getPlayerByTurn();
        List<TypeOfOrgan> typeOfOrgans = TypeOfOrgan.getListType();
        for(Player player : players) {
            if(!player.equals(playerInTurn)) {
                for(TypeOfOrgan type : typeOfOrgans) {
                    if (playerInTurn.getBody().canPlayContagionInBody(type, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void playLatexGloves(String cardPosition) {
        for(Player player : players) {
            if(!player.equals(this.getPlayerByTurn())) {
                player.getHand().discardHand();
            }
        }
        this.discardHandCard(cardPosition);
    }

    public void playMedicalError(String cardPosition, String playerToSwapBody) {
        this.getPlayerByTurn().playMedicalErrorCard(this.players.get(this.getPlayerByName(playerToSwapBody)));
        this.discardHandCard(cardPosition);
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
