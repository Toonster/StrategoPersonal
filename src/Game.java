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
import java.util.Collections;
import java.util.List;

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
        currentArmy = new Army(ArmyColor.BLUE);
        enemyArmy = new Army(ArmyColor.RED);
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
        update();
        swapTurns();
        while (currentArmy.hasUnitsToPlace()) {
/*            placeUnit();*/
            update();
        }
    }

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
    public void placeUnit(int index, int x, int y) {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        Unit selectedUnit = unitsToPlace.get(index);
        Position unitDestination = new Position(x,y);
        if (enemyArmy.hasUnitsToPlace()) {
            unitDestination.add(new Position(0, 6));
        }
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

    public void load(String fileName) throws StrategoException {
        GameData data = loadDataFromFile(fileName);
        setGame(data);
    }

    public void loadArmyConfig() throws StrategoException {
        GameData data = loadDataFromFile("ArmyConfig.txt");
        this.currentArmy = data.getCurrentArmy();
    }

    public void setGame(GameData gameData) {
        currentPlayer = gameData.getCurrentPlayer();
        enemyPlayer = gameData.getEnemyPlayer();
        currentArmy = gameData.getCurrentArmy();
        enemyArmy = gameData.getEnemyArmy();
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
}


