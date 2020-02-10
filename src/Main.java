import SetupView.*;
import game.Game;
import gameView.GamePresenter;
import gameView.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SetupView setupView = new SetupView();
        GameView gameView = new GameView();
        Game game = new Game();
        GamePresenter presenter = new GamePresenter(gameView, game);
        Scene scene = new Scene(presenter.getView());
        stage.setTitle("Stratego");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
