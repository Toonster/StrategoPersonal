package board;

public class Surface {

    private boolean accessible;

    public Surface( boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isAccessible() {
        return accessible;
    }
}
