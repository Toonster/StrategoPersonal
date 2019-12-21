package army.unit;

public class Flag extends Unit {

    public Flag() {
        super(0,0, 'F', Rank.Flag);
    }

    @Override
    public String toString() {
        return String.format("%s - (%d)\n",this.getClass().getSimpleName(),0);
    }
}
