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

    private Position selectPosition(String message) {
        boolean isRunning = true;
        int x = 0;
        int y = 0;
        while (isRunning) {
            System.out.println(message);
            String position = input.nextLine();
            try {
                x = Integer.parseInt(position.substring(0, position.indexOf(',')));
                y = Integer.parseInt(position.substring(position.indexOf(',') + 1));
                isRunning = false;
            } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Invalid input format, try again!");
            }
        }
        return new Position(x, y);
    }

    @Override
    public Unit selectUnitToPlace(List<Unit> unitsToPlace) {
        System.out.println("Choose a unit to place!");
        System.out.println("Index. - Name - (Strength)");
        for (int i = 0; i < unitsToPlace.size(); i++) System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        boolean indexIsInvalid = true;
        int index = 0;
        while (indexIsInvalid) {
            System.out.print("Enter index: ");
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
        return unitsToPlace.get(index);
    }

    @Override
    public boolean useStandardArmyConfig() {
        System.out.print("Would you like to use a standard army configuration (y/n)?: ");
        String answer = input.nextLine();
        return answer.equalsIgnoreCase("y");
    }
}
