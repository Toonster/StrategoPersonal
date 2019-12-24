package army.unit;

import common.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unit implements Serializable {

    protected Position position;
    private boolean alive = true;
    private final int strength;
    private final int movementSpeed;
    private final char character;
    private final Rank rank;
    private int visibleForTurns = 0;

    public enum Rank {
        Bomb, Captain, Colonel,
        Flag, General, Lieutenant,
        Major, Marshal, Miner,
        Scout, Sergeant, Spy
    }

    public Unit(int strength, int movementSpeed, char character, Rank rank) {
        this.strength = strength;
        this.movementSpeed = movementSpeed;
        this.character = character;
        this.rank = rank;
    }

    public void battle(Unit enemyUnit) {
        if (this.strength > enemyUnit.strength) {
            this.position = new Position(enemyUnit.getX(), enemyUnit.getY());
            enemyUnit.die();
            return;
        }
        if (this.strength == enemyUnit.strength) {
            enemyUnit.die();
            die();
            return;
        }
        die();
        enemyUnit.setVisibleToEnemy();
    }

    public int getStrength() {
        return this.strength;
    }

    public void die() {
        alive = false;
        position = null;
    }

    public String toString() {
        return String.format("%s - (%d)\n", this.getClass().getSimpleName(), this.strength);
    }

    public boolean canMoveTo(Position destination) {
        int deltaX = Math.abs(this.position.getX() - destination.getX());
        int deltaY = Math.abs(this.position.getY() - destination.getY());
        return (deltaX <= this.movementSpeed && deltaY == 0) || (deltaY <= this.movementSpeed && deltaX == 0);
    }

    public void place(Position position) {
        this.position = position;
    }

    public boolean isDead() {
        return !alive;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public boolean isPlaced() {
        return this.alive && this.position != null;
    }

    public boolean atPosition(Position position) {
        return hasPosition() && this.position.equals(position);
    }

    public boolean hasPosition() {
        return this.position != null;
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
        if (destinationPath.size() >= 2) {
            destinationPath.remove(destinationPath.size() - 1);
        }
        destinationPath.remove(0);
        return destinationPath;
    }

    public Character getCharacter() {
        return this.character;
    }

    public void setVisibleToEnemy() {
        visibleForTurns = 3;
    }

    public void updateVisibleToEnemy() {
        if (visibleForTurns > 0) {
            this.visibleForTurns--;
        }
    }

    public boolean isVisibleToEnemy() {
        return visibleForTurns > 0;
    }

    public Rank getRank() {
        return this.rank;
    }
}