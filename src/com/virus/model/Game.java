package com.virus.model;

/* Los metodos estaticos se pueden acceder de manera: "Clase. ...."--> se pueden acceder haicendo una instancia */
/* ArrayList es una clase, List es una interface */
/* BUSCAR: pricipio de superposicion de Liskov*/

import com.virus.model.card.*;
import com.virus.model.enums.Event;
import com.virus.model.enums.TypeOfOrgan;
import com.virus.model.enums.TypeOfTreatment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Game implements Observer {

    private static Game INSTANCE;
    private final List<Player> players;
    private final Deck deck;
    private final DiscardPile discardPile;
    private int turn;
    private Player winner;

    private Game() {
        this.players = new ArrayList<>();
        deck = Deck.getInstance();
        deck.mixTheCards();
        discardPile = DiscardPile.getInstance();
        turn = 0;
    }

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(String name) {
        players.add(new Player(name.trim()));
    }

    public boolean deletePlayer(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name.trim())) {
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
        StringBuilder stringBuilder = new StringBuilder();
        for (Player player : players) {
            stringBuilder.append("\n");
            stringBuilder.append(player.getName());
        }
        return stringBuilder.substring(1); //Elimina el primer caracter
    }

    /**
     * Precondicion: validar la cantidad de jugadores
     */
    public void initialize() {  // asigno la mano a los jugadores

        for (Player player : players) {
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
        for (Player player : this.players) {
            if (!player.equals(this.getPlayerByTurn())) {
                list.add(player);
            }
        }
        return list;
    }

    public String getPlayerNameByTurn() {
        return this.players.get(turn).getName();
    }

    public boolean existWinner() {
        TypeOfOrgan[] list = TypeOfOrgan.values();
        Body body = this.getPlayerByTurn().getBody();
        int healthyOrgan = 0;
        for (TypeOfOrgan type : list) {
            if (body.isHealthy(type)) {
                healthyOrgan++;
            }
        }
        if (healthyOrgan > 3) {
            this.winner = this.getPlayerByTurn();
            return true;
        }
        return false;
    }

    public String getWinnerName() {
        return this.winner.getName();
    }

    public boolean actualHandPlayerIsEmpty() {
        return this.getPlayerByTurn().getHand().isHandEmpty();
    }

    public void actualHandPlayerRefill() {
        this.getPlayerByTurn().getHand().refillHand();
    }

    // map es programacion es una funcion que convierte una cosa en otra
    private String map(List<List<String>> matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> row : matrix) {
            for (String element : row) {
                stringBuilder.append(element);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String mapPlayerInTurn() {
        List<List<String>> matrix = this.getPlayerByTurn().getBody().getStateMatrix();
        return this.map(matrix);
    }

    public String otherPlayersBody(String title) {
        Player actual = this.getPlayerByTurn();
        StringBuilder bodyPlayers = new StringBuilder();
        for (Player player : this.getPlayers()) {
            if (!player.equals(actual)) {
                bodyPlayers.append(String.format(title, player.getName()));
                bodyPlayers.append("\n");
                bodyPlayers.append(this.map(player.getBody().getStateMatrix()));
                bodyPlayers.append("\n");
            }
        }
        return bodyPlayers.toString();
    }

    public String mapCard() {
        List<String> list = this.getPlayerByTurn().getHand().getState();
        StringBuilder result = new StringBuilder();
        for (String card : list) {
            result.append(card);
            result.append("\n");
        }
        return result.toString();
    }

    public int getNumberOfCards() {
        return this.getPlayerByTurn().getHand().getState().size();
    }

    // Recibe una lista de Typos haciendo referencia a cada organo del cuerpo
    public String mapOptions(String title) {
        TypeOfOrgan[] list = TypeOfOrgan.values();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            result.append(i+1);
            result.append(String.format(title, list[i].getSpanishName()));
            result.append("\n");
        }
        return result.toString();
    }

    public boolean isNormalCard(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1) instanceof NormalCard;
    }

    public boolean isOrgan(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1) instanceof Organ;
    }

    public boolean isMedicine(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1) instanceof Medicine;
    }

    private boolean isTreatment(String option) {
        return this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1) instanceof Treatment;
    }

    public boolean isTransplant(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1)).getType()
                        .equals(TypeOfTreatment.TRANSPLANT);
    }

    public boolean isOrganThief(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1))
                        .getType().equals(TypeOfTreatment.ORGAN_THIEF);
    }

    public boolean isContagion(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1))
                        .getType().equals(TypeOfTreatment.CONTAGION);
    }

    public boolean isLatexGloves(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1))
                        .getType().equals(TypeOfTreatment.LATEX_GLOVES);
    }

    public boolean isMedicalError(String option) {
        return this.isTreatment(option) &&
                ((Treatment) this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(option) - 1))
                        .getType().equals(TypeOfTreatment.MEDICAL_ERROR);
    }

    private TypeOfOrgan getTypeOfOrgan(String type) {
        TypeOfOrgan[] list = TypeOfOrgan.values();
        return list[Integer.parseInt(type) - 1];
    }

    public boolean addOrganToBody(String option) {
        Player player = this.getPlayerByTurn();
        Organ organ = (Organ) player.getHand().listCard().get(Integer.parseInt(option) - 1);
        return (player.getBody().addOrgan(organ)) && (player.getHand().operateHand(organ));
    }

    public boolean addMedicineToBody(String cardPosition, String typeOfOrgan) {
        TypeOfOrgan type = this.getTypeOfOrgan(typeOfOrgan);
        Player player = this.getPlayerByTurn();
        Medicine medicine = (Medicine) player.getHand().listCard().get(Integer.parseInt(cardPosition) - 1);
        return (player.getBody().addMedicineCard(medicine, type, this.discardPile)) && (player.getHand().operateHand(medicine));
    }

    public boolean addVirusToBody(String cardPosition, String typeOfOrgan, int player) {
        TypeOfOrgan type = this.getTypeOfOrgan(typeOfOrgan);
        Player playerTurn = this.getPlayerByTurn();
        Player playerToAttack = this.getPlayers().get(player);
        Virus virus = (Virus) playerTurn.getHand().listCard().get(Integer.parseInt(cardPosition) - 1);
        return playerToAttack.getBody().addVirusCard(virus, type, this.discardPile) && playerTurn.getHand().operateHand(virus);
    }

    public void discardHandCard(String cardPosition) {
        Card card = this.getPlayerByTurn().getHand().listCard().get(Integer.parseInt(cardPosition) - 1);
        this.getPlayerByTurn().getHand().operateHand(card, this.discardPile);
    }

    public boolean playTransplant(String cardPosition, String organToSwap, String selectedPlayer, String playerOrganToSwap) {
        TypeOfOrgan type = this.getTypeOfOrgan(organToSwap);
        TypeOfOrgan otherPlayerType = this.getTypeOfOrgan(playerOrganToSwap);
        Player playerToSwap = this.getPlayers().get(this.getPositionPlayerByName(selectedPlayer));

        if (this.getPlayerByTurn().getBody().playTransplantCard(type, playerToSwap, otherPlayerType)) {
            this.discardHandCard(cardPosition);
            return true;
        }
        return false;
    }

    public boolean playOrganThief(String cardPosition, String selectedPlayer, String playerOrganToSteal) {
        TypeOfOrgan otherPlayerType = this.getTypeOfOrgan(playerOrganToSteal);
        Player playerToSteal = this.getPlayers().get(this.getPositionPlayerByName(selectedPlayer));

        if (this.getPlayerByTurn().getBody().playOrganThiefCard(playerToSteal, otherPlayerType)) {
            this.discardHandCard(cardPosition);
            return true;
        }
        return false;
    }

    public boolean playContagion(String selectedVirus, String playerToInfect, String organToInfect) {
        TypeOfOrgan[] list = TypeOfOrgan.values();
        TypeOfOrgan virusType = list[Integer.parseInt(selectedVirus) - 1];
        Player player = this.getPlayers().get(this.getPositionPlayerByName(playerToInfect));
        TypeOfOrgan organType = list[Integer.parseInt(organToInfect) - 1];

        return this.getPlayerByTurn().getBody().playContagionCard(virusType, player, organType, this.discardPile);
    }

    public boolean canInfect() {
        Player playerInTurn = this.getPlayerByTurn();
        TypeOfOrgan[] typeOfOrgans = TypeOfOrgan.values();
        for (Player player : players) {
            if (!player.equals(playerInTurn)) {
                for (TypeOfOrgan type : typeOfOrgans) {
                    if (playerInTurn.getBody().canPlayContagionInBody(type, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void playLatexGloves(String cardPosition) {
        for (Player player : players) {
            if (!player.equals(this.getPlayerByTurn())) {
                player.getHand().discardHand();
            }
        }
        this.discardHandCard(cardPosition);
    }

    public void playMedicalError(String cardPosition, String playerToSwapBody) {
        this.getPlayerByTurn().playMedicalErrorCard(this.players.get(this.getPositionPlayerByName(playerToSwapBody)));
        this.discardHandCard(cardPosition);
    }

    public int getPositionPlayerByName(String name) {
        return this.getPlayers().indexOf(new Player(name));
    }

    public boolean existPlayer(String name) {
        for (Player player : this.getPlayers()) {
            if (player.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean notPlayerInTurn(String name) {
        return this.getPlayerByTurn().getName().equalsIgnoreCase(name.trim());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Event) {
            switch ((Event) arg) {
                case EMPTY_DECK:
                    while (!this.discardPile.empty()) {
                        this.deck.addCard(this.discardPile.getDiscardPileCard());
                    }
                    this.deck.mixTheCards();
                    break;
                default:
                    break;
            }
        }
    }
}
