package player;

import army.unit.Unit;
import common.Position;

import java.util.List;
import java.util.Scanner;

public class Human implements Player {

    Scanner input = new Scanner(System.in);

    @Override
    public Position selectUnitPosition() {

        System.out.println("Enter position of the unit you want to move (x,y): ");
        String position = input.nextLine();
        int x = Integer.parseInt(position.substring(0, position.indexOf(',')));
        int y = Integer.parseInt(position.substring(position.indexOf(',')+1));
        return new Position(x, y);

    }

    @Override
    public Position selectDestination() {

        System.out.println("Enter unit destination (x,y): ");
        String destination = input.nextLine();
        int x = Integer.parseInt(destination.substring(0, destination.indexOf(',')));
        int y = Integer.parseInt(destination.substring(destination.indexOf(',')+1));
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
            index = Integer.parseInt(input.nextLine());
            if (index < 0 || index >= unitsToPlace.size()) {
                System.out.println("Invalid index, try again");
                continue;
            }
            indexIsInvalid = false;
        }
        return unitsToPlace.get(index);
    }

    @Override
    public boolean useStandardArmyConfig() {
        return false;
    }
}
