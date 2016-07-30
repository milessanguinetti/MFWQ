package Profile;

import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Spaghetti on 7/29/2016.
 */
//a class that serves as an overlay on top of the map or overworld with buttons that allow the player
//to access things like the inventory and character screen or simply quit the game.
public class optionsOverlay extends StackPane{
    private static optionsButton current;
    private static int currentButton = 0;
    private static int numButtons = 0;
    private static VBox buttonBox;
    private static boolean Enterdown = false;

    //constructor.
    public optionsOverlay(){
        setAlignment(Pos.CENTER);
        Rectangle backdrop = new Rectangle(3000, 3000);
        backdrop.setFill(Color.BLACK);
        backdrop.setOpacity(.3);
        Rectangle background = new Rectangle(350, 450);
        background.setFill(Color.BLACK);
        Text Title = new Text("Options");
        Title.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));
        Title.setFill(Color.GOLDENROD);
        Title.setTranslateY(-180);
        buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(new partyButton(), new inventoryButton(), new settingsButton(),
                new backButton(), new saveButton(), new exitButton());
        buttonBox.setTranslateY(30);
        getChildren().addAll(backdrop, background, Title, buttonBox);

        setOnKeyPressed(event1 -> {
            if(event1.getCode() == KeyCode.ENTER && current != null) {
                Enterdown = true;
                current.setSelected();
            }
        });

        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                if(currentButton != 0) {
                    if(current != null) {
                        current.setUnselected();
                        current.setPlain();
                    }
                    --currentButton;
                    current = (optionsButton)buttonBox.getChildren().get(currentButton);
                    current.setHighLit();
                    if(Enterdown)
                        current.setSelected();
                }
            }
            else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                if(currentButton != numButtons-1){
                    if(current != null) {
                        current.setUnselected();
                        current.setPlain();
                    }
                    ++currentButton;
                    current = (optionsButton)buttonBox.getChildren().get(currentButton);
                    current.setHighLit();
                    if(Enterdown)
                        current.setSelected();
                }
            }
            else if(event.getCode() == KeyCode.ENTER){
                Enterdown = false;
                if(current != null) {
                    current.performAction();
                }
            }
            else if(event.getCode() == KeyCode.ESCAPE){
                Game.mainmenu.getCurrentGame().removeOptionsOverylay();
            }
        });
    }

    public static void Reset(){
        if(current != null)
                current.setPlain();
        current = (optionsButton) buttonBox.getChildren().get(0);
        current.setHighLit();
        currentButton = 0;
        Enterdown = false;
    }

    //class strictly for creating buttons in the menu.
    private abstract static class optionsButton extends StackPane{
        private int buttonNumber;
        private Text buttonText;
        protected Rectangle buttonShape;
        private LinearGradient buttonGradient;

        public optionsButton(String buttonname){
            buttonNumber = numButtons;
            ++numButtons;
            setMaxSize(300, 50);

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

            setAlignment(Pos.CENTER); //center the button's sub-components.
            getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();

            setOnMouseEntered(event -> {
                if(current != null){
                    current.setUnselected();
                    current.setPlain();
                }
                current = this;
                currentButton = buttonNumber;
                setHighLit();
                if(Enterdown)
                    setSelected();
            });

            setOnMouseExited(event1 -> {
                if(current == this)
                    current = null;
                setUnselected();
                setPlain();
            });

            setOnMousePressed(event ->{
                setSelected();
            });

            setOnMouseReleased(event ->{
                performAction();
                setUnselected();
                setPlain();
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

        public abstract void performAction();

        public void setSelected(){
            buttonShape.setFill(Color.RED);
        }

        public void setUnselected(){
            buttonShape.setFill(buttonGradient);
        }
    }

    private class exitButton extends optionsButton{
        public exitButton(){
            super("Exit");
        }

        @Override
        public void performAction(){
            setUnselected();
            Game.mainmenu.getCurrentGame().removeOptionsOverylay();
            if(Game.currentMap == null)
                Game.mainmenu.getCurrentGame().swapToMainMenu(Game.overworld);
            else
                Game.mainmenu.getCurrentGame().swapToMainMenu(Game.currentMap.getPane());
        }
    }

    private class settingsButton extends optionsButton{
        public settingsButton(){
            super("Settings");
        }

        @Override
        public void performAction(){
            setUnselected();
            Game.mainmenu.getCurrentGame().removeOptionsOverylay();
            if(Game.currentMap == null)
                Game.mainmenu.getCurrentGame().swapToSettings(Game.overworld);
            else
                Game.mainmenu.getCurrentGame().swapToSettings(Game.currentMap.getPane());
        }
    }

    private class backButton extends optionsButton{
        public backButton(){
            super("Back");
        }

        @Override
        public void performAction(){
            setUnselected();
            Game.mainmenu.getCurrentGame().removeOptionsOverylay();
        }
    }

    private class partyButton extends optionsButton{
        public partyButton(){
            super("Party");
        }

        @Override
        public void performAction(){
            setUnselected();
            /*
            Game.mainmenu.getCurrentGame().removeOptionsOverylay();
            if(Game.currentMap == null)
                Game.mainmenu.getCurrentGame().
            else
                Game.mainmenu.getCurrentGame().*/
        }
    }

    private class inventoryButton extends optionsButton{
        public inventoryButton(){
            super("Inventory");
        }

        @Override
        public void performAction(){
            setUnselected();
            Game.mainmenu.getCurrentGame().removeOptionsOverylay();
            if(Game.currentMap == null)
                Game.mainmenu.getCurrentGame().swapToInventory(Game.overworld);
            else
                Game.mainmenu.getCurrentGame().swapToInventory(Game.currentMap.getPane());
        }
    }

    private class saveButton extends optionsButton{
        public saveButton(){
            super("Save");
        }

        @Override
        public void performAction(){
            setUnselected();
            if(Game.currentMap == null)
                Game.mainmenu.getCurrentGame().writeToDisk();
            else
                buttonShape.setFill(Color.RED);

        }
    }
}
