package army.unit;

import common.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Unit implements Serializable {

    private int movementSpeed;
    private int strength;
    protected Position position;
    private boolean alive = true;
    private Character character;
    private boolean visibleToEnemy = false;
    private Rank rank;

    public enum Rank {
        Bomb(0,11,'B'), Captain(1,6, '5'), Colonel (1,8,'3'),
        Flag(0,0, 'F'), General(1,9, '2'), Lieutenant(1, 5, '6'),
        Major(1,7, '4'), Marshal(1,10, '1'), Miner(1,3, '8'),
        Scout(9, 2, '9'), Sergeant(1,4, '7'), Spy(1, 1, 'S');
        private int movementSpeed;
        private int strength;
        private Character character;

        Rank(int movementSpeed, int strength, Character character) {
            this.movementSpeed = movementSpeed;
            this.strength = strength;
            this.character = character;
        }
    }

    public void battle(Unit enemyUnit) {
        switch (this.rank) {
            case Spy:
                if (enemyUnit.rank == Rank.Marshal) {
                    enemyUnit.die();
                }
                break;
            case Miner:
                if (enemyUnit.rank == Rank.Bomb) {
                    enemyUnit.die();
                }
            default:
            if (rank.strength > enemyUnit.rank.strength) {
                this.position = enemyUnit.position;
                enemyUnit.die();
                return;
            }
            if (strength == enemyUnit.strength) {
                enemyUnit.die();
                this.die();
                return;
            }
            die();
            enemyUnit.setVisibleToEnemy();
        }
    }


    public Unit(int movementSpeed, int power, Character character) {
        this.movementSpeed = movementSpeed;
        this.strength = power;
        this.character = character;
    }

    public int getStrength() {
        return strength;
    }

    public void die() {
        alive = false;
        position = null;
    }

    public String toString() {
        return String.format("%s - (%d)\n",this.getClass().getSimpleName(),strength);
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
        return alive;
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
        if (!hasPosition()) {
            return false;
        }
        return this.position.equals(position);
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

    public void setVisibleToEnemy() {
        visibleToEnemy = true;
    }

    public void clearVisibleToEnemy() {
        visibleToEnemy = false;
    }

    public boolean isVisibleToEnemy() {
        return visibleToEnemy;
    }
}