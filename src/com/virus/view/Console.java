package com.virus.view;

import com.virus.controller.Controller;

import java.util.Scanner;

public class Console {

    private static Console INSTANCE;
    private final Controller controller;

    private Console() {
        this.controller = Controller.getInstance();
    }

    public static Console getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Console();
        }
        return INSTANCE;
    }

    public void play() {
        System.out.println("Bienvenido a Virus. \nPor favor ingrese los jugadodes primeros 2 jugadores.");
        System.out.print("Ingrese el nombre del primer jugador: ");
        requestPlayer();
        System.out.print("Ingrese el nombre del segundo jugador: ");
        requestPlayer();

        Scanner scanner = new Scanner(System.in);
        String option;
        byte players = 2;

        do {
            do {
                System.out.println("""

                        1. Agregar un judador (maximo 6 jugadores)\s
                        2. Eliminar un jugador\s
                        3. Mostrar jugadores\s
                        4. Empezar juego""");
                option = scanner.nextLine();
            } while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"));

            if (option.equals("1")) {
                if (players < 6) {
                    System.out.print("Ingrese el nombre del nuevo jugador: ");
                    requestPlayer();
                    players++;
                } else {
                    System.out.print("No se pueden agregar mas jugadores");
                }
            } else if (option.equals("2")) {
                System.out.print("Ingrese el nombre del jugador que quiere eliminar: ");
                if (!requestPlayerToDelete()) {
                    System.out.println("No existe el jugador.");
                } else {
                    players--;
                }
            } else if (option.equals("3")) {
                System.out.println("Los jugadores son: ");
                System.out.println(controller.getListOfPlayers());
            } else {
                break;
            }
        } while (true);

        controller.initialize();

        this.start();
    }

    private void start() {
        boolean existWinner = false;
        do {
            System.out.println("Le toca al jugador " + controller.getPlayerNameByTurn() + "\n");

            if (controller.actualHandPlayerIsEmpty()) {
                System.out.println("Tu mano esta vacia, se rellenara y continuara el siguient jugador");
                this.preparedNextTurn();
                continue;
            }

            System.out.println(controller.otherPlayersBody("Cuerpo de jugador %s"));
            System.out.println("Tu cuerpo: ");
            System.out.println(controller.mapPlayerInTurn());
            System.out.println("Tu mano: ");
            System.out.println(controller.mapCard());

            boolean playedOnce = false;
            boolean executedAnOption = false;
            do {
                Scanner scanner = new Scanner(System.in);
                String option;
                String option2;
                String option3;

                System.out.println("1. Jugar una carta. \n2. Descartar una carta. \n3. Terminar turno");
                option = scanner.nextLine();

                // TODO hacer un while por cada ingreso de dato, y verfique que el nombre del jugador ingreaso no sea el del turno
                // TODO No mostrar la opcion de jugar en caso de que no se pueda ¿?
                if (option.equals("1")) {
                    if (!playedOnce) {
                        System.out.println("Elija cual carta jugar:");
                        System.out.println(controller.mapCard());
                        option = scanner.nextLine();

                        if (option.equals("1") || option.equals("2") || option.equals("3")) {
                            // Si es Organo, Virus o Medicina
                            if (controller.isNormalCard(option)) { // No es necesaria

                                if (controller.isOrgan(option)) {
                                    if (controller.addOrganToBody(option)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se puede agregar el órgano.");
                                    }

                                } else if (controller.isMedicine(option)) {  // Es de tipo Medicina

                                    option2 = this.organList("Elija en donde jugar la carta seleccionada: ", ". Usar en el organo de %s");

                                    if (controller.addMedicineToBody(option, option2)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se pudo jugar la carta");
                                    }

                                } else { //Es de tipo Virus

                                    option2 = this.playerAndBodyList("Eliga a cual jugador mandar el virus: ");
                                    option3 = this.organList("Eliga en que organo jugar el virus: ", ". Usar en el organo de %s");

                                    if (controller.addVirusToBody(option, option3, controller.getPositionPlayerByName(option2))) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    }else{
                                        System.out.println("No se pudo jugar la carta");
                                    }
                                }

                            } else { // Si es Tratamiento
                                String option4;

                                if (controller.isTransplant(option)) {

                                    option2 = this.organList("Elija que organo de su cuerpo intercambiar: ", ". Intercambiar el organo de %s");
                                    option3 = this.playerAndBodyList("Elija a cual jugador intercambiar: ");
                                    option4 = this.organList("Elija por cual organo del jugador contrario intercambiar: ", ". Intercambiar el organo de %s");

                                    //option2: stackToSwap , option3: selectedPlayer , option4: playerStackToSwap
                                    if (controller.playTransplant(option, option2, option3, option4)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se pudo intercambiar los organos.");
                                    }


                                } else if (controller.isOrganThief(option)) {

                                    option2 = this.playerAndBodyList("Elija a cual jugador robar: ");
                                    option3 = this.organList("Elija cual organo del jugador contrario robar: ", ". Usar en el organo de %s");

                                    // option2: jugador seleccionado , option3: cuerpo a robar
                                    if (!controller.notPlayerInTurn(option2) && controller.playOrganThief(option, option2, option3)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se pudo robar el organo.");
                                    }

                                } else if (controller.isContagion(option)) {

                                    if (controller.canInfect()) {
                                        controller.discardHandCard(option);
                                        while (controller.canInfect()) {
                                            option2 = this.organList("Elija el virus del organo que quieres transferir: ", ". Elegir virus del organo %s");
                                            option3 = this.playerAndBodyList("Ingrese el nombre del jugador al cual pasarle el virus : ");
                                            option4 = this.organList("Elija el organo a infectar del jugador seleccionado: ", ". Infectar %s");

                                            if (!controller.playContagion(option2, option3, option4)) {
                                                System.out.println("No puedes contagiar el organo seleccionado. Intende con otro.");
                                            } else {
                                                System.out.println("Se pudo infectar correctamente. " +
                                                        "Se pedira nuevamente otro virus y cuerpo a contagiar en caso de que este la posibilidad.");
                                            }
                                        }
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("Cuidado, no se puede jugar esta carta!");
                                    }

                                } else if (controller.isLatexGloves(option)) {
                                    controller.playLatexGloves(option);
                                    playedOnce = true;
                                    executedAnOption = true;

                                } else if (controller.isMedicalError(option)) {
                                    option2 = this.playerAndBodyList("Elija a cual jugador intercambiarle el cuerpo: ");

                                    controller.playMedicalError(option, option2);
                                    playedOnce = true;
                                    executedAnOption = true;

                                }
                            }

                        } else { // No eligio una carta de la mano, dato mal ingresado
                            System.out.println("El dato ingresado no es una opcion valida.");
                        }
                    } else {
                        System.out.println("No puedes jugar mas de una carta a la vez.");
                    }

                } else if (option.equals("2")) {      // DESCARTAR
                    System.out.println("Elija cual carta descartar:");
                    System.out.println(controller.mapCard());
                    option = scanner.nextLine();
                    if (Integer.parseInt(option) <= controller.getNumberOfCards()) { // Menos que la cantidad de cartas de Mano
                        controller.discardHandCard(option);
                        executedAnOption = true;
                        System.out.println(controller.mapCard());
                    } else {
                        System.out.println("El dato ingresado no es una opcion valida.");
                    }

                } else if (option.equals("3")) {    // FINALIZAR TURNO
                    if (executedAnOption) {
                        // llene mano jugador
                        break;
                    } else {
                        System.out.println("Debes jugar o descartar al menos una carta.");
                    }
                }
            } while (!controller.actualHandPlayerIsEmpty() && !controller.existWinner());

            if (controller.existWinner()) {
                existWinner = true;
            } else {
                this.preparedNextTurn();
            }

        } while (!existWinner);

        end();
    }

    private void end() {
        System.out.print("El ganador es: " + controller.getWinnerName());
    }

    private void requestPlayer() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isBlank() && !controller.existPlayer(name)) {
                break;
            }
            System.out.println("Por favor, ingrese un nombre valido y no repetido: ");
        } while (true);
        controller.addPlayer(name);
    }

    private boolean requestPlayerToDelete() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isBlank() && controller.existPlayer(name)) {
                break;
            }
            System.out.print("Por favor, ingrese un nombre valido para eliminar: ");
        } while (true);
        return controller.deletePlayer(name);
    }

    public void preparedNextTurn() {
        controller.actualHandPlayerRefill();

        System.out.print("Presione Enter para continuar con el siguiente jugador.");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        while (!option.equals("")) { // En vez de "\n" es una string vacia
            System.out.print("Presione Enter para continuar con el siguiente jugador.");
            option = scanner.nextLine();
        }

        controller.nextTurn();
    }

    private String organList(String title1, String title2) {
        Scanner scanner = new Scanner(System.in);
        String option;
        do {
            System.out.println(title1);
            System.out.println(controller.mapOptions(title2));
            option = scanner.nextLine();
            if (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5")) {
                System.out.println("Eliga el organo correctamente por favor.");
            }
        } while (!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5"));
        return option;
    }

    private String playerAndBodyList(String title1) {
        Scanner scanner = new Scanner(System.in);
        String option;
        do {
            System.out.println(controller.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
            System.out.println(title1);
            option = scanner.nextLine();
            if (!controller.existPlayer(option)) {
                System.out.println("Ingrese el nombre de jugador correctamente por favor.");
            }
        } while (!controller.existPlayer(option));
        return option;
    }

}

/* TODO
        1. Se debe poder cancelar la operacion de eliminar jugador
        2. Se debe controlar la cantidad de jugadores maximo
        3. Posible mejora: Tener dos menu, uno que no tenga la option
           de agregar jugador cuando alcance el maximo de jugadores.
        4. Arreglar el metodo que muestra los cuerpos de los jugadores (map)
 */