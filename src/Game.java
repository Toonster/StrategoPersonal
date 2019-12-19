import army.*;
import army.unit.*;
import board.Board;
import common.Position;
import filemanager.FileManager;
import filemanager.GameData;
import player.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Game {

    private Player currentPlayer;
    private Player enemyPlayer;
    public Army currentArmy;
    private Army enemyArmy;
    private Board board;

    public Game() {
        board = new Board();
        currentPlayer = new Human();
        enemyPlayer = new Computer();
        currentArmy = new Army("Blue");
        enemyArmy = new Army("Red");
    }

/*    public void start() {
        board.draw();
        while (currentArmy.hasUnitsToPlace() || enemyArmy.hasUnitsToPlace()) {
            setUpArmy();
            swapTurns();
        }
    }*/

    public void start() {
        currentArmy.giveStandardPosToUnits();
        swapTurns();
        while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
        }
        board.draw();
    }

    public void setUpArmy() {
        /*if (currentPlayer.useStandardArmyConfig()) {
            loadArmyConfig();
            return;
        }*/
        placeArmy();
    }

    public void placeArmy() {
        while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
            if (currentPlayer instanceof Human) {
                board.draw();
            }
        }
    }

    public void placeUnit() {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        Unit selectedUnit = currentPlayer.selectUnitToPlace(unitsToPlace);
        Position unitDestination = currentPlayer.selectDestination();
        if (enemyArmy.hasUnitsToPlace()) {
            unitDestination.add(new Position(0, 6));
        }
        if (currentArmy.isAvailableStartingPosition(unitDestination)) {
            currentArmy.placeUnit(selectedUnit, unitDestination);
            return;
        }
        if (currentPlayer instanceof Human) {
            System.out.println("Invalid destination, retry!");
        }
    }

    public void play() {
        while (!currentArmy.isDefeated() && !enemyArmy.isDefeated()) {
            processTurn();
            update();
            enemyArmy.showDeadUnits();
            board.draw();
            currentArmy.showDeadUnits();
            swapTurns();
        }
    }

    public void processTurn() {
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

    public void end() {
        System.out.println("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", enemyArmy.getColor());
    }

    public void update() {
        board.clear();
        board.update(currentArmy.getUnits());
        List<Unit> enemyUnits = enemyArmy.getUnits();
        enemyUnits.forEach(unit -> {
            if (!unit.isVisibleToEnemy()) {
                unit.setChar('X');
            }
        });
        board.update(enemyUnits);
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

    public void load(String fileName) {
        filemanager.GameData state = null;
        try {
            state = (filemanager.GameData) filemanager.FileManager.read(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
        if (state == null) {
            System.out.println("Couldn't load gameState");
            return;
        }
        loadGame(state);
        System.out.println("Gamestate loaded!");
    }

    public void loadArmyConfig() {
        filemanager.GameData state = null;
        try {
            state = (filemanager.GameData) filemanager.FileManager.read("ArmyConfig.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
        if (state == null) {
            System.out.println("Couldn't load army configuration");
            return;
        }
        this.currentArmy = state.getCurrentArmy();
        System.out.println("Army config loaded!");
    }

    public void loadGame(GameData gameData) {
        currentPlayer = gameData.getCurrentPlayer();
        enemyPlayer = gameData.getEnemyPlayer();
        currentArmy = gameData.getCurrentArmy();
        enemyArmy = gameData.getEnemyArmy();
    }

    public Army getCurrentArmy() {
        return currentArmy;
    }
}


