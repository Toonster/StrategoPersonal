/*
package filemanager;

import army.Army;
import player.Computer;
import player.Human;
import player.Player;

import java.io.Serializable;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currentPlayerType;
    private Army currentArmy;
    private Army enemyArmy;

    public GameData(Player currentPlayer, Army currentArmy, Army enemyArmy) {
        this.currentPlayerType = currentPlayer.getClass().getSimpleName();
        this.currentArmy = currentArmy;
        this.enemyArmy = enemyArmy;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerType.equalsIgnoreCase("human")) {
            return new Human();
        }
        return new Computer();
    }

    public Player getEnemyPlayer() {
        if (currentPlayerType.equalsIgnoreCase("computer")) {
            return new Human();
        }
        return new Computer();
    }

    public Army getCurrentArmy() {
        return currentArmy;
    }

    public Army getEnemyArmy() {
        return enemyArmy;
    }

}
*/
