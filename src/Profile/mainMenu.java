package Profile;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles on 6/2/2015.
 */
public class mainMenu {
    private Game currentGame;
    static private int Selection;
    private menuButton newGame = new menuButton("NEW GAME", 0);
    private menuButton loadGame = new menuButton("LOAD GAME", 1);
    private menuButton Options = new menuButton("OPTIONS", 2);
    private menuButton Exit = new menuButton("EXIT", 3);
    private StackPane contentRoot;
    private boolean isMostRecentPane;
    //create a scene endemic to the menu

    public mainMenu(){
        Selection = 0;
        contentRoot = new StackPane();
        contentRoot.setAlignment(Pos.CENTER);
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/images/title.jpg"))){
            ImageView titlescrn = new ImageView(new Image(imginput));
            titlescrn.setFitWidth(1280);
            titlescrn.setFitHeight(800); //preserve aspect ratio
            contentRoot.getChildren().add(titlescrn);
        }

        catch (IOException e){
            System.out.println("Error loading title screen.");
        }

        menuContainer buttons = new menuContainer(newGame, loadGame, Options, Exit);

        contentRoot.getChildren().add(buttons);

        newGame.setHighLit(); //set newgame to highlit for starts.

        //key pressed listening code
        contentRoot.setOnKeyPressed(event -> {
            //ENTER CASE
            if (event.getCode() == KeyCode.ENTER) {
                intToButton(Selection).setSelected();
            }
        });

        //key released listening code
        contentRoot.setOnKeyReleased(event -> {
            //DOWN CASE
            if (event.getCode() == KeyCode.DOWN && Selection < 3 || event.getCode() == KeyCode.S && Selection < 3) {
                intToButton(Selection).setPlain();
                ++Selection;
                intToButton(Selection).setHighLit();
            }
            //UP CASE
            else if (event.getCode() == KeyCode.UP && Selection > 0 || event.getCode() == KeyCode.W && Selection > 0) {
                intToButton(Selection).setPlain();
                --Selection;
                intToButton(Selection).setHighLit();
            }
            //ENTER CASE
            else if (event.getCode() == KeyCode.ENTER) {
                intToButton(Selection).setUnselected();
                intToAction(Selection);
            }
        });
    }

    public boolean isMostRecentPane(){
        return isMostRecentPane;
    }

    //resets selection and other variables such that they're like they are at startup.
    public void Reset(){
        intToButton(Selection).setPlain();
        Selection = 0;
        intToButton(Selection).setHighLit();
    }

    public Pane getPane(){
        return contentRoot;
    }

    public void setGame(Game currentgame){
        currentGame = currentgame;
    }

    //class for housing buttons within the main menu.
    private static class menuContainer extends VBox{
        public menuContainer(menuButton... all){
            setAlignment(Pos.CENTER);
            getChildren().add(generateLine());
            //generate one line at the very top.

            //for all buttons that we will be adding...
            for(menuButton one : all){
                getChildren().addAll(one, generateLine());
            } //add the button as well as a spacing line
        }

        private Line generateLine(){
            Line line = new Line();
            line.setEndX(300);
            line.setStroke(Color.WHITE);
            return line;
        }
    }

    //class strictly for creating buttons in the menu.
    private static class menuButton extends StackPane{
        private Text buttonText;
        private int buttonVal;
        private Rectangle buttonShape;
        private LinearGradient buttonGradient;

        public menuButton(String buttonname, int buttonval){
            buttonVal = buttonval;
            buttonGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
            //add a reddened gradient to the menuButton item
                    new Stop(0, Color.BLACK),
                    new Stop(.2, Color.DARKRED),
                    new Stop(.8, Color.DARKRED),
                    new Stop(1, Color.BLACK)
            });

            buttonShape = new Rectangle(300, 50);
            buttonShape.setOpacity(.4);

            buttonText = new Text(buttonname); //initialize our text to the button's name
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));

            setMaxSize(300, 50);
            setAlignment(Pos.CENTER); //center the button's sub-components.
            getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();

            setOnMouseEntered(event -> {
                Game.mainmenu.intToButton(Selection).setPlain();
                setHighLit();
                Selection = buttonVal; //change selection to whatever has been entered.
            });

            setOnMouseClicked(event ->{
                setSelected();
            });

            setOnMouseReleased(event ->{
                setUnselected();
                Game.mainmenu.intToAction(buttonVal);
            });
        }

        public void setHighLit(){
            buttonShape.setFill(buttonGradient);
            buttonShape.setOpacity(.6);
            buttonText.setFill(Color.WHITE);
        }

        public void setPlain(){
            buttonShape.setFill(Color.BLACK);
            buttonShape.setOpacity(.4);
            buttonText.setFill(Color.GRAY);
        }

        public void setSelected(){
            buttonShape.setFill(Color.RED);
        }

        public void setUnselected(){
            buttonShape.setFill(buttonGradient);
        }
    }

    //converts an integer value to a menu button.
    public menuButton intToButton(int toConvert){
        if(toConvert == 0)
            return newGame;
        if(toConvert == 1)
            return loadGame;
        if(toConvert == 2)
            return Options;
        return Exit;
    }

    //performs an action based on an integer value
    public void intToAction(int toConvert){
        isMostRecentPane = false;
        intToButton(toConvert).setUnselected();
        if(toConvert == 0) {
            currentGame.newPlayer(); //new game
        }
        else if(toConvert == 1)
            currentGame.loadPlayer(); //load game
        else if(toConvert == 2) {
            isMostRecentPane = true;
            currentGame.swapToSettings(contentRoot);
        }
        else
            System.exit(0); //exit
    }

    public Game getCurrentGame(){
        return currentGame;
    }
}
