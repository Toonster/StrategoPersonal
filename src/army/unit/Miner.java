package army.unit;

public class Miner extends Unit {

    public Miner() {
        super(1,3, '8', Rank.Miner);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit instanceof Bomb) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
