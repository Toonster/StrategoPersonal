import army.Army;
import player.Player;

import java.io.Serializable;

public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    private Player currentPlayer;
    private Player enemyPlayer;
    private Army currentArmy;
    private Army enemyArmy;

    public GameState(Player currentPlayer, Player enemyPlayer, Army currentArmy, Army enemyArmy) {
        this.currentPlayer = currentPlayer;
        this.enemyPlayer = enemyPlayer;
        this.currentArmy = currentArmy;
        this.enemyArmy = enemyArmy;
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }

    public Army getCurrentArmy() {
        return currentArmy;
    }

    public Army getEnemyArmy() { return enemyArmy; }
}
