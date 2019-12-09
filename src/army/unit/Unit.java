package army.unit;

import common.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    private int movementSpeed;
    private int strength;
    protected Position position;
    private boolean isAlive = true;
    private String weakness;

    public Unit(int movementSpeed, int power) {
        this.movementSpeed = movementSpeed;
        this.strength = power;
    }

    public int getStrength() {
        return strength;
    }

    public void die() {
        isAlive = false;
        position = null;
    }

    public String toString() {
        return String.format("%d", strength);
    }

    public boolean canMoveTo(Position destination) {
        int deltaX = Math.abs(this.position.getX() - destination.getX());
        int deltaY = Math.abs(this.position.getY() - destination.getY());
        return (deltaX <= this.movementSpeed && deltaY == 0) || (deltaY <= this.movementSpeed && deltaX == 0);
    }

    public void place(Position position) {
        this.position = position;
    }

   /* public Position getPosition() {
        return position;
    }*/

    public boolean isAlive() {
        return isAlive;
    }

    public void battle(Unit enemyUnit) {
        if (strength > enemyUnit.strength) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        if (strength == enemyUnit.strength) {
            enemyUnit.die();
        }
        die();
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

    public boolean atPosition(Position position) {
        return this.position.equals(position);
    }

    public List<Position> getDestinationPath(Position destination) {
        List<Position> destinationPath = new ArrayList<>();
        int deltaX = Math.abs(this.position.getX() - destination.getX());
        int deltaY = Math.abs(this.position.getY() - destination.getY());
        for (int x = this.getX() + 1; x < x+deltaX; x++) {
            for (int y = this.getY() + 1; y < y+deltaY; y++) {
                destinationPath.add(new Position(x,y));
            }
        }
        return destinationPath;
    }

    public boolean isScout() {
        return this instanceof Scout;
    }
}