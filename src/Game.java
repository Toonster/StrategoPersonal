import army.*;
import army.unit.*;
import board.Board;
import common.Position;
import player.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private Player currentPlayer;
    private Player enemyPlayer;
    private Army currentArmy;
    private Army enemyArmy;
    private Board board;

    public Game() {
        board = new Board();
        currentPlayer = new Human();
        enemyPlayer = new Computer();
        currentArmy = new Army("Blue");
        enemyArmy = new Army("Red");
    }

    public void start() {
        while (currentArmy.hasUnitsToPlace() || enemyArmy.hasUnitsToPlace()) {
            setUpArmy();
            swapTurns();
        }
    }

    public void setUpArmy() {
        if (!currentPlayer.useStandardArmyConfig()) {
            loadArmyConfig();
        }
        placeArmy();
    }

    public void placeArmy() {
        while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
            board.draw();
        }
    }

    public void placeUnit() {
            List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
            Unit selectedUnit = currentPlayer.selectUnitToPlace(unitsToPlace);
            Position unitDestination = currentPlayer.selectDestination();
            if (currentArmy.isFreeStartingPosition(unitDestination)) {
                if (enemyArmy.hasUnitsToPlace()) {
                    unitDestination.add(new Position(0,6));
                }
                currentArmy.placeUnit(selectedUnit, unitDestination);
            }
    }

    public void play() {
        while (!currentArmy.isDefeated() && !enemyArmy.isDefeated()) {
            processTurn();
            update();
            currentArmy.showDeadUnits();
            board.draw();
            swapTurns();
        }
    }

    public void processTurn() {
        Position position = currentPlayer.selectUnitPosition();
        Unit selectedUnit = currentArmy.getUnitAtPosition(position);
        Position destination = currentPlayer.selectDestination();
        if (board.isInBounds(destination) && selectedUnit.canMoveTo(destination) && board.tilesAreAvailable(selectedUnit.getPathTo(destination))) {
            if (board.tileIsAvailable(destination)){
                currentArmy.placeUnit(selectedUnit,position);
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
        enemyUnits.forEach(unit -> {if (!unit.isVisibleToEnemy()){ unit.setChar('X');}});
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

    public void save(String fileName){
        GameState state = new GameState(currentPlayer, enemyPlayer, currentArmy, enemyArmy);
        FileManager.write(state, fileName);
    }

    public void load(String fileName) {
        GameState state = null;
        try {
            state = (GameState) FileManager.read(fileName);
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
        loadGameState(state);
        System.out.println("Gamestate loaded!");
    }

    public void loadArmyConfig() {
        GameState state = null;
        try {
            state = (GameState) FileManager.read("ArmyConfig.txt");
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

    public void loadGameState(GameState gameState) {
        currentPlayer = gameState.getCurrentPlayer();
        enemyPlayer = gameState.getEnemyPlayer();
        currentArmy = gameState.getCurrentArmy();
        enemyArmy = gameState.getEnemyArmy();
    }
}


