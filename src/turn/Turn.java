package turn;

import army.unit.Unit;

public class Turn {

    TurnType turnType;
    Unit selectedUnit;

    public Turn(TurnType turnType) {
        this.turnType = turnType;
    }

    public TurnType getTurnType() {
        return turnType;
    }

    public void setTurnType(TurnType turnType) {
        this.turnType = turnType;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }
}
