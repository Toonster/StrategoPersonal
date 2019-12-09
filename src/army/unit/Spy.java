package army.unit;

public class Spy extends Unit {

    public Spy() {
        super(1, 1);
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getType().equals("Marshal")) {
            this.position = enemyUnit.position;
            enemyUnit.die();
            return;
        }
        super.battle(enemyUnit);
    }
}
