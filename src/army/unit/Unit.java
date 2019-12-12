package army.unit;

import common.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    private int movementSpeed;
    private int strength;
    protected Position position;
    private boolean isAlive = true;
    private Character character;

    public Unit(int movementSpeed, int power, Character character) {
        this.movementSpeed = movementSpeed;
        this.strength = power;
        this.character = character;
    }

    public int getStrength() {
        return strength;
    }

    public void die() {
        isAlive = false;
        position = null;
    }

    public String toString() {

        return String.format("%s - (%d)\n",this.getClass().getName(),strength);
    }

    public boolean canMoveTo(Position destination) {
        int deltaX = Math.abs(this.position.getX() - destination.getX());
        int deltaY = Math.abs(this.position.getY() - destination.getY());
        return (deltaX <= this.movementSpeed && deltaY == 0) || (deltaY <= this.movementSpeed && deltaX == 0);
    }

    public void place(Position position) {
        this.position = position;
    }

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

    public List<Position> getPathTo(Position destination) {

        List<Position> destinationPath = new ArrayList<>();

        int xMin = Math.min(this.position.getX(), destination.getX());
        int xMax = Math.max(this.position.getX(), destination.getX());
        int yMin = Math.min(this.position.getY(), destination.getY());
        int yMax = Math.max(this.position.getY(), destination.getY());

        for (int x = xMin; x < xMax + 1; x++) {
            for (int y = yMin; y < yMax + 1; y++) {
                destinationPath.add(new Position(x, y));
            }
        }

        destinationPath.remove(destinationPath.size() - 1);
        destinationPath.remove(0);
        return destinationPath;
    }

    public Character getCharacter() {
        return character;
    }

    public void setChar(Character character) {
        this.character = character;
    }
}