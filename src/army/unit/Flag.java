package army.unit;

public class Flag extends Unit {

    public Flag() {
        super(0,0);
    }

    @Override
    public String toString() {
        return "F";
    }

    @Override
    public void battle(Unit enemyUnit) {
        die();
    }
}
