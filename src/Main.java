import Profile.Game;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception{
        Game newGame = new Game(primaryStage);
    }
}
