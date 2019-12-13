package army;

import army.unit.*;
import common.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Army {

    private List<Unit> units;
    private String color;

    public Army(String color) {
        this.color = color;
        units = new ArrayList<>();
        initializeArmy();
    }

    public void initializeArmy() {
        addAmountOfUnitsOfType(new Flag(), 1);
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
    }

    public int calculateTotalStrength() {
        int totalStrength = 0;
        for (Unit unit : units) {
            totalStrength += unit.getStrength();
        }
        return totalStrength;
    }

    public Unit getUnitAtPosition(Position position){
        return (Unit) units.stream().filter(unit -> unit.atPosition(position));
    }

    public void placeUnit(Unit unit, Position position) {
        unit.place(position);
    }

    public boolean isStartingPosition(Position position) {
        return position.getX() < 10 && position.getY() < 4 && !hasUnitAtPosition(position);
    }

    public List<Unit> getUnitsToPlace() {
      return this.units.stream().filter(unit -> !unit.isPlaced()).collect(Collectors.toList());
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
        return color;
    }

    public void showDeadUnits() {
        System.out.println(units.stream().filter(unit -> !unit.isAlive()).collect(Collectors.toList()));
    }
}

