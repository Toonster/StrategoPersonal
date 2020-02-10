package SetupView;

import army.unit.Unit;
import common.Position;
import game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class SetupPresenter {

    private SetupView view;
    private Game model;

    public SetupPresenter(SetupView view, Game model) {
        this.view = view;
        this.model = model;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        for (Node btn : view.getBoard().getChildren()) {
            if (GridPane.getRowIndex(btn) > 5) {
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int x = GridPane.getColumnIndex(btn);
                        int y = GridPane.getRowIndex(btn);
                        Unit unitToPlace = view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem();
                        model.placeUnit(unitToPlace, new Position(x, y));
                        ((Button) btn).setText(view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem().getRank().name());
                        updateView();
                    }
                });
            }
        }
    }

    public void updateView() {
        ListView<Unit> units = view.getListOfUnplacedUnits();
        ObservableList<Unit> obsList = FXCollections.observableArrayList(model.getArmyUnitsToPlace());
        units.setItems(obsList);
    }

    public SetupView getView() {
        return view;
    }
}
