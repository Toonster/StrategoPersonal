package board;

import common.Position;
import army.unit.Unit;

import java.util.List;

public class Board {

    private final int HEIGTH = 10;
    private final int WIDTH = 10;
    private Tile[][] gameField;

    public Board() {
        gameField = new Tile[WIDTH][HEIGTH];
        initializeField();
    }

    public void initializeField() {
        for (int y = 0; y < HEIGTH; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if ((y == 4 || y == 5) && (x == 2 || x == 3 || x == 6 || x == 7)) {
                    gameField[x][y] = new Tile(new Water());
                    continue;
                }
                gameField[x][y] = new Tile(new Grass());
            }
        }
    }

    public void draw() {
        for (int y = 0; y < HEIGTH; y++) {
            for (int x = 0; x < WIDTH; x++) {
                gameField[x][y].draw();
            }
            System.out.println();
        }
    }

    public void update(List<Unit> units) {
        for (Unit unit: units){
            gameField[unit.getX()][unit.getY()].update(unit.toString().charAt(0));
        }
    }

    public boolean isInBounds(Position position) {
        return position.getX() >= 0 && position.getX() < WIDTH  && position.getY() >= 0 && position.getY() < HEIGTH &&;
    }

    public boolean tilesAreFree(List<Position> tilePositions) {
        boolean tilesAreFree = true;
        for (Position position : tilePositions) {
            if (!tileIsFree(position)) {
                tilesAreFree = false;
            }
        }
        return tilesAreFree;
    }

    public boolean tileIsFree(Position tilePosition) {
        return gameField[tilePosition.getX()][tilePosition.getY()].isFree();
    }
}
