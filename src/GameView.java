import army.Army;
import army.unit.Unit;
import board.Board;
import board.Tile;

import java.util.List;

public class GameView {
    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void showUnitsToPlace(List<Unit> unitsToPlace) {
        showMessage("Index. - Name - (Strength)");
        for (int i = 0; i < unitsToPlace.size(); i++){
            System.out.printf("%d. - %s", i, unitsToPlace.get(i).toString());
        }
    }

    public static void showWinner (Army army) {
        System.out.println("The game has ended!");
        System.out.printf("The %s army won, congratulations!\n", army.getColor());
    }

    public static void drawBoard (Board board) {
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

    public static void showDeadUnits(List<Unit> deadUnits) {
        System.out.println(deadUnits);
    }
}
