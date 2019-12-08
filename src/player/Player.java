package player;

import common.Position;
import army.unit.Unit;

import java.util.List;

public abstract class Player {

    public abstract Position selectUnitPosition();

    public abstract Position selectDestination();

    public abstract Unit selectUnitToPlace(List<Unit> unitsToPlace);
}