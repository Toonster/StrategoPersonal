package army.unit;

import common.Position;

public class Miner extends Unit {

    public Miner() {
        super(3,1, '8', Rank.Miner);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getRank() == Rank.Bomb) {
            this.position = new Position(enemyUnit.getX(), enemyUnit.getY());
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
