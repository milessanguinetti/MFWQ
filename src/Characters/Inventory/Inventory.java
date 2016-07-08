package Characters.Inventory;

import Profile.Game;
import Structures.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public class Inventory implements Serializable {
    private orderedDLL[] Items = new orderedDLL[5];
    //0 = consumables
    //1 = weapons
    //2 = armor
    //3 = accessories
    //4 = quest items
    private int currentCategory = 1; //the current category of item that the user is looking at.
    private static categoryButton[] Categories = new categoryButton[5];
    private inventoryBox itemBox;
    private static StackPane contentRoot;
    private static GridPane buttonPane;
    private ScrollPane itemBoxPane;

    public Inventory() {
        contentRoot = new StackPane();
        contentRoot.setAlignment(Pos.CENTER);
        Rectangle Background = new Rectangle(1280, 800); //establish a mostly-translucent shaded background
        Background.setFill(Color.WHITE);
        contentRoot.getChildren().add(Background);
        buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        contentRoot.getChildren().add(buttonPane);

        Categories[0] = new categoryButton(0, "Consumables", this);
        Categories[1] = new categoryButton(1, "Weapons", this);
        Categories[2] = new categoryButton(2, "Armor", this);
        Categories[3] = new categoryButton(3, "Accessories", this);
        Categories[4] = new categoryButton(4, "Quest", this);

        for (int i = 0; i < 5; ++i) {
            Items[i] = new orderedDLL(); //allocate all ordered DLLs.
            Categories[i].setTranslateX((-2+i)*205);
            Categories[i].setTranslateY(-300);
            buttonPane.getChildren().add(Categories[i]);
        }

        //interfacing code:
        contentRoot.setOnKeyReleased(event -> {
            switch (event.getCode()){
                case ENTER:{
                    itemBox.useCurrent();
                    break;
                }
                case UP: {
                    itemBox.incrementCurrent(1);
                    break;
                }
                case W:{
                    itemBox.incrementCurrent(1);
                    break;
                }
                case DOWN: {
                    itemBox.incrementCurrent(-1);
                    break;
                }
                case S: {
                    itemBox.incrementCurrent(-1);
                    break;
                }
                case RIGHT:{
                    setItemBox(currentCategory + 1);
                    break;
                }
                case D:{
                    setItemBox(currentCategory + 1);
                    break;
                }
                case LEFT:{
                    setItemBox(currentCategory - 1);
                    break;
                }
                case A:{
                    setItemBox(currentCategory - 1);
                    break;
                }
                case ESCAPE:{
                    Game.mainmenu.getCurrentGame().swapToMap(contentRoot);
                    break;
                }
            }
        });

    }

    public void setItemBox(int i){
        if(i >= 0 && i < 5){
            Categories[currentCategory].setPlain();
            currentCategory = i;
            Categories[currentCategory].setBold();
            contentRoot.getChildren().remove(itemBox);
            itemDisplay.Remove();
            itemBox = new inventoryBox(Items[i]);
            if(Items[i].getSize() != 0)
                itemBox.setCurrent(0);
            /*itemBoxPane = new ScrollPane();
            itemBoxPane.setVisible(false);
            contentRoot.getChildren().remove(itemBoxPane);
            itemBoxPane.setContent(itemBox);
            Rectangle2D bounds = Screen.getPrimary().getBounds();
            itemBoxPane.setVmax(bounds.getHeight()/1.5);
            itemBoxPane.setPrefSize(bounds.getHeight()/1.5, 500);
            itemBoxPane.setStyle("-fx-font-size: 30px;");
            itemBox.setVgrow(itemBoxPane, Priority.ALWAYS);
            itemBoxPane.setTranslateX(0 - bounds.getWidth()/4);
            itemBoxPane.setTranslateY(bounds.getHeight()/4);
            contentRoot.getChildren().add(itemBoxPane);
            */
            contentRoot.getChildren().add(itemBox);
        }
    }

    //returns the head of the consumables DLL
    public orderedDLLNode getConsumables() {
        return Items[0].getHead();
    }

    public int getConsumablesSize() {
        return Items[0].getSize();
    }

    public void Insert(Item toInsert) {
        orderedDLL itemType = null;
        if (toInsert instanceof Consumable)
            itemType = Items[0];
        else if (toInsert instanceof Weapon)
            itemType = Items[1];
        else if (toInsert instanceof Armor)
            itemType = Items[2];
        else if (toInsert instanceof Accessory)
            itemType = Items[3];
        else if (toInsert instanceof questItem)
            itemType = Items[4];
        if (itemType == null) //if this isn't actually an item, we can't insert it safely.
            return;
        itemType.Insert(new orderedDLLNode(toInsert));
    }

    private static class itemDisplay extends StackPane{
        private static itemDisplay currentDisplay;

        public itemDisplay(Item toDisplay){
            if(toDisplay != null){
                if(currentDisplay != null){
                    Inventory.contentRoot.getChildren().remove(currentDisplay);
                }
                currentDisplay = this;
                setTranslateX(450);
                setTranslateY(100);
                Rectangle displayBackground = new Rectangle(300, 500);
                displayBackground.setFill(Color.LIGHTGRAY);
                getChildren().add(displayBackground);
                addText(toDisplay.returnKey(), -200, 35);
                ImageView icon = toDisplay.getIcon();
                icon.setTranslateY(-50);
                getChildren().add(icon);
                addText(toDisplay.getDescription(), 100, 20);
                addText("Quantity: " + toDisplay.getQuantity(), 200, 20);
                Inventory.contentRoot.getChildren().add(currentDisplay);
            }
        }

        private void addText(String toAdd, int Y, int size){
            Text text = new Text(toAdd);
            text.setWrappingWidth(250);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, size));
            text.setTranslateY(Y);
            getChildren().add(text);
        }

        public static void Remove(){
            Inventory.contentRoot.getChildren().remove(currentDisplay);
        }
    }

    private static class categoryButton extends StackPane{
        private Text buttonText;
        private int buttonVal;
        private Rectangle buttonShape;
        private static Inventory currentInventory;

        public categoryButton(int val, String name, Inventory currentinventory){
            currentInventory = currentinventory;
            buttonVal = val;
            buttonShape = new Rectangle(200, 75);
            buttonShape.setFill(Color.GRAY);
            buttonShape.setOpacity(.4);
            buttonText = new Text(name);
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 32));
            getChildren().addAll(buttonShape, buttonText);

            setOnMouseClicked(event -> {
                currentInventory.setItemBox(buttonVal);
            });
        }

        public void setPlain(){
            buttonShape.setOpacity(.4);
        }

        public void setBold(){
            buttonShape.setOpacity(.7);
        }
    }

    //class strictly for creating clickable buttons for items in the menu.
    private static class inventoryButton extends StackPane {
        private Text buttonText;
        private int buttonVal;
        private Item item;
        private Rectangle buttonShape;

        public inventoryButton(int val, Item toshow) {
            buttonVal = val;
            item = toshow;

            buttonShape = new Rectangle(400, 45);
            buttonShape.setFill(Color.LIGHTGRAY);
            buttonShape.setOpacity(.4);

            buttonText = new Text();
            buttonText.setText(item.returnKey());
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 15));

            this.setAlignment(Pos.CENTER); //center the button's sub-components.
            this.getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();

            setOnMouseEntered(event -> {
                Inventory.inventoryBox.setCurrent(buttonVal);
            });

            setOnMouseReleased(event -> {
                item.Use();
            });
    }

        public void setPlain() {
            buttonShape.setOpacity(.6);
        }

        public void setBold() {
            buttonShape.setOpacity(.4);
            new itemDisplay(item);
        }

        public void Use(){
            item.Use();
        }
}

    private static class inventoryBox extends VBox {
        private static int currentItem = 0;
        private static inventoryButton [] itemArray;
        private static int sumItems;

        public inventoryBox(orderedDLL toadd) {
            this.setAlignment(Pos.CENTER);
            sumItems = toadd.getSize();
            itemArray = new inventoryButton[sumItems];
            orderedDLLNode current = toadd.getHead();
            //for all buttons that we will be adding...
            int i = 0;
            while(current != null) {
                itemArray[i] = new inventoryButton(i, (Item)current.returnData());
                this.getChildren().add(itemArray[i]);
                this.getChildren().add(generateLine());
                current = current.getNext();
                ++i;
            } //add the button as well as a spacing line
        }

        public static void setCurrent(int which){
            if(which >= 0 && which < sumItems && sumItems != 0) {
                itemArray[currentItem].setPlain();
                currentItem = which;
                itemArray[currentItem].setBold();
            }
        }

        public static void incrementCurrent(int toIncrement){
            if(currentItem + toIncrement >= 0 && currentItem + toIncrement < sumItems && sumItems != 0) {
                itemArray[currentItem].setPlain();
                currentItem += toIncrement;
                itemArray[currentItem].setBold();
            }
        }

        public static void useCurrent(){
            itemArray[currentItem].Use();
        }

        private Line generateLine() {
            Line line = new Line();
            line.setEndX(630);
            line.setStroke(Color.WHITE);
            return line;
        }
    }

    public StackPane getContentRoot(){
        return contentRoot;
    }
}
