package army;

import army.unit.*;
import common.Position;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Army implements Serializable {

    private List<Unit> units;
    private ArmyColor color;

    public Army(ArmyColor color) {
        this.color = color;
        units = new ArrayList<>();
        initializeArmy();
    }

    public void initializeArmy() {
        units.add(new Flag());
        units.add(new Spy());
        for (int i = 0; i < 8; i++) {
            units.add(new Scout());
        }
        for (int i = 0; i < 5; i++) {
            units.add(new Miner());
        }
        for (int i = 0; i < 4; i++) {
            units.add(new Sergeant());
        }
        for (int i = 0; i < 4; i++) {
            units.add(new Lieutenant());
        }
        for (int i = 0; i < 4; i++) {
            units.add(new Captain());
        }
        for (int i = 0; i < 3; i++) {
            units.add(new Major());
        }
        for (int i = 0; i < 2; i++) {
            units.add(new Colonel());
        }
        for (int i = 0; i < 1; i++) {
            units.add(new General());
        }
        for (int i = 0; i < 1; i++) {
            units.add(new Marshal());
        }
        for (int i = 0; i < 6; i++) {
            units.add(new Bomb());
        }
    }

    public int calculateTotalStrength() {
        int totalStrength = 0;
        for (Unit unit : units) {
            totalStrength += unit.getStrength();
        }
        return totalStrength;
    }

    public Unit getUnitAtPosition(Position position){
        return units.stream().filter(unit -> unit.atPosition(position)).findFirst().orElse(null);
    }

    public void placeUnit(Unit unit, Position position) {
        unit.place(position);
    }

    public boolean isAvailableStartingPosition(Position position) {
        if (hasUnitAtPosition(position)) {
            return false;
        }
        if (this.color.equals(ArmyColor.RED)) {
            return position.getX() < 10 && position.getX() >= 0 && position.getY() >= 0 && position.getY() < 4;
        }
        if (this.color.equals(ArmyColor.BLUE)) {
            return position.getX() < 10 && position.getX() >= 0 && position.getY() >= 6 && position.getY() < 10;
        }
        return false;
    }

    public List<Unit> getUnitsToPlace() {
      return this.units.stream().filter(unit -> !unit.isPlaced()).collect(Collectors.toList());
    }

    public List<Unit> getPlacedUnits() {
        return this.units.stream().filter(Unit::isPlaced).map(Unit::new).collect(Collectors.toList());
    }

    public boolean isDefeated() {
        boolean flagIsDead = units.stream().filter(unit -> unit.getRank() == Unit.Rank.Flag).noneMatch(Unit::isAlive);
        /*List<Unit> placedUnits = this.getPlacedUnits();
        List<Unit> movableUnits = placedUnits.stream().filter(unit -> !(unit.getRank() == Unit.Rank.Flag || unit.getRank() == Unit.Rank.Bomb)).collect(Collectors.toList());
        boolean hasMovableUnit = !movableUnits.isEmpty();*/
        return (flagIsDead);
    }

    public boolean hasUnitAtPosition(Position position) {
        return getUnitAtPosition(position) != null;
    }

    public boolean hasUnitsToPlace() {
        return this.units.stream().anyMatch(unit -> !unit.isPlaced());
    }

    public ArmyColor getColor() {
        return this.color;
    }

    public List<Unit> getDeadUnits() {
        return units.stream().filter(unit -> !unit.isAlive()).collect(Collectors.toList());
    }

    public void clearUnitVisibility() {
        this.units.forEach(Unit::clearVisibleToEnemy);
    }

    public void giveStandardPosToUnits() {
        int index = 0;
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                units.get(index++).place(new Position(x,y));
            }
        }
    }
}

