import army.Army;
import army.ArmyColor;
import army.unit.Unit;
import board.Board;
import board.Tile;
import common.Position;
import player.Player;

import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        showMessage("The game has started!");
        if (useStandardArmyConfig()) {

        }
        while(currentArmy.hasUnitsToPlace()) {
            showMessage("Choose a unit to place!");
            showUnitsToPlace();
            int index = selectIndexOfUnitToPlace();
            int x = selectCoordinate("X");
            int y = selectCoordinate("Y");
            placeUnit(index, x, y);
        }
        showMessage("All your units have been placed!");
        swapTurns();

        showMessage("Enter position of the unit you want to move (x,y): ");

    }

    static Army currentArmy = new Army(ArmyColor.BLUE);
    static Army enemyArmy = new Army(ArmyColor.RED);
    static Board board = new Board();

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void showUnitsToPlace() {
        showMessage("Index. - Name - (Strength)");
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        for (int i = 0; i < unitsToPlace.size(); i++){
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public static void showWinner (Army army) {
        System.out.println("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", army.getColor());
    }

    public static void drawBoard () {
        Tile [][] gameField = board.getGameField();
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

    public static int selectIndexOfUnitToPlace() {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        Scanner input = new Scanner(System.in);
        boolean indexIsInvalid = true;
        int index = 0;
        showMessage("Enter index: ");
        while (indexIsInvalid) {
            try {
                index = Integer.parseInt(input.nextLine());
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
        return index;
    }

    public static int selectCoordinate(String axis) {
        Scanner input = new Scanner(System.in);
        showMessage("Select " + axis + " coordinate of a position: ");
        boolean isRunning = true;
        int coordinate = 0;
        while (isRunning) {
            try {
                coordinate = Integer.parseInt(input.nextLine());
                isRunning = false;
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid input format!");
            }
        }
        return coordinate;
    }

    public static boolean useStandardArmyConfig() {
        Scanner input = new Scanner(System.in);
        System.out.print("Would you like to use a standard army configuration (y/n)?: ");
        String answer = input.nextLine();
        return answer.equalsIgnoreCase("y");
    }

    public static void swapTurns() {
        Army tempArmy = currentArmy;
        currentArmy = enemyArmy;
        enemyArmy = tempArmy;
    }

    public static void placeUnit(int index, int x, int y) {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        Unit selectedUnit = unitsToPlace.get(index);
        Position unitDestination = new Position(x,y);
        if (enemyArmy.hasUnitsToPlace()) {
            unitDestination.add(new Position(0, 6));
        }
        if (currentArmy.isAvailableStartingPosition(unitDestination)) {
            currentArmy.placeUnit(selectedUnit, unitDestination);
        }
    }
}
