package SetupView;

import army.Army;
import army.unit.Unit;
import common.Position;
import game.Game;
import Exception.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class SetupPresenter {

    private SetupView view;
    /*    private Army model;*/
    private Game game;

    public SetupPresenter(SetupView view, Game game) {
        this.view = view;
        /*this.model = model;*/
        this.game = game;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
       /* for (Node btn : view.getBoard().getChildren()) {
            if (GridPane.getRowIndex(btn) > 5) {
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int x = GridPane.getColumnIndex(btn);
                        int y = GridPane.getRowIndex(btn);
                        Unit unitToPlace = view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem();
                        try {
                            game.placeUnit(unitToPlace, new Position(x, y));
                            ((Button) btn).setText(view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem().getRank().name());
                        } catch (StrategoException e) {
                            e.printStackTrace();
                        }
                        updateView();
                    }
                });
            }
        }*/
        for (Node btn : view.getBoard().getChildren()) {
            if (GridPane.getRowIndex(btn) > 5) {
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int x = GridPane.getColumnIndex(btn);
                        int y = GridPane.getRowIndex(btn);
                        Unit placedUnit = game.getUnitAtPositionOfArmy(new Position(x, y));
                        Unit unitToPlace = view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem();
                        if (placedUnit != null) {
                            game.placeUnit(placedUnit, null);
                        }
                        game.placeUnit(unitToPlace, new Position(x, y));
                        ((Button) btn).setText(view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem().getRank().name());
                        updateView();
                    }
                });
            }
        }
    }

    public void updateView() {
        ListView<Unit> units = view.getListOfUnplacedUnits();
        ObservableList<Unit> obsList = FXCollections.observableArrayList(game.getArmyUnitsToPlace());
        units.setItems(obsList);
        /*units.getItems().addAll(model.getUnitsToPlace());
        units.setCellFactory(new UnitCellFactory());*/
    }

    public SetupView getView() {
        return view;
    }
}
