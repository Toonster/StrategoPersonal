/*
package player;

import army.unit.Unit;
import common.Position;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Human extends Player {

    Scanner input = new Scanner(System.in);

    @Override
    public Position selectUnitPosition() {
        return selectPosition("Enter position of the unit you want to move (x,y): ");
    }

    @Override
    public Position selectDestination() {
        return selectPosition("Enter unit destination (x,y): ");
    }

    private Position selectPosition(String answer) {
        boolean isRunning = true;
        int x = 0;
        int y = 0;
        while (isRunning) {
            try {
                x = Integer.parseInt(answer.substring(0, answer.indexOf(',')));
                y = Integer.parseInt(answer.substring(answer.indexOf(',') + 1));
                isRunning = false;
            } catch (StringIndexOutOfBoundsException e) {
                throw new StringIndexOutOfBoundsException("Index is out of bounds!");
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid input format!");
            }
        }
        return new Position(x, y);
    }

    @Override
    public Unit selectUnitToPlace(List<Unit> unitsToPlace, String input) {
        boolean indexIsInvalid = true;
        int index = 0;
        while (indexIsInvalid) {
            try {
                index = Integer.parseInt(input);
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

    @Override
    public boolean useStandardArmyConfig() {
        System.out.print("Would you like to use a standard army configuration (y/n)?: ");
        String answer = input.nextLine();
        return answer.equalsIgnoreCase("y");
    }
}
*/
