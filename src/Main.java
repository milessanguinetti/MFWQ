import Profile.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;

public class Main extends Application{

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception{
        Game newGame = new Game(primaryStage);
    }
}
