package army.unit;

public class Spy extends Unit {

    public Spy() {
        super(1, 1, 'S', Rank.Spy);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit instanceof Marshal) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
