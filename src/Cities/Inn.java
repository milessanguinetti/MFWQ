package Cities;

import Characters.Inventory.Inventory;
import Characters.playerCharacter;
import Profile.Game;
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
import javafx.scene.text.TextAlignment;

/**
 * Created by Spaghetti on 8/15/2016.
 */
public class Inn extends StackPane {
    private VBox buttonBox;
    private int currentButton;
    private static boolean enterDown;
    private int Price;
    private Text goldText;

    public Inn(String name, int price){
        setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(3000, 3000, Color.BLACK);
        getChildren().add(background);
        Text Name = new Text(name);
        Name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 50));
        Name.setFill(Color.WHITE);
        Name.setTranslateY(-320);
        getChildren().add(Name);
        Price = price;


        Rectangle buttonBackground1 = new Rectangle(320, 210, Color.GRAY);
        buttonBackground1.setTranslateX(-550);
        Rectangle buttonBackground2 = new Rectangle(315, 195, Color.BLACK);
        buttonBackground2.setTranslateX(-550);
        getChildren().addAll(buttonBackground1, buttonBackground2);

        buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.setTranslateY(-30);
        buttonBox.setTranslateX(-550);
        buttonBox.getChildren().addAll(new restButton(), new leaveButton());
        getChildren().add(buttonBox);

        Text priceText = new Text("Price: " + Price + "g");
        priceText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 20));
        priceText.setFill(Color.WHITE);
        priceText.setTextAlignment(TextAlignment.CENTER);
        priceText.setWrappingWidth(150);
        priceText.setTranslateX(-475);
        priceText.setTranslateY(50);
        int playercoins = Game.Player.getCoins();
        goldText = new Text("Gold: " + playercoins + "g");
        goldText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 20));
        if(playercoins >= Price)
            goldText.setFill(Color.WHITE);
        else
            goldText.setFill(Color.RED);
        goldText.setTextAlignment(TextAlignment.CENTER);
        goldText.setWrappingWidth(150);
        goldText.setTranslateX(-625);
        goldText.setTranslateY(50);
        getChildren().addAll(goldText, priceText);

        setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                enterDown = true;
            }
        });
        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                ((innButton)buttonBox.getChildren().get(currentButton)).performAction();
                enterDown = false;
                setCurrentButtonSelected();
            }
            if(event.getCode() == KeyCode.ESCAPE){
                setCurrentButtonUnselected();
                Leave();
            }
            if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){
                if(currentButton == 1){
                    setCurrentButtonUnselected();
                    --currentButton;
                    setCurrentButtonSelected();
                }
            }
            if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){
                if(currentButton == 0){
                    setCurrentButtonUnselected();
                    ++currentButton;
                    setCurrentButtonSelected();
                }
            }
        });
    }

    private void Rest(){
        if(Game.Player.hasCoins(Price)) {
            playerCharacter[] party = Game.Player.getParty();
            for (int i = 0; i < party.length; ++i) {
                if (party[i] != null) {
                    party[i].clearStatus();
                    party[i].fullyHeal();
                }
            }
            Game.Player.subtractCoins(Price);
            int playercoins = Game.Player.getCoins();
            if(playercoins >= Price)
                goldText.setFill(Color.WHITE);
            else
                goldText.setFill(Color.RED);
            goldText.setText("Gold: " + playercoins + "g");
        }
    }

    private void Leave(){
        Game.currentCity.getChildren().remove(this);
        Game.currentCity.returnFromSubpane();
    }

    private void setCurrentButtonUnselected(){
        innButton toUnselect = ((innButton)buttonBox.getChildren().get(currentButton));
        toUnselect.setUnselected();
        toUnselect.setPlain();
    }

    public void setCurrentButtonSelected(){
        if(enterDown){
            ((innButton)buttonBox.getChildren().get(currentButton)).setHighLit();
            ((innButton)buttonBox.getChildren().get(currentButton)).setSelected();
        }
        else{
            ((innButton)buttonBox.getChildren().get(currentButton)).setHighLit();
        }
    }

    private abstract class innButton extends StackPane{
        private int buttonNumber;
        private Text buttonText;
        protected Rectangle buttonShape;
        private LinearGradient buttonGradient;

        public innButton(String buttonname, int buttonnumber){
            buttonNumber = buttonnumber;
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
                setCurrentButtonUnselected();
                currentButton = buttonNumber;
                setHighLit();
                if(enterDown)
                    setSelected();
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

    private class restButton extends innButton{
        private restButton(){
            super("Rest", 0);
        }

        @Override
        public void performAction() {
            Rest();
        }
    }

    private class leaveButton extends innButton{
        private leaveButton(){
            super("Leave", 1);
        }

        @Override
        public void performAction() {
            Leave();
        }
    }
}


