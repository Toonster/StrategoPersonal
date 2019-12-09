package army.unit;

public class Bomb extends Unit {

    public Bomb() {
        super(0, 0);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public void battle(Unit enemyUnit) {
        if (enemyUnit.getType().equalsIgnoreCase("Miner")) {
            this.die();
            return;
        }
        enemyUnit.die();
    }
}
