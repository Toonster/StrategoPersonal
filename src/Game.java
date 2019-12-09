import army.Army;
import board.Board;
import common.Position;
import player.Computer;
import player.Human;
import player.Player;
import army.unit.Unit;

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
        currentArmy = new Army("Blue");
        enemyArmy = new Army("Red");
    }

    public void start() {
        while (!currentArmy.allUnitsPlaced() && !enemyArmy.allUnitsPlaced()) {
            placeArmy();
            swapTurns();
        }
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
            if (currentArmy.isStartingAvailablePositionAvailable(unitDestination)) {
                if (!enemyArmy.allUnitsPlaced()) {
                    unitDestination.add(new Position(0,6));
                }
                currentArmy.placeUnit(selectedUnit, unitDestination);
            }
    }

/*    public void placeUnitOfArmyAtStart(List<unit.Unit> unitsToPlace) {
        unit.Unit selectedUnit = currentPlayer.selectUnitToPlace(unitsToPlace);
        common.Position unitDestination = currentPlayer.selectDestination();
        List<common.Position> availableStartingPositions = currentArmy.getAvailableStartingPositions();
        if (availableStartingPositions.contains(unitDestination)) {
            currentArmy.placeUnit(selectedUnit, unitDestination);
            availableStartingPositions.remove(unitDestination);
            unitsToPlace.remove(selectedUnit);
        }
    }*/

    public void play() {
        while (!currentArmy.isDefeated() && !enemyArmy.isDefeated()) {
            processTurn();
            update();
            board.draw();
            swapTurns();
        }
    }

    public void processTurn() {
        Position position = currentPlayer.selectUnitPosition();
        Unit selectedUnit = currentArmy.getUnitAtPosition(position);
        Position destination = currentPlayer.selectDestination();
        if (board.isInBounds(destination) && selectedUnit.canMoveTo(destination)) {
            if (selectedUnit.isScout() && !board.tilesAreFree(selectedUnit.getDestinationPath(destination))) {
                return;
            }
            if (board.tileIsFree(destination)){
                currentArmy.placeUnit(selectedUnit,position);
                return;
            }
            if (enemyArmy.hasUnitAtPosition(destination)) {
                selectedUnit.battle(enemyArmy.getUnitAtPosition(destination));
            }
        }
    }

    public void end() {

    }

    public void update() {
        board.update(currentArmy.getUnits());
        board.update(enemyArmy.getUnits());
    }

    public void swapTurns() {
        Player tempPlayer = currentPlayer;
        Army tempArmy = currentArmy;
        currentPlayer = enemyPlayer;
        currentArmy = enemyArmy;
        enemyPlayer = tempPlayer;
        enemyArmy = tempArmy;
    }
}

(6,0) (0 ,0 )


