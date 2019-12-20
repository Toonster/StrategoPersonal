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
    private List<Position> startingPosition;
    private Color color;

    public enum Color {
        BLUE, RED
    }

    public Army(String color) {
        this.color = Color.valueOf(color.toUpperCase());
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

/*ddAmountOfUnitsOfType(new Flag(), 1);
        addAmountOfUnitsOfType(new Spy(), 1);
        addAmountOfUnitsOfType(new Scout(), 8);
        addAmountOfUnitsOfType(new Miner(), 5);
        addAmountOfUnitsOfType(new Sergeant(), 4);
        addAmountOfUnitsOfType(new Lieutenant(), 4);
        addAmountOfUnitsOfType(new Captain(), 4);
        addAmountOfUnitsOfType(new Major(), 3);
        addAmountOfUnitsOfType(new Colonel(), 2);
        addAmountOfUnitsOfType(new General(), 1);
        addAmountOfUnitsOfType(new Marshal(), 1);
        addAmountOfUnitsOfType(new Bomb(), 6);

    }

    public void addAmountOfUnitsOfType(Unit unit, int amount) {
        for (int i = 0; i < amount; i++) {
            units.add(unit);
        }
    }*/
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
        if (this.color.equals(Color.RED)) {
            return position.getX() < 10 && position.getX() >= 0 && position.getY() >= 0 && position.getY() < 4 && !hasUnitAtPosition(position);
        }
        if (this.color.equals(Color.BLUE)) {
            return position.getX() < 10 && position.getX() >= 0 && position.getY() >= 6 && position.getY() < 10 && !hasUnitAtPosition(position);
        }
        return false;
    }

    public List<Unit> getUnitsToPlace() {
      return this.units.stream().filter(unit -> !unit.isPlaced()).collect(Collectors.toList());
    }

    public List<Unit> getPlacedUnits() {
        return this.units.stream().filter(Unit::isPlaced).collect(Collectors.toList());
    }

    public boolean isDefeated() {
        return units.stream().filter(unit -> unit instanceof Flag).noneMatch(Unit::isAlive);
    }

    public boolean hasUnitAtPosition(Position position) {
        return getUnitAtPosition(position) != null;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public boolean hasUnitsToPlace() {
        return this.units.stream().anyMatch(unit -> !unit.isPlaced());
    }

    public String getColor() {
        return this.color.name();
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

