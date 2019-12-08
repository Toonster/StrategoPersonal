package board;

public class Surface extends Tile {

    private boolean isAccessible;

    public Surface(char character, boolean isAccessible) {
        super(character);
        this.isAccessible = isAccessible;
    }
}
