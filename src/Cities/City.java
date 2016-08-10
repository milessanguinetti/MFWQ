package Cities;

import Characters.Inventory.Inventory;
import Profile.Game;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;

/**
 * Created by Spaghetti on 8/8/2016.
 */
//A location on the map where characters can generally buy and sell items and rest at an inn.
//Many scripted events also take place in cities.
public class City extends StackPane{
    protected StackPane Inn;
    protected StackPane Shop;
    private int numButtons; //number of buttons that this city has.
    private int currentButton;
    private cityButton [] buttons;
    private boolean enterDown = false;

    public City(StackPane inn, StackPane shop){
        setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(3000, 3000, Color.BLACK);
        getChildren().add(background);
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        if(inn != null){
            Inn = inn;
            buttonBox.getChildren().add(new innButton());
        }
        if(shop != null){
            Shop = shop;
            buttonBox.getChildren().add(new shopButton());
        }
        buttonBox.getChildren().add(new optionsButton());
        buttonBox.getChildren().add(new leaveButton());
        buttons = new cityButton[buttonBox.getChildren().size()];
        for(int i = 0; i < buttonBox.getChildren().size(); ++i){
            buttons[i] = (cityButton)buttonBox.getChildren().get(i);
        }
        currentButton = 0;
        buttons[currentButton].setSelected();
        buttons[currentButton].setHighLit();
        Rectangle buttonBackground1 = new Rectangle(320, numButtons*70, Color.GRAY);
        Rectangle buttonBackground2 = new Rectangle(315, numButtons*65, Color.BLACK);
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        buttonBackground1.setTranslateX(0 - bounds.getWidth()/3);
        buttonBackground2.setTranslateX(0 - bounds.getWidth()/3);
        buttonBox.setTranslateX(0-bounds.getWidth()/3);
        getChildren().addAll(buttonBackground1, buttonBackground2, buttonBox);
        Game.currentCity = this;
        setOnKeyPressed(event1 -> {
            if(event1.getCode() == KeyCode.ENTER){
                buttons[currentButton].setSelected();
            }
        });
        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                buttons[currentButton].performAction();
                buttons[currentButton].setUnselected();
            }
            else if(event.getCode() == KeyCode.ESCAPE){
                Game.mainmenu.getCurrentGame().addOptionsOverlay();
            }
            else if((event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) && currentButton < numButtons){
                buttons[currentButton].setPlain();
                ++currentButton;
                buttons[currentButton].setHighLit();
            }
            else if((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) && currentButton != 0){
                buttons[currentButton].setPlain();
                --currentButton;
                buttons[currentButton].setHighLit();
            }
        });
    }

    public void exit(){
        Game.currentCity = null;
        Game.mainmenu.getCurrentGame().swapToOverworld(this);
    }

    private abstract class cityButton extends StackPane{
        private int buttonNumber;
        private Text buttonText;
        protected Rectangle buttonShape;
        private LinearGradient buttonGradient;
        protected boolean isGrayedOut = false;

        public cityButton(String buttonname){
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
                if(buttons[currentButton] != null){
                    buttons[currentButton].setUnselected();
                    buttons[currentButton].setPlain();
                }
                currentButton = buttonNumber;
                setHighLit();
                if(enterDown)
                    setSelected();
            });

            setOnMouseExited(event1 -> {
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
            if(!isGrayedOut)
                buttonShape.setFill(Color.RED);
        }

        public void setUnselected(){
            if(!isGrayedOut)
                buttonShape.setFill(buttonGradient);
        }
    }

    private class shopButton extends cityButton{
        public shopButton(){
            super("Shop");
        }

        @Override
        public void performAction() {
            Inventory.setCanSell(true);
        }
    }

    private class innButton extends cityButton{
        public innButton(){
            super("Inn");
        }

        @Override
        public void performAction() {

        }
    }

    private class leaveButton extends cityButton{
        public leaveButton(){
            super("Leave");
        }

        @Override
        public void performAction(){
            Inventory.setCanSell(false);
            exit();
        }
    }

    private class optionsButton extends cityButton{
        public optionsButton(){
            super("Options");
        }

        @Override
        public void performAction() {
            Game.mainmenu.getCurrentGame().addOptionsOverlay();
        }
    }
}
