/*
import army.Army;
import army.unit.Unit;
import board.Board;
import board.Tile;

import java.util.List;
import java.util.Scanner;

public class GameView {

    static Scanner input = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showUnitsToPlace(List<Unit> unitsToPlace) {
        showMessage("Index. - Name - (Strength)");
        for (int i = 0; i < unitsToPlace.size(); i++){
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public void showWinner (Army army) {
        showMessage("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", army.getColor());
    }

    public void drawBoard (Tile[][] gameField) {
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

    public void showDeadUnits(List<Unit> deadUnits) {
        System.out.println(deadUnits);
    }

    public Unit selectUnitToPlace(List<Unit> unitsToPlace) {
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

    public int selectCoordinate(String axis) {
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
}
*/
