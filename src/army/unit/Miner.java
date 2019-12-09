package army.unit;

public class Miner extends Unit {

    public Miner() {
        super(1,3);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getType().equals("Bomb")) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
