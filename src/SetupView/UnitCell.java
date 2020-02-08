package SetupView;

import army.unit.Unit;
import javafx.scene.control.ListCell;

public class UnitCell extends ListCell<Unit> {
    @Override
    protected void updateItem(Unit item, boolean empty) {
        super.updateItem(item, empty);

        int index = this.getIndex();
        String name = null;

        // Format name
        if (item == null || empty)
        {
            this.setText(null);
        }
        else
        {
            name =  (index + 1) + ". " + item.getRank().name();
        }

        this.setText(name);
    }
}
