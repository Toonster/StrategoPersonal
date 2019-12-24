import army.ArmyColor;
import army.unit.Unit;
import board.Tile;
import common.Position;
import filemanager.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Main {

    enum CoordinateAxis {
        X, Y
    }

    public static void main(String[] args) {
        showMessage("Welcome to Stratego!");
        Game game = initializeGame();
        startPlacingPhase(game);
        startBattlePhase(game);
        showWinner(game);
        showMessage("Thank you for playing!");
    }

    static Scanner input = new Scanner(System.in);
    static Random rand = new Random();

    public static void showMessage(String message) {
        System.out.println(message);
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

    public static void startPlacingPhase(Game game) {
        showMessage("Placing phase");
        showMessage("Would you like to use a standard configuration for your army? (y/n): ");
        String loadArmyAnswer = input.nextLine();
        if (loadArmyAnswer.equalsIgnoreCase("y")) {
            loadStandardArmyConfig(game);
        }
        gameTick(game);
        while (game.currentArmyHasUnitsToPlace()) {
            Unit selectedUnit = selectUnitToPlace(game.getArmyUnitsToPlace());
            Position unitDestination = askPosition();
            try {
                game.placeUnit(selectedUnit, unitDestination);
                gameTick(game);
            } catch (StrategoException e) {
                showMessage(e.getMessage());
            }
        }
        showMessage("All your units have been placed!");
        game.swapTurns();
        game.computerPlaceArmy();
        game.swapTurns();
        gameTick(game);
        showMessage("Placing phase done!");
    }

    public static void loadStandardArmyConfig(Game game) {
        try {
            game.loadArmyConfig();
        } catch (StrategoException e) {
            showMessage(e.getMessage());
        }
    }

    public static Unit selectUnitToPlace(List<Unit> unitsToPlace) {
        showMessage("Pick a unit to place!");
        boolean indexIsInvalid = true;
        int index = 0;
        while (indexIsInvalid) {
            try {
                showUnitsToPlace(unitsToPlace);
                showMessage("Enter index: ");
                String answer = input.nextLine();
                index = Integer.parseInt(answer);
                if (index < 0 || index >= unitsToPlace.size()) {
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

    public static void showUnitsToPlace(List<Unit> unitsToPlace) {
        showMessage("Index. - Name - (Strength)");
        for (int i = 0; i < unitsToPlace.size(); i++) {
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public static Position askPosition() {
        int x = selectCoordinate(CoordinateAxis.X);
        int y = selectCoordinate(CoordinateAxis.Y);
        return new Position(x, y);
    }

    public static int selectCoordinate(CoordinateAxis axis) {
        boolean coordinateIsInvalid = true;
        int coordinate = 0;
        while (coordinateIsInvalid)
            try {
                showMessage("Enter " + axis + " coordinate: ");
                String answer = input.nextLine();
                coordinate = Integer.parseInt(answer);
                coordinateIsInvalid = false;
            } catch (NumberFormatException e) {
                showMessage("Invalid input format, try again!");
            }
        return coordinate;
    }

    public static void gameTick(Game game) {
        game.update();
        showDeadUnits(game.getDeadUnitsOfCurrentArmy());
        drawBoard(game.getGameField());
        showDeadUnits(game.getDeadUnitsOfEnemyArmy());
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


    public static void startBattlePhase(Game game) {
        showMessage("Battle phase");
        while (game.isPlaying()) {
            Unit selectedUnit = null;
            Position destination;
            boolean moveIsUnfinished = true;
            while (moveIsUnfinished) {
                if (game.getArmyColor() == ArmyColor.BLUE) {
                    boolean invalidUnitSelected = true;
                    while (invalidUnitSelected) {
                        showMessage("Player move");
                        showMessage("Enter the position of the unit you want to move");
                        Position position = askPosition();
                        if (game.armyHasUnitAtPosition(position)) {
                            invalidUnitSelected = false;
                            selectedUnit = game.getUnitAtPositionOfArmy(position);
                            showMessage(game.getSelectedUnitInformation(selectedUnit));
                            continue;
                        }
                        showMessage("You do not have a unit at this position!");
                    }
                    showMessage("Enter the destination you want to move the unit to (enter -1 to cancel move)");
                    int xDes = selectCoordinate(CoordinateAxis.X);
                    int yDes = selectCoordinate(CoordinateAxis.Y);
                    if (xDes == -1 || yDes == -1) {
                        showMessage("Move cancelled");
                        break;
                    }
                    destination = new Position(xDes, yDes);
                } else {
                    destination = generatePosition();
                    selectedUnit = game.selectRandomPlacedUnitFromArmy();
                }
                try {
                    gameMove(selectedUnit, destination, game);
                    moveIsUnfinished = false;
                } catch (StrategoException e) {
                    if (game.getArmyColor() == ArmyColor.BLUE) {
                        showMessage(e.getMessage());
                    }
                }
            }
        }
    }

    public static Position generatePosition() {
        return new Position(rand.nextInt(10), rand.nextInt(10));
    }

    public static void gameMove(Unit selectedUnit, Position destination, Game game) throws StrategoException {
        game.moveUnit(selectedUnit, destination);
        gameTick(game);
        game.swapTurns();
    }

    public static void showWinner(Game game) {
        showMessage("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", game.getWinner());
    }

    public static void saveGame(String fileName, Game game) {
        FileManager.write(game, fileName);
    }
}
