package board;

public class Tile {

    private char character;

    public Tile(char character) {
        this.character = character;
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
}
