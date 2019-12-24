package board;

public class Surface {

    private final boolean accessible;
    private final char character;

    public Surface( boolean accessible, char character) {
        this.accessible = accessible;
        this.character = character;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public char getCharacter() {
        return character;
    }
}
