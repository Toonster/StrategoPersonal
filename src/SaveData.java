import army.Army;
import player.Player;

import java.io.Serializable;

public class SaveData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Player currentPlayer;
    private Player enemyPlayer;
    private Army currentArmy;
    private Army enemyArmy;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }

    public Army getCurrentArmy() {
        return currentArmy;
    }

    public Army getEnemyArmy() {
        return enemyArmy;
    }
}
