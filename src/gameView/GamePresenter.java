package gameView;

import game.Game;

public class GamePresenter {

    GameView view;
    Game model;

    public GamePresenter(GameView view, Game model) {
        this.view = view;
        this.model = model;
    }

    public GameView getView() {
        return view;
    }
}
