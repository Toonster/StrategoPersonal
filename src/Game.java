import army.*;
import army.unit.*;
import board.Board;
import board.Tile;
import common.Position;
import filemanager.FileManager;
import filemanager.GameData;
import player.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {

    private Army currentArmy;
    private Army enemyArmy;
    private Board board;

    public Game() {
        board = new Board();
        currentArmy = new Army(ArmyColor.BLUE);
        enemyArmy = new Army(ArmyColor.RED);
    }

    public void placeUnit(Unit selectedUnit, int x, int y) throws StrategoException {
        Position unitDestination = new Position(x, y);
        if (!currentArmy.isAvailableStartingPosition(unitDestination)) {
            throw new StrategoException("Invalid starting position!");
        }
        currentArmy.placeUnit(selectedUnit, unitDestination);
    }

    public Unit getUnitAtPositionOfArmy(int x, int y){
        Position unitPosition = new Position(x, y);
        return currentArmy.getUnitAtPosition(unitPosition);
    }

    public boolean armyHasUnitAtPosition(int x, int y) {
        return !currentArmy.hasUnitAtPosition(new Position(x, y));
    }

    public String getSelectedUnitInformation(int x, int y) {
        Unit unit = getUnitAtPositionOfArmy(x,y);
        return String.format("Unit selected: %s at position (%d,%d)\n", unit.getClass().getSimpleName(), unit.getX(), unit.getY());
    }

    public void humanMoveUnit(int xPos, int yPos, int xDes, int yDes) throws StrategoException {
        Unit selectedUnit = getUnitAtPositionOfArmy(xPos, yPos);
        Position destination = new Position(xDes, yDes);
        validateMoveAndResult(selectedUnit, destination);
    }

    private void validateMoveAndResult(Unit selectedUnit, Position destination) throws StrategoException {
        if (board.isInBounds(destination) && selectedUnit.canMoveTo(destination) && board.tilesAreAvailable(selectedUnit.getPathTo(destination))) {
            if (board.tileIsAvailable(destination)) {
                currentArmy.placeUnit(selectedUnit, destination);
                return;
            }
            if (enemyArmy.hasUnitAtPosition(destination)) {
                selectedUnit.battle(enemyArmy.getUnitAtPosition(destination));
                return;
            }
        }
        throw new StrategoException("Invalid move");
    }

    public void update() {
        board.clearUnits();
        board.updateUnits(currentArmy.getPlacedUnits());
        List<Unit> enemyUnits = enemyArmy.getPlacedUnits();
        enemyUnits.forEach(unit -> {
            if (!unit.isVisibleToEnemy()) {
                unit.setChar('X');
            }
        });
        board.updateUnits(enemyUnits);
        enemyArmy.clearUnitVisibility();
    }

    public void swapTurns() {
        Army tempArmy = currentArmy;
        currentArmy = enemyArmy;
        enemyArmy = tempArmy;
    }

    public void saveArmy() {
        FileManager.write(currentArmy, "ArmyConfig.txt");
    }

    public void loadArmyConfig() throws StrategoException {
        try {
            currentArmy = (Army) filemanager.FileManager.read("ArmyConfig.txt");
        } catch (FileNotFoundException e) {
            throw new StrategoException("File not found");
        } catch (IOException e) {
            throw new StrategoException("Error parsing data from file");
        } catch (Exception e) {
            throw new StrategoException("Caught Exception, contact administrator for further information.");
        }
    }

    public void computerMoveUnit() {
        Random rand = new Random();
        boolean moveIsDone = false;
        while (!moveIsDone) {
            List<Unit> placedUnits = currentArmy.getPlacedUnits();
            Unit selectedUnit = placedUnits.get(rand.nextInt(placedUnits.size()));
            Position destination = new Position(rand.nextInt(10), rand.nextInt(10));
            if (board.isInBounds(destination) && selectedUnit.canMoveTo(destination) && board.tilesAreAvailable(selectedUnit.getPathTo(destination))) {
                if (board.tileIsAvailable(destination)) {
                    currentArmy.placeUnit(selectedUnit, destination);
                    moveIsDone = true;
                }
                if (enemyArmy.hasUnitAtPosition(destination)) {
                    selectedUnit.battle(enemyArmy.getUnitAtPosition(destination));
                    moveIsDone = true;;
                }
            }
        }
    }

    public void computerPlaceArmy() {
        Random rand = new Random();
        while (currentArmy.hasUnitsToPlace()) {
            List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
            Unit selectedUnit = unitsToPlace.get(rand.nextInt(unitsToPlace.size()));
            Position unitDestination = new Position(rand.nextInt(10), rand.nextInt(10));
            if (currentArmy.isAvailableStartingPosition(unitDestination)) {
                currentArmy.placeUnit(selectedUnit, unitDestination);
            }
        }
    }

    public boolean currentArmyHasUnitsToPlace() {
        return currentArmy.hasUnitsToPlace();
    }

    public List<Unit> getCurrentArmyUnitsToPlace() {
        return currentArmy.getUnitsToPlace();
    }

    public Tile[][] getGameField() {
        return board.getGameField();
    }

    public boolean isOver() {
        return currentArmy.isDefeated() || enemyArmy.isDefeated();
    }

    public String getWinningArmyColor() {
        if (currentArmy.isDefeated()) {
            return currentArmy.getColor();
        }
        return enemyArmy.getColor();
    }

    public List<Unit> getDeadUnitsOfArmy() {
        return enemyArmy.getDeadUnits();
    }
}


