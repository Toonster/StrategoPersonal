package army.unit;

public class Flag extends Unit {

    public Flag() {
        super(0,0, 'F');
    }

    @Override
    public String toString() {
        return String.format("%s - (%d)\n",this.getClass().getName(),0);
    }
}
