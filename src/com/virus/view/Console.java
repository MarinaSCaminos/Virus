package com.virus.view;

import com.virus.model.Game;

import java.util.Scanner;

public class Console { //TODO singleton console

    private final Game game;

    public Console() {
        this.game = Game.getInstance();
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
                System.out.println(game.getListOfPlayers());
            } else {
                break;
            }
        } while (true);

        game.initialize();

        this.start();
    }

    private void start() {
        boolean existWinner = false;
        do {
            System.out.println("Le toca al jugador " + game.getPlayerNameByTurn() + "\n");

            if (game.actualHandPlayerIsEmpty()) {
                System.out.println("Tu mano esta vacia, se rellenara y continuara el siguient jugador");
                this.preparedNextTurn();
                continue;
            }

            System.out.println(game.otherPlayersBody("Cuerpo de jugador %s"));
            System.out.println("Tu cuerpo: ");
            System.out.println(game.mapPlayerInTurn());
            System.out.println("Tu mano: ");
            System.out.println(game.mapCard());

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
                        System.out.println(game.mapCard());
                        option = scanner.nextLine();

                        if (option.equals("1") || option.equals("2") || option.equals("3")) {
                            // Si es Organo, Virus o Medicina
                            if (game.isNormalCard(option)) { // NO es necesaria

                                if (game.isOrgan(option)) {
                                    if (game.addOrganToBody(option)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se puede agregar el órgano. Se recomienda descartar la carta");
                                    }

                                } else if (game.isMedicine(option)) {  // Es de tipo Medicina
                                    System.out.println("Elija en donde jugar la carta seleccionada: ");
                                    System.out.println(game.mapOptions(". Usar en la pila de %s")); // Da la lista de tipos del cuerpo para elegir
                                    option2 = scanner.nextLine();

                                    if (option2.equals("1") || option2.equals("2") || option2.equals("3") || option2.equals("4") || option2.equals("5")) {
                                        if (game.addMedicineToBody(option, option2)) {
                                            playedOnce = true;
                                            executedAnOption = true;
                                        }
                                    }
                                } else { //Es de tipo Virus
                                    System.out.println(game.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
                                    System.out.println("Eliga a que jugador mandar el virus: ");
                                    option2 = scanner.nextLine();

                                    System.out.println("Eliga en que pila jugar el virus: ");
                                    System.out.println(game.mapOptions(". Usar en la pila de %s"));
                                    option3 = scanner.nextLine();

                                    if (game.addVirusToBody(option, option3, game.getPlayerByName(option2))) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    }
                                }
                            } else { // Si es Tratamiento
                                String option4;

                                if (game.isTransplant(option)) {
                                    System.out.println("Elija que organo de su cuerpo intercambiar: ");
                                    System.out.println(game.mapOptions(". Intercambiar la pila de %s"));
                                    option2 = scanner.nextLine();

                                    System.out.println("Elija a cual jugador intercambiar: ");
                                    System.out.println(game.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
                                    option3 = scanner.nextLine();


                                    System.out.println("Elija por cual organo del jugador contrario intercambiar: ");
                                    System.out.println(game.mapOptions(". Intercambiar la pila de %s"));
                                    option4 = scanner.nextLine();

                                    //option2: stackToSwap , option3: selectedPlayer , option4: playerStackToSwap
                                    if (game.playTransplant(option, option2, option3, option4)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se pudo intercambiar los organos.");
                                        // Dar un menu para que intente con otrova a inicio
                                    }


                                } else if (game.isOrganThief(option)) {
                                    System.out.println("Elija a cual jugador robar: ");
                                    System.out.println(game.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
                                    option2 = scanner.nextLine();


                                    System.out.println("Elija cual organo del jugador contrario robar: ");
                                    System.out.println(game.mapOptions(". Usar en la pila de %s"));
                                    option3 = scanner.nextLine();

                                    // option2: jugador seleccionado , option3: cuerpo a robar
                                    if (!game.notPlayerInTurn(option2) && game.playOrganThief(option, option2, option3)) {
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("No se pudo robar el organo.");
                                        // Dar un menu para que intente con otrova a inicio
                                    }
                                } else if (game.isContagion(option)) {
                                    if (game.canInfect()) {
                                        game.discardHandCard(option);
                                        while (game.canInfect()) {
                                            // Poner en un while hasta que no pueda pasar mas virus

                                            System.out.println("Elija el virus del organo que quieres transferir: ");
                                            System.out.println(game.mapOptions(". Elegir virus del organo %s"));
                                            option2 = scanner.nextLine();


                                            System.out.println("Ingrese el nombre del jugador al cual pasarle el virus : ");
                                            System.out.println(game.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
                                            option3 = scanner.nextLine();


                                            System.out.println("Elija a cual organo infectar del jugador seleccionado: ");
                                            System.out.println(game.mapOptions(". Infectar %s"));
                                            option4 = scanner.nextLine();

                                            if (!game.playContagion(option2, option3, option4)) {
                                                System.out.println("No puedes contagiar el organo seleccionado. Intende con otro.");
                                            } else {
                                                System.out.println("Se pudo infectar correctamente. " +
                                                        "Se pedira nuevamente otro virus y cuerpo a contagiar en case de que este la posibilidad.");
                                            }
                                        }
                                        playedOnce = true;
                                        executedAnOption = true;
                                    } else {
                                        System.out.println("Cuidado, no se puede jugar esta carta!");
                                    }
                                } else if (game.isLatexGloves(option)) {
                                    game.playLatexGloves(option);
                                    playedOnce = true;
                                    executedAnOption = true;

                                } else if (game.isMedicalError(option)) {
                                    System.out.println("Elija a cual jugador intercambiarle el cuerpo: ");
                                    System.out.println(game.otherPlayersBody("Cuerpo de jugador %s")); // Funcion que me muestra el cuerpo de los demas jugadores
                                    option2 = scanner.nextLine();

                                    game.playMedicalError(option, option2);
                                    playedOnce = true;
                                    executedAnOption = true;

                                }
                            }

                        } else { // No eligio una carta de la mano, dato mal ingresado
                            System.out.println("El dato ingresado no es una opcion valida."); // TODO crear un while
                        }
                    } else {
                        System.out.println("No puedes jugar mas de una carta a la vez.");
                    }

                } else if (option.equals("2")) {      // DESCARTAR
                    System.out.println("Elija cual carta descartar:");
                    System.out.println(game.mapCard());
                    option = scanner.nextLine();
                    if (Integer.parseInt(option) <= game.getNumberOfCards()) { // Menos que la cantidad de cartas de Mano
                        game.discardHandCard(option);
                        executedAnOption = true;
                        System.out.println(game.mapCard());
                    } else {
                        System.out.println("El dato ingresado no es una opcion valida.");  // TODO crear un while
                    }

                } else if (option.equals("3")) {    // FINALIZAR TURNO
                    if (executedAnOption) {
                        // llene mano jugador
                        break;
                    } else {
                        System.out.println("Debes jugar o descartar al menos una carta.");
                    }
                }
            } while (!game.actualHandPlayerIsEmpty() && !game.existWinner());

            if (game.existWinner()) {
                existWinner = true;
            } else {
                this.preparedNextTurn();
            }

        } while (!existWinner);

        end();
    }

    private void end() {
        System.out.print("El ganador es: " + game.geWinnerName());
    }

    private void requestPlayer() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isBlank() && !game.existPlayer(name)) {
                break;
            }
            System.out.println("Por favor, ingrese un nombre valido y no repetido: ");
        } while (true);
        game.addPlayer(name);
    }

    private boolean requestPlayerToDelete() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isBlank() && game.existPlayer(name)) {   //TODO agregar condicion de nombres distintos
                break;
            }
            System.out.print("Por favor, ingrese un nombre valido para eliminar: ");
        } while (true);
        return game.deletePlayer(name);
    }

    public void preparedNextTurn() {
        game.actualHandPlayerReFill();

        System.out.print("Presione Enter para continuar con el siguiente jugador.");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        while (!option.equals("")) { // En vez de "\n" es una string vacia
            System.out.print("Presione Enter para continuar con el siguiente jugador.");
            option = scanner.nextLine();
        }

        game.nextTurn();
    }

}

/* TODO
        1. Se debe poder cancelar la operacion de eliminar jugador
        2. Se debe controlar la cantidad de jugadores maximo
        3. Posible mejora: Tener dos menu, uno que no tenga la option
           de agregar jugador cuando alcance el maximo de jugadores.
        4. Arreglar el metodo que muestra los cuerpos de los jugadores (map)
 */