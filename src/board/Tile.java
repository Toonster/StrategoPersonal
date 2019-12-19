package board;

public class Tile {

    private char character;
    private Surface surface;

    public Tile(Surface surface) {
        this.surface = surface;
    }

    public void draw() {
        System.out.print(character);
    }

    public void update(char character) {
        this.character = character;
    }

    public void clear() {
        character = ' ';
    }

    public boolean isFree() {
        return character == ' ';
    }

    public boolean isAccessible() {
        return this.surface.isAccessible();
    }

    public char getSurfaceCharacter() {
        return this.surface.getCharacter();
    }
}
