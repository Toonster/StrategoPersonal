package board;

import army.unit.Unit;

public class Tile {

    private Unit unit;
    private final Surface surface;

    public Tile(Surface surface) {
        this.surface = surface;
    }

    public void update(Unit unit) {
        this.unit = unit;
    }

    public void clear() {
        unit = null;
    }

    public boolean isOccupied() {
        return unit != null;
    }

    public boolean isAccessible() {
        return this.surface.isAccessible();
    }

    public Unit getUnit() {
        return unit;
    }
}
