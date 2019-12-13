package player;

import common.Position;

import java.util.List;
import java.util.Random;

public class Computer extends Player {

    Random rand = new Random();

    @Override
    public Position selectUnitPosition() {

        return new Position(rand.nextInt(50), rand.nextInt(50));

    }

    @Override
    public Position selectDestination() {

        return new Position(rand.nextInt(50), rand.nextInt(50));

    }

    @Override
    public Unit selectUnitToPlace(List<Unit> unitsToPlace) {

        return unitsToPlace.get(rand.nextInt(unitsToPlace.size()));
        
    }

    @Override
    public boolean useStandardArmyConfig() {
        return false;
    }
}
