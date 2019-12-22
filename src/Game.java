import army.*;
import army.unit.*;
import board.Board;
import board.Tile;
import common.Position;
import filemanager.FileManager;
import filemanager.GameData;
import player.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Game implements Serializable {

    private Player currentPlayer;
    private Player enemyPlayer;
    private Army currentArmy;
    private Army enemyArmy;
    private Board board;
    private GameView view;

    public Game() {
        board = new Board();
        currentPlayer = new Human();
        enemyPlayer = new Computer();
        currentArmy = new Army(ArmyColor.BLUE);
        enemyArmy = new Army(ArmyColor.RED);
    }

    public void start() {
        view.drawBoard(board.getGameField());
        while (currentArmy.hasUnitsToPlace() || enemyArmy.hasUnitsToPlace()) {
            setUpArmy();
            swapTurns();
        }
    }

/*    public void start() {
        currentArmy.giveStandardPosToUnits();
        update();
        swapTurns();
        while (currentArmy.hasUnitsToPlace()) {
*//*            placeUnit();*//*
            update();
        }
    }*/

 /*   public void setUpArmy() {
        if (currentPlayer.useStandardArmyConfig()) {
            loadArmyConfig();
            return;
        }
        placeArmy();
    }*/

   /* public void placeArmy() {
        while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
            if (currentPlayer instanceof Human) {
                board.draw();
            }
        }
    }*/
    public void placeUnit(Unit selectedUnit, int x, int y) {
        Position unitDestination = new Position(x, y);
        if (currentArmy.isAvailableStartingPosition(unitDestination)) {
            currentArmy.placeUnit(selectedUnit, unitDestination);
        }
    }

    public void play() {
        while (!currentArmy.isDefeated() && !enemyArmy.isDefeated()) {
            processTurn();
            update();
            swapTurns();
        }
    }

    public void humanPlaceArmy() {

    }

    public void humanMoveUnit() {
        Position position = currentPlayer.selectUnitPosition();
        Unit selectedUnit = currentArmy.getUnitAtPosition(position);
        Position destination = currentPlayer.selectDestination();
        if (board.isInBounds(destination) && selectedUnit.canMoveTo(destination) && board.tilesAreAvailable(selectedUnit.getPathTo(destination))) {
            if (board.tileIsAvailable(destination)) {
                currentArmy.placeUnit(selectedUnit, position);
                return;
            }
            if (enemyArmy.hasUnitAtPosition(destination)) {
                selectedUnit.battle(enemyArmy.getUnitAtPosition(destination));
            }
        }
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
        Player tempPlayer = currentPlayer;
        Army tempArmy = currentArmy;
        currentPlayer = enemyPlayer;
        currentArmy = enemyArmy;
        enemyPlayer = tempPlayer;
        enemyArmy = tempArmy;
    }

    public void save(String fileName) {
        GameData state = new GameData(currentPlayer, currentArmy, enemyArmy);
        FileManager.write(state, fileName);
    }

    public void loadArmyConfig() throws StrategoException {
        GameData data = loadDataFromFile("ArmyConfig.txt");
        this.currentArmy = data.getCurrentArmy();
    }

    public GameData loadDataFromFile(String fileName) throws StrategoException {
        GameData state;
        try {
            state = (filemanager.GameData) filemanager.FileManager.read(fileName);
        } catch (FileNotFoundException e) {
            throw new StrategoException("Caught FileNotFoundException");
        } catch (IOException e) {
            throw new StrategoException("Caught IOException");
        } catch (Exception e) {
            throw new StrategoException("Caught Exception");
        }
        if (state == null) {
            throw new StrategoException("Data is null");
        }
        return state;
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
}


