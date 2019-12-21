package army.unit;

public class Bomb extends Unit {

    public Bomb() {
        super(0, 11, 'B', Rank.Bomb);
    }

    @Override
    public String toString() {
        return String.format("%s - (%d)\n",this.getClass().getSimpleName(),0);
    }
}
