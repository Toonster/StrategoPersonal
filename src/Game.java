import army.*;
import army.unit.*;
import board.Board;
import board.Tile;
import common.Position;
import filemanager.FileManager;

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

    public void placeUnit(Unit selectedUnit, Position unitDestination) throws StrategoException {
        if (!currentArmy.isAvailableStartingPosition(unitDestination)) {
            throw new StrategoException("Invalid starting position!");
        }
        currentArmy.placeUnit(selectedUnit, unitDestination);
    }

    public void moveUnit(Unit selectedUnit, Position destination) throws StrategoException {
        validateMove(selectedUnit, destination);
        processMove(selectedUnit, destination);
    }

    public void validateMove(Unit selectedUnit, Position destination) throws StrategoException {
        boolean isInBounds = board.isInBounds(destination);
        boolean canMoveTo = selectedUnit.canMoveTo(destination);
        boolean tilesAreAvailable = board.positionsAreFree(selectedUnit.getPathTo(destination));
        boolean friendlyUnitAtPosition = currentArmy.hasUnitAtPosition(destination);
        boolean isAccessible = board.tileIsAccessible(destination);

        if (isInBounds && canMoveTo && tilesAreAvailable && !friendlyUnitAtPosition && isAccessible) {
            return;
        }
        throw new StrategoException("Invalid move");
    }

    private void processMove(Unit selectedUnit, Position destination) {
        boolean available = board.tileIsFree(destination);
        if (available) {
            currentArmy.placeUnit(selectedUnit, destination);
            return;
        }
        boolean unitAtPlace = enemyArmy.hasUnitAtPosition(destination);
        if (unitAtPlace) {
            selectedUnit.battle(enemyArmy.getUnitAtPosition(destination));
        }
    }

    public void update() {
        board.clearUnits();
        currentArmy.updateUnitVisibility();
        enemyArmy.updateUnitVisibility();
        List<Unit> currentUnits = currentArmy.getPlacedUnits();
        List<Unit> enemyUnits = enemyArmy.getPlacedUnits();
        boolean isPlayer = currentArmy.getColor() == ArmyColor.BLUE;
        board.updateUnits(currentUnits, isPlayer);
        board.updateUnits(enemyUnits, !isPlayer);
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

    public boolean armyHasUnitAtPosition(Position position) {
        return currentArmy.hasUnitAtPosition(position);
    }

    public String getSelectedUnitInformation(Unit unit) {
        return String.format("Unit selected: %s at position (%d,%d)\n", unit.getClass().getSimpleName(), unit.getX(), unit.getY());
    }

    public void computerPlaceArmy() {
        Random rand = new Random();
        while (currentArmy.hasUnitsToPlace()) {
            List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
            Unit selectedUnit = unitsToPlace.get(rand.nextInt(unitsToPlace.size()));
            Position unitDestination = new Position(rand.nextInt(10), rand.nextInt(10));
            try {
                placeUnit(selectedUnit, unitDestination);
            } catch (StrategoException ignored) {
            }
        }
    }

    public boolean currentArmyHasUnitsToPlace() {
        return currentArmy.hasUnitsToPlace();
    }

    public List<Unit> getArmyUnitsToPlace() {
        return currentArmy.getUnitsToPlace();
    }

    public Tile[][] getGameField() {
        return board.getGameField();
    }

    public boolean isPlaying() {
        return !currentArmy.isDefeated() && !enemyArmy.isDefeated();
    }

    public List<Unit> getDeadUnitsOfCurrentArmy() {
        return currentArmy.getDeadUnits();
    }

    public List<Unit> getDeadUnitsOfEnemyArmy() {
        return enemyArmy.getDeadUnits();
    }

    public ArmyColor getArmyColor() {
        return currentArmy.getColor();
    }

    public Unit selectRandomPlacedUnitFromArmy() {
        return currentArmy.selectRandomPlacedUnit();
    }

    public ArmyColor getWinner() {
        return this.currentArmy.isDefeated() ? this.enemyArmy.getColor() : this.currentArmy.getColor();
    }

    public Unit getUnitAtPositionOfArmy(Position position) {
        return currentArmy.getUnitAtPosition(position);
    }
}


