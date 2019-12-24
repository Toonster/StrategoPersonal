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

    public Unit getUnitAtPositionOfArmy(Position unitPosition){
        return currentArmy.getUnitAtPosition(unitPosition);
    }

    public boolean armyHasUnitAtPosition(Position position) {
        return currentArmy.hasUnitAtPosition(position);
    }

    public String getSelectedUnitInformation(Unit unit) {
        return String.format("Unit selected: %s at position (%d,%d)\n", unit.getClass().getSimpleName(), unit.getX(), unit.getY());
    }

    public void moveUnit(Unit selectedUnit, Position destination) throws StrategoException {
        try {
            validateMove(selectedUnit, destination);
        } catch (StrategoException e) {
            throw new StrategoException(e.getMessage());
        }
        processMove(selectedUnit, destination);
    }

    public void validateMove(Unit selectedUnit, Position destination) throws StrategoException {
        boolean isInBounds = board.isInBounds(destination);
        boolean canMoveTo = selectedUnit.canMoveTo(destination);
        boolean tilesAreAvailable = board.positionsAreFree(selectedUnit.getPathTo(destination));
        boolean friendlyUnitAtPosition = currentArmy.hasUnitAtPosition(destination);
        if (isInBounds && canMoveTo && tilesAreAvailable && !friendlyUnitAtPosition) {
            return;
        }
        throw new StrategoException("Invalid move");
    }

    private void processMove(Unit selectedUnit, Position destination) {
        boolean available = board.tileIsAvailable(destination);
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
        if (currentArmy.getColor() == ArmyColor.BLUE) {
            enemyArmy.clearUnitVisibility();
            currentArmy.clearUnitVisibility();
            board.updateUnits(currentArmy.getPlacedUnits());
            List<Unit> units = enemyArmy.getPlacedUnits();
            armySetUnknown(units);
            board.updateUnits(units);
        } else {
            board.updateUnits(enemyArmy.getPlacedUnits());
            List<Unit> unitsVisible = currentArmy.getPlacedUnits();
            armySetUnknown(unitsVisible );
            board.updateUnits(unitsVisible );
        }
    }

    public void armySetUnknown(List<Unit> unitsVisible) {
        unitsVisible.forEach(unit -> {
            if (!unit.isVisibleToEnemy()) {
                unit.setChar('X');
            } else {
                System.out.println();
            }
        });
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

    public void humanPlaceArmy() {
        currentArmy.giveStandardPosToUnits();
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

    public List<Unit> getDeadUnitsOfCurrentArmy() {
        return currentArmy.getDeadUnits();
    }
    public List<Unit> getDeadUnitsOfEnemyArmy() {
        return enemyArmy.getDeadUnits();
    }

    public List<Unit> getArmyUnits() {
        return currentArmy.getPlacedUnits();
    }

    public ArmyColor getArmyColor() {
        return currentArmy.getColor();
    }

    public boolean allUnitsPlaced() {
        return currentArmy.hasUnitsToPlace() && enemyArmy.hasUnitsToPlace();
    }

    public int getTotalStrengthOfArmy() {
        return currentArmy.calculateTotalStrength();
    }
}


