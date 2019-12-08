package army.unit;

import common.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    private int movementSpeed;
    private int strength;
    private Position position;
    private boolean isAlive = true;

    public Unit(int movementSpeed, int power) {
        this.movementSpeed = movementSpeed;
        this.strength = power;
    }

    public int getStrength() {
        return strength;
    }

    public void dies() {
        isAlive = false;
        position = null;
    }

    public String toString() {
        return String.format("%d", strength);
    }

    public boolean canMoveTo(Position destination) {
        boolean canMoveTo = false;
        List<Position> availableDestinationPositions = new ArrayList<>();
        availableDestinationPositions.add(new Position(position.getX() + movementSpeed,position.getY()));
        availableDestinationPositions.add(new Position(position.getX() - movementSpeed,position.getY()));
        availableDestinationPositions.add(new Position(position.getX(),position.getY() + movementSpeed));
        availableDestinationPositions.add(new Position(position.getX(),position.getY() - movementSpeed));
        if (availableDestinationPositions.contains(destination)) {
            canMoveTo = true;
        }
        return canMoveTo;
    }

    public void place(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getUnitType() {
        return getClass().getName();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void battle(Unit enemyUnit) {
        if (strength > enemyUnit.strength) {
            position = enemyUnit.getPosition();
            enemyUnit.dies();
            return;
        }
        dies();
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public boolean isPlaced() {
        return this.isAlive && this.position != null;
    }
}
