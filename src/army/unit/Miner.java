package army.unit;

public class Miner extends Unit {

    public Miner() {
        super(3,1, '8', Rank.Miner);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getRank() == Rank.Bomb) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
