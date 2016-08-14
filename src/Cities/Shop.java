package Cities;

import Characters.Inventory.Consumable;
import Characters.Inventory.Inventory;
import Characters.Inventory.Item;
import Profile.Game;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
import javafx.util.Duration;
import sun.security.krb5.internal.crypto.Des;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Spaghetti on 8/8/2016.
 */
public class Shop extends StackPane{
    private VBox itemContainers;
    private VBox shopButtons;
    private GridPane clickablepane;
    private int currentContainer = 0;
    private int currentButton;
    private static itemContainer selectedContainer;
    private static StackPane Description;
    private static Shop currentShop;
    private static boolean enterDown;

    public Shop(String name, Item ... Items){
        setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(3000, 3000, Color.BLACK);
        getChildren().add(background);
        Text Name = new Text(name);
        Name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 50));
        Name.setFill(Color.WHITE);
        Name.setTranslateY(-320);
        getChildren().add(Name);

        Rectangle buttonBackground1 = new Rectangle(320, 210, Color.GRAY);
        buttonBackground1.setTranslateX(-550);
        Rectangle buttonBackground2 = new Rectangle(315, 195, Color.BLACK);
        buttonBackground2.setTranslateX(-550);
        getChildren().addAll(buttonBackground1, buttonBackground2);

        clickablepane = new GridPane();
        clickablepane.setAlignment(Pos.CENTER);
        getChildren().add(clickablepane);

        shopButtons = new VBox();
        shopButtons.setAlignment(Pos.CENTER);
        shopButtons.setSpacing(15);
        shopButtons.setTranslateX(-550);
        shopButtons.getChildren().addAll(new buyButton(), new sellButton(), new leaveButton());

        itemContainers = new VBox();
        itemContainers.setAlignment(Pos.CENTER);
        itemContainers.setSpacing(160);
        itemContainers.setTranslateY(100);
        itemContainers.setTranslateX(-50);
        clickablepane.getChildren().addAll(shopButtons, itemContainers);
        clickablepane.toFront();
        for(int i = 0; i < Items.length; ++i){
            if(i%(Math.ceil(Items.length/2f)) == 0){
                selectedContainer = new itemContainer();
                itemContainers.getChildren().add(selectedContainer);
            }
            selectedContainer.addItem(Items[i]);
        }
        setOnKeyPressed(event1 -> {
            if(event1.getCode() == KeyCode.ENTER)
                enterDown = true;
        });
        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                if(Description != null && currentContainer != 0) {
                    selectedContainer.getCurrent().setPlain();
                    --currentContainer;
                    selectedContainer = (itemContainer) itemContainers.getChildren().get(currentContainer);
                    selectedContainer.getCurrent().setHighLit();
                }
                else if(Description == null && currentButton != 0){
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setPlain();
                    --currentButton;
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setHighLit();
                }
            }
            else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
                if(selectedContainer.current != 0){
                    for(int i = selectedContainer.current - 1; i >= 0; --i){
                        if(((itemBox)selectedContainer.getChildren().get(i)).Quantity != 0){
                            selectedContainer.setCurrent(i);
                            break;
                        }
                    }
                }
                else{
                    selectedContainer.getCurrent().setPlain();
                    ((shopButton)shopButtons.getChildren().get(0)).setHighLit();
                    currentButton = 0;
                    getChildren().remove(Description);
                    Description = null;
                }
            }
            else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                if(Description != null && currentContainer != itemContainers.getChildren().size()-1) {
                    selectedContainer.getCurrent().setPlain();
                    ++currentContainer;
                    selectedContainer = (itemContainer) itemContainers.getChildren().get(currentContainer);
                    selectedContainer.getCurrent().setHighLit();
                }
                else if(Description == null && currentButton != 2){
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setPlain();
                    ++currentButton;
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setHighLit();
                }
            }
            else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                if(Description != null) {
                    if (selectedContainer.current != selectedContainer.itemSum - 1) {
                        for (int i = selectedContainer.current + 1; i < selectedContainer.itemSum; ++i) {
                            if (((itemBox) selectedContainer.getChildren().get(i)).Quantity != 0) {
                                selectedContainer.setCurrent(i);
                                break;
                            }
                        }
                    }
                }
                else{
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setUnselected();
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setPlain();
                    ((itemContainer)itemContainers.getChildren().get(currentContainer)).getCurrent().setPlain();
                    currentButton = 0;
                    currentContainer = 0;
                    selectedContainer = (itemContainer) itemContainers.getChildren().get(0);
                    selectedContainer.setCurrent(0);
                }
            }
            else if(event.getCode() == KeyCode.ENTER) {
                if(Description == null){
                    ((shopButton)shopButtons.getChildren().get(currentButton)).performAction();
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setPlain();
                    ((shopButton)shopButtons.getChildren().get(currentButton)).setUnselected();
                }
                else {
                    enterDown = false;
                    BuyCurrent();
                }
            }
            else if(event.getCode() == KeyCode.ESCAPE){
                Game.currentCity.getChildren().remove(this); //remove this pane from its city's pane
                Inventory.setCanSell(false); //ensure that the player can no longer sell
                selectedContainer.getCurrent().setPlain();
            }
        });
    }

    public void Enter(){
        requestFocus();
        currentShop = this;
        currentContainer = 0;
        selectedContainer = (itemContainer) itemContainers.getChildren().get(0);
        selectedContainer.setCurrent(0);
    }

    public static void BuyCurrent() {
        if (selectedContainer.getCurrent().Quantity == 0)
            return;
        if (selectedContainer.getCurrent().Contents.getValue() * 1.5 <= Game.Player.getCoins()) {
            Game.Player.addCoins(Math.round(selectedContainer.getCurrent().Contents.getValue() * -1.5f));
            Game.battle.playMedia("loot");
            Game.Player.Insert(selectedContainer.getCurrent().Contents);
            selectedContainer.getCurrent().decrementQuantity();
        }
    }

    //subclasses for notifications
    //class for displaying loot graphics.
    private class itemContainer extends HBox {
        private int itemSum;
        private int current = 0;

        public itemContainer() {
            itemSum = 0;
            setAlignment(Pos.CENTER);
            setSpacing(10);
        }

        public boolean addItem(Item toAdd){
            itemBox toadd = new itemBox(toAdd, itemSum, this);
            getChildren().add(toadd);
            ++itemSum;

            if(itemSum == 4) //acknowledge that this item container is full
                return true;
            return false;
        }

        public itemBox getCurrent(){
            return (itemBox) this.getChildren().get(current);
        }

        public void setCurrent(int toset){
            selectedContainer.getCurrent().setPlain();
            if (selectedContainer != this) {
                selectedContainer = this;
                for(int i = 0; i < itemContainers.getChildren().size(); ++i){
                    if(itemContainers.getChildren().get(i) == this)
                        currentContainer = i;
                }
            }
            ((itemBox) getChildren().get(toset)).setHighLit();
            current = toset;
        }
    }

    //class strictly for holding a loot icon
    private static class itemBox extends StackPane {
        private Rectangle highlit;
        private ImageView backGround = null;
        private ImageView Icon;
        private Item Contents;
        private int Quantity = 1;
        private int which;
        private Text itemName = new Text();
        private Text QuantityText = new Text();

        public itemBox(Item contents, int itemNumber, itemContainer parent){
            which = itemNumber;
            this.setMaxSize(110, 110);
            Contents = contents;
            if(Contents instanceof Consumable)
                Quantity = -1;
            itemName.setFont(Font.font("Tw Cen MT Condensed", FontWeight.BOLD, 25));
            itemName.setFill(Color.WHITE);
            itemName.setTranslateY(-120);
            itemName.setWrappingWidth(200);
            itemName.setTextAlignment(TextAlignment.CENTER);
            itemName.setText(contents.returnKey());
            if(Quantity > 0)
                QuantityText.setText("Quantity: " + Quantity);
            else if(Quantity == 0)
                setOutOfStock();
            else
                QuantityText.setText("Quantity: -");
            QuantityText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 20));
            QuantityText.setFill(Color.WHITE);
            QuantityText.setTranslateY(90);
            QuantityText.setWrappingWidth(185);
            QuantityText.setTextAlignment(TextAlignment.CENTER);

            Icon = contents.getIcon();
            highlit = new Rectangle(120, 120, Color.GOLDENROD);
            highlit.setVisible(false);
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/thumbnails/lootslot.jpg"))){
                ImageView background = new ImageView(new Image(imginput));
                background.setFitWidth(110);
                background.setFitHeight(110); //preserve aspect ratio
                backGround = background;
            }
            catch (IOException e){
                System.out.println("Error loading loot slot image.");
            }
            setAlignment(Pos.CENTER); //center the box's sub-components.
            getChildren().addAll(highlit, backGround, Icon, itemName, QuantityText);
            //add the background and icon to the button.

            //if the user enters any component of the image, set to highlit; set to plain if they leave.
            this.setOnMouseEntered(event -> {
                if(Quantity != 0)
                    parent.setCurrent(which);
            });

            this.setOnMouseReleased(event -> {
                if(Quantity != 0){
                    Shop.BuyCurrent();
                }
            });
        }

        public void decrementQuantity(){
            --Quantity;
            if(Quantity > 0)
                QuantityText.setText("Quantity: " + Quantity);
            else if(Quantity == 0)
                setOutOfStock();
            else{
                QuantityText.setText("Quantity: -");
            }
        }

        public void setHighLit(){ //makes the image/text more striking
            if(Quantity != 0 && !highlit.isVisible()) {
                backGround.setOpacity(1);
                Icon.setOpacity(1);
                Description = Contents.getItemDisplay(false);
                Description.setTranslateX(Description.getTranslateX()+120);
                currentShop.getChildren().add(Description);
                currentShop.clickablepane.toFront();

                highlit.setVisible(true);
            }
        }

        public void setPlain() { //self-explanatory
            if(Quantity != 0) {
                currentShop.getChildren().remove(Description);
                Description = null;
                backGround.setOpacity(.9);
                Icon.setOpacity(.9);
                highlit.setVisible(false);
            }
        }

        public void setOutOfStock(){
            backGround.setOpacity(.5);
            Icon.setOpacity(.5);
            itemName.setFill(Color.GRAY);
            QuantityText.setText("Out of Stock");
            QuantityText.setFill(Color.GRAY);
            if(Description != null)
            Description.setVisible(false);
            highlit.setVisible(false);
        }
    }

    private abstract class shopButton extends StackPane{
        private int buttonNumber;
        private Text buttonText;
        protected Rectangle buttonShape;
        private LinearGradient buttonGradient;
        protected boolean isGrayedOut = false;

        public shopButton(String buttonname, int buttonnumber){
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
                ((shopButton)shopButtons.getChildren().get(currentButton)).setPlain();
                currentButton = buttonNumber;
                setHighLit();
                if(enterDown)
                    setSelected();
                currentShop.getChildren().remove(Description);
                Description = null;
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

    private class buyButton extends shopButton{
        private buyButton(){
            super("Buy", 0);
        }

        @Override
        public void performAction() {
             BuyCurrent();
        }
    }

    private class sellButton extends shopButton{
        private sellButton(){
            super("Sell", 1);
        }

        @Override
        public void performAction() {
            Game.swapToInventory(Game.currentCity);
        }
    }

    private class leaveButton extends shopButton{
        private leaveButton(){
            super("Leave", 2);
        }

        @Override
        public void performAction() {
            Game.currentCity.getChildren().remove(currentShop); //remove this pane from its city's pane
            Inventory.setCanSell(false); //ensure that the player can no longer sell
            selectedContainer.getCurrent().setPlain();
        }
    }
}
