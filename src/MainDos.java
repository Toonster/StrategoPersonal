import army.Army;
import army.ArmyColor;
import army.unit.Unit;
import board.Board;
import board.Tile;
import common.Position;
import filemanager.FileManager;
import filemanager.GameData;
import player.Computer;
import player.Human;
import player.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainDos {
    public static void main(String[] args) {
        showUnitsToPlace();
        showMessage();
    }

    static Player currentPlayer = new Human();
    static Player enemyPlayer = new Computer();
    static Army currentArmy = new Army(ArmyColor.BLUE);
    static Army enemyArmy = new Army(ArmyColor.RED);
    static Board board = new Board();

/*    public void start() {
        showMessage("The game has started!");
        board.draw();
        while (currentArmy.hasUnitsToPlace() || enemyArmy.hasUnitsToPlace()) {
            setUpArmy();
            swapTurns();
        }
    }*/

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void showUnitsToPlace() {
        showMessage("Index. - Name - (Strength)");
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        for (int i = 0; i < unitsToPlace.size(); i++){
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public static void start(Army army) {
        army.giveStandardPosToUnits();
        update();
        drawBoard();
        swapTurns();
        while (currentArmy.hasUnitsToPlace()) {
            placeUnit();
            update();
        }
        draw();
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
    public void placeUnit(String unitIndex, String destination) {
        List<Unit> unitsToPlace = currentArmy.getUnitsToPlace();
        Unit selectedUnit = currentPlayer.selectUnitToPlace(unitsToPlace, unitIndex);
        Position unitDestination = currentPlayer.selectDestination();
        if (enemyArmy.hasUnitsToPlace()) {
            unitDestination.add(new Position(0, 6));
        }
        if (currentArmy.isAvailableStartingPosition(unitDestination)) {
            currentArmy.placeUnit(selectedUnit, unitDestination);
            return;
        }
    }

    public static void drawBoard() {
        Tile[][] gameField = board.getGameField();
        System.out.print(" - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                System.out.print(" | ");
                System.out.print(gameField[x][y]);
            }
            System.out.print(" |");
            System.out.println();
            System.out.print(" - - - - - - - - - - - - - - - - - - - - - ");
            System.out.println();
        }
    }

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

    public static void update() {
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
}
