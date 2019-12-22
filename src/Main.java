import army.Army;
import army.unit.Unit;
import board.Tile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        showMessage("Welcome to Stratego!");
        showMessage("Would you like to load a previous game? (y/n): ");
        String loadGameAnswer = input.nextLine();
        if (loadGameAnswer.equalsIgnoreCase("y")) {
            showMessage("Enter filename: ");
            String filename = input.nextLine();
            try {
                game = loadGame(filename);
                showMessage("Game loaded");
            } catch (StrategoException e) {
                showMessage(e.getMessage());
                showMessage("Unable to load game");
            }
        }
        showMessage("New game created");
        showMessage("Placing phase");
        showMessage("Would you like to use a standard army configuration? (y/n)");
        String loadArmyAnswer = input.nextLine();
        if (loadArmyAnswer.equalsIgnoreCase("y")) {
            try {
                game.loadArmyConfig();
                showMessage("Army loaded");
            } catch (StrategoException e) {
                showMessage(e.getMessage());
                showMessage("Unable to load army");
            }
        } else {
            // While loop to place Player's army
            while (game.currentArmyHasUnitsToPlace()) {
                Unit selectedUnit = selectUnitToPlace(game.getCurrentArmyUnitsToPlace());
                int x = selectCoordinate("X");
                int y = selectCoordinate("Y");
                game.placeUnit(selectedUnit, x, y);
                // catch error if starting position is unavailable
                game.update();
                drawBoard(game.getGameField());
            }
        }
        showMessage("All your units have been placed!");
        // place computer army
        game.update();
        drawBoard(game.getGameField());
        // transition to play phase
        showMessage("Battle phase");
        // loop the battle phase until one of the teams is defeated
        while (!game.isOver()) {
            // loop until valid unit has been chosen
            while()
            showMessage("Enter the position of the unit you want to move");
            int xPos = selectCoordinate("X");
            int yPos = selectCoordinate("Y");
            // check if the player has a unit on this position
            // catch exception and print
            showMessage("You do not have a unit at this position!");
            // loop until valid move has been chosen
            while()
            showMessage("Enter the destination you want to move the unit to");
            int xDes = selectCoordinate("X");
            int yDes = selectCoordinate("Y");
            // check if the move is valid
            // catch exception and print
            showMessage("Invalid move!");
            // process the human move and its result
            game.update();
            drawBoard(game.getGameField());
            // process computer move
            game.update();
            drawBoard(game.getGameField());
        }
        showMessage("The game has finished!");
        // show winner
        showMessage("Thank you for playing!");
    }

    static Scanner input = new Scanner(System.in);

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void showUnitsToPlace(List<Unit> unitsToPlace) {
        showMessage("Index. - Name - (Strength)");
        for (int i = 0; i < unitsToPlace.size(); i++) {
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public static void showWinner(Army army) {
        showMessage("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", army.getColor());
    }

    public static void drawBoard(Tile[][] gameField) {
        System.out.print(" - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(" | ");
                System.out.print(gameField[x][y]);
            }
            System.out.print(" |");
            System.out.println();
            System.out.print(" - - - - - - - - - - - - - - - - - - - - - ");
            System.out.println();
        }
    }

    public static void showDeadUnits(List<Unit> deadUnits) {
        System.out.println(deadUnits);
    }

    public static Unit selectUnitToPlace(List<Unit> unitsToPlace) {
        showMessage("Pick a unit to place!");
        showUnitsToPlace(unitsToPlace);
        showMessage("Enter index: ");
        boolean indexIsInvalid = true;
        int index = 0;
        String answer = input.nextLine();
        while (indexIsInvalid) {
            try {
                index = Integer.parseInt(answer);
                if (index < 0 || index > unitsToPlace.size()) {
                    throw new IndexOutOfBoundsException();
                }
                indexIsInvalid = false;
            } catch (NumberFormatException e) {
                System.out.println("Not a number, try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index, try again");
            }
        }
        return unitsToPlace.get(index);
    }

    public static int selectCoordinate(String axis) {
        showMessage("Enter " + axis + " coordinate: ");
        String answer = input.nextLine();
        boolean coordinateIsInvalid = true;
        int coordinate = 0;
        while (coordinateIsInvalid)
            try {
                coordinate = Integer.parseInt(answer);
                coordinateIsInvalid = false;
            } catch (NumberFormatException e) {
                System.out.println("Not a number, try again!");
            }
        return coordinate;
    }

    public static Game loadGame(String fileName) throws StrategoException {
        Game game;
        try {
            game = (Game) filemanager.FileManager.read(fileName);
        } catch (FileNotFoundException e) {
            throw new StrategoException("Caught FileNotFoundException");
        } catch (IOException e) {
            throw new StrategoException("Caught IOException");
        } catch (Exception e) {
            throw new StrategoException("Caught Exception");
        }
        if (game == null) {
            throw new StrategoException("File is empty");
        }
        return game;
    }
}
