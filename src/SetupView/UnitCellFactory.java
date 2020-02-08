package SetupView;

import army.unit.Unit;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class UnitCellFactory implements Callback<ListView<Unit>, ListCell<Unit>> {
    @Override
    public ListCell<Unit> call(ListView<Unit> unitListView) {
        return new UnitCell();
    }
}
