import army.unit.Unit;
import board.Tile;
import filemanager.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        showMessage("Welcome to Stratego!");
        Game game = initializeGame();
        startPlacingPhase(game);
        startBattlePhase(game);
        showWinner(game);
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

    public static void showWinner(Game game) {
        showMessage("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", game.getWinningArmyColor());
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

    public static Game loadGame() throws StrategoException {
        showMessage("Enter filename: ");
        String fileName = input.nextLine();
        Game game;
        try {
            game = (Game) filemanager.FileManager.read(fileName);
        } catch (FileNotFoundException e) {
            throw new StrategoException("File not found");
        } catch (IOException e) {
            throw new StrategoException("Error parsing data from file");
        } catch (Exception e) {
            throw new StrategoException("Caught Exception, contact administrator for further information.");
        }
        if (game == null) {
            throw new StrategoException("File is empty");
        }
        return game;
    }

    public static Game initializeGame() {
        Game game = null;
        showMessage("Would you like to load a previous game? (y/n): ");
        String loadGameAnswer = input.nextLine();
        if (loadGameAnswer.equalsIgnoreCase("y")) {
            try {
                game = loadGame();
                showMessage("Game loaded");
            } catch (StrategoException e) {
                showMessage(e.getMessage());
            }
        } else {
            game = new Game();
            showMessage("New game created");
        }
        return game;
    }
    public static void startPlacingPhase(Game game) {
        showMessage("Placing phase");
        showMessage("Would you like to use a standard configuration for your army? (y/n): ");
        String loadArmyAnswer = input.nextLine();
        if (loadArmyAnswer.equalsIgnoreCase("y")) {
            try {
                game.loadArmyConfig();
            } catch (StrategoException e) {
                showMessage(e.getMessage());
            }
        }
        while (game.currentArmyHasUnitsToPlace()) {
            Unit selectedUnit = selectUnitToPlace(game.getCurrentArmyUnitsToPlace());
            int x = selectCoordinate("X");
            int y = selectCoordinate("Y");
            try {
                game.placeUnit(selectedUnit, x, y);
            } catch (StrategoException e) {
                showMessage(e.getMessage());
            }
            gameTick(game);
        }
        showMessage("All your units have been placed!");
        // place computer army
        gameTick(game);

    }

    public static void gameTick(Game game) {
        game.update();
        showDeadUnits(game.getDeadUnitsOfArmy(currentArmy));
        drawBoard(game.getGameField());
    }

    public static void startBattlePhase(Game game) {
        showMessage("Battle phase");
        while (!game.isOver()) {
            // loop until valid unit has been chosen
            boolean invalidUnitSelected = true;
            int xPos = 0;
            int yPos = 0;
            int xDes = 0;
            int yDes = 0;
            while (invalidUnitSelected) {
                showMessage("Enter the position of the unit you want to move");
                xPos = selectCoordinate("X");
                yPos = selectCoordinate("Y");
                if (game.armyHasUnitAtPosition(xPos, yPos)) {
                    invalidUnitSelected = false;
                    showMessage(game.getSelectedUnitInformation(xPos,yPos));
                    continue;
                }
                showMessage("You do not have a unit at this position!");
                // check if the player has a unit on this position
                // catch exception and print
            }
            // loop until valid move has been chosen
            boolean moveIsInvalid = true;
            while (moveIsInvalid) {
                showMessage("Enter the destination you want to move the unit to");
                xDes = selectCoordinate("X");
                yDes = selectCoordinate("Y");
                // check if the move is valid
                // catch exception and print
                try {
                    game.humanMoveUnit(xPos, yPos, xDes, yDes);
                    moveIsInvalid = false;
                } catch (StrategoException e) {
                    showMessage(e.getMessage());
                }
            }
            // process the human move and its result
            gameTick(game);
            // process computer move
            gameTick(game);
        }
    }

    public static void saveGame(String fileName,Game game) {
        FileManager.write(game, fileName);
    }


}
