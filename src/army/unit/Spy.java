package army.unit;

import common.Position;

public class Spy extends Unit {

    public Spy() {
        super(1, 1, 'S', Rank.Spy);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getRank() == Rank.Marshal) {
            this.position = new Position(enemyUnit.getX(), enemyUnit.getY());
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
