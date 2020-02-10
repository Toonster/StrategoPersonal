package army.unit;

import common.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Unit implements Serializable {

    private final Rank rank;
    private final UnitColor color;
    protected Position position;
    private boolean alive = true;
    private int visibleForTurns = 0;

    public Unit(Rank rank, UnitColor color) {
        this.rank = rank;
        this.color = color;
    }

    public Rank getRank() {
        return rank;
    }

    public void battle(Unit enemyUnit) {
        Rank enemyRank = enemyUnit.getRank();
        if (rank.getBattleResult(enemyRank) == BattleResult.WIN) {
            this.place(new Position(enemyUnit.getX(), enemyUnit.getY()));
            enemyUnit.die();
            return;
        }
        if (rank.getBattleResult(enemyRank) == BattleResult.DRAW) {
            enemyUnit.die();
            die();
            return;
        }
        if (rank.getBattleResult(enemyRank) == BattleResult.LOSS) {
            die();
            enemyUnit.setVisibleToEnemy();
        }
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
        return (deltaX <= this.rank.getMovementspeed() && deltaY == 0) || (deltaY <= this.rank.getMovementspeed() && deltaX == 0);
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
}