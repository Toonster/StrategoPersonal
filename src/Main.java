import army.Army;
import player.Human;
import player.Player;

public class Main {
    public static void main(String[] args) {
        Player player = new Human();
        Army army = new Army("red");
        player.selectUnitToPlace(army.getUnitsToPlace());

    }
}
