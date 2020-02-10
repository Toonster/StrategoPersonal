package army;

import army.unit.*;
import common.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Army implements Serializable {

    private List<Unit> units;
    private UnitColor color;

    public Army(UnitColor color) {
        this.color = color;
        units = new ArrayList<>();
        initializeArmy();
    }

    public void initializeArmy() {
        for (Rank value : Rank.values()) {
            for (UnitColor unitColor : UnitColor.values()) {
                for (int i = 0; i < value.getAmountOf(); i++) {
                    units.add(new Unit(value, unitColor));
                }
            }
        }
    }

    public Unit getUnitAtPosition(Position position) {
        return units.stream().filter(unit -> unit.atPosition(position)).findFirst().orElse(null);
    }

    public void placeUnit(Unit unit, Position position) {
        unit.place(position);
    }

    public List<Unit> getUnitsToPlace() {
        return this.units.stream().filter(unit -> !unit.isPlaced()).collect(Collectors.toList());
    }

    public List<Unit> getPlacedUnits() {
        return this.units.stream().filter(Unit::isPlaced).collect(Collectors.toList());
    }

    public boolean isDefeated() {
        boolean flagIsDead = units.stream().anyMatch(unit -> unit.getRank() == Unit.Rank.Flag && unit.isDead());
        List<Unit> placedUnits = this.getPlacedUnits();
        boolean hasMovableUnit = placedUnits.stream().anyMatch(unit -> unit.getRank() != Unit.Rank.Flag && unit.getRank() != Unit.Rank.Bomb);
        return (flagIsDead || !hasMovableUnit);
    }

    public boolean hasUnitAtPosition(Position position) {
        return getUnitAtPosition(position) != null;
    }

    public boolean hasUnitsToPlace() {
        return this.units.stream().anyMatch(unit -> !unit.isPlaced());
    }

    public UnitColor getColor() {
        return this.color;
    }

    public List<Unit> getDeadUnits() {
        return units.stream().filter(Unit::isDead).collect(Collectors.toList());
    }

    public void updateUnitVisibility() {
        this.units.forEach(Unit::updateVisibleToEnemy);
    }

    public Unit selectRandomPlacedUnit() {
        Random rand = new Random();
        List<Unit> placedUnits = this.units.stream().filter(Unit::isPlaced).collect(Collectors.toList());
        return placedUnits.get(rand.nextInt(placedUnits.size()));
    }
}

