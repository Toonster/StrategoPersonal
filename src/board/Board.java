package board;

import common.Position;
import army.unit.Unit;

import java.util.List;

public class Board {

    private final int HEIGHT = 10;
    private final int WIDTH = 10;
    private Tile[][] gameField;

    public Board() {
        gameField = new Tile[WIDTH][HEIGHT];
        initializeField();
    }

    public void initializeField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if ((y == 4 || y == 5) && (x == 2 || x == 3 || x == 6 || x == 7)) {
                    Tile waterTile = new Tile(Surface.WATER);
                    gameField[x][y] = waterTile;
                    continue;
                }
                gameField[x][y] = new Tile(Surface.GRASS);
            }
        }
    }

    public void clearUnits() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (!((y == 4 || y == 5) && (x == 2 || x == 3 || x == 6 || x == 7))) {
                    gameField[x][y].clear();
                }
            }
        }
    }

    public void updateUnits(List<Unit> units, boolean visible) {
        for (Unit unit : units) {
            char unitCharacter = unit.isVisibleToEnemy() ? unit.getCharacter() : visible ? unit.getCharacter() : 'X';
            gameField[unit.getX()][unit.getY()].update(unit);
        }
    }

    public boolean isInBounds(Position position) {
        return position.getX() >= 0 && position.getX() < WIDTH && position.getY() >= 0 && position.getY() < HEIGHT;
    }

    public boolean positionsAreFree(List<Position> tilePositions) {
        if (tilePositions.isEmpty()) {
            return true;
        }
        boolean tilesAvailable = true;
        for (Position tilePosition : tilePositions) {
            if (!tileIsFree(tilePosition) || !tileIsAccessible(tilePosition)) {
                tilesAvailable = false;
            }
        }
        return tilesAvailable;
    }

    public boolean tileIsFree(Position tilePosition) {
        return this.gameField[tilePosition.getX()][tilePosition.getY()].isOccupied();
    }

    public boolean tileIsAccessible(Position tilePosition) {
        return this.gameField[tilePosition.getX()][tilePosition.getY()].isAccessible();
    }

    public Tile[][] getGameField() {
        return gameField;
    }
}
