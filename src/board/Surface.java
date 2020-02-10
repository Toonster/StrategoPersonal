package board;

public enum Surface {

    WATER(false), GRASS(true);

    private final boolean accessible;

    Surface(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isAccessible() {
        return accessible;
    }
}
