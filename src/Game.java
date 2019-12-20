import army.*;
import army.unit.*;
import board.Board;
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
        update();
        draw();
        swapTurns();
        /*while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
        }*/
        setRandomPositions();
        update();
        board.draw();
        swapTurns();
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
/*
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
    }*/

    public void play() {
        while (!currentArmy.isDefeated() && !enemyArmy.isDefeated()) {
            processTurn();
            update();
            draw();
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

    public void draw() {
        System.out.println(enemyArmy.getDeadUnits());
        board.draw();
        System.out.println(currentArmy.getDeadUnits());
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
        System.out.println("Game loaded!");
    }

    public void loadArmyConfig() throws StrategoException {
        GameData data = loadDataFromFile("ArmyConfig.txt");
        this.currentArmy = data.getCurrentArmy();
        System.out.println("Army config loaded!");
    }

    public void setGame(GameData gameData) {
        currentPlayer = gameData.getCurrentPlayer();
        enemyPlayer = gameData.getEnemyPlayer();
        currentArmy = gameData.getCurrentArmy();
        enemyArmy = gameData.getEnemyArmy();
    }

    public GameData loadDataFromFile(String fileName) throws StrategoException {
        GameData state = null;
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

    public void setRandomPositions() {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        System.out.println(unitsToPlace.size());
        Collections.shuffle(unitsToPlace);
        System.out.println(unitsToPlace.size());
        int index = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 10; x++) {
                Unit unit = unitsToPlace.get(index++);
                    unit.place(new Position(x,y));
                    System.out.println(unit.getCharacter());
                    System.out.println(x + "," + y );
            }
        }
    }
}


