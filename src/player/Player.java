package player;

import common.Position;
import army.unit.Unit;

import java.io.Serializable;
import java.util.List;

public abstract class Player implements Serializable {

    public abstract Position selectUnitPosition();

    public abstract Position selectDestination();

    public abstract Unit selectUnitToPlace(List<Unit> unitsToPlace, String input);

    public abstract boolean useStandardArmyConfig();
}
