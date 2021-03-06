package Characters.Inventory;

import Characters.rotarySelectionPane;
import Profile.Game;
import Profile.characterScreen;
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
    private static orderedDLL[] Items = new orderedDLL[5];
    //0 = consumables
    //1 = weapons
    //2 = armor
    //3 = accessories
    //4 = quest items
    private static int currentCategory = 1; //the current category of item that the user is looking at.
    private static categoryButton[] Categories = new categoryButton[5];
    private static inventoryBox itemBox;
    private static StackPane contentRoot;
    private static GridPane buttonPane;
    private static ItemUseBox usebox;
    private static transient ScrollPane itemBoxPane;
    private static StackPane itemDisplay;
    private static boolean canSell = false;

    public Inventory() {
        contentRoot = new StackPane();
        contentRoot.setAlignment(Pos.CENTER);
        Rectangle Background = new Rectangle(1280, 800); //establish a mostly-translucent shaded background
        Background.setFill(Color.WHITE);
        contentRoot.getChildren().add(Background);
        buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        contentRoot.getChildren().add(buttonPane);

        itemBoxPane = new ScrollPane();
        itemBoxPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        itemBoxPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        itemBoxPane.setVmax(1);
        itemBoxPane.setVmin(1);
        itemBoxPane.setPrefSize(400, 450);
        itemBoxPane.setMaxSize(400, 450);
        itemBoxPane.setStyle("-fx-font-size: 30px;");
        itemBoxPane.setTranslateY(bounds.getHeight()/12);
        buttonPane.getChildren().add(itemBoxPane);

        Categories[0] = new categoryButton(0, "Consumables");
        Categories[1] = new categoryButton(1, "Weapons");
        Categories[2] = new categoryButton(2, "Armor");
        Categories[3] = new categoryButton(3, "Accessories");
        Categories[4] = new categoryButton(4, "Quest");

        for (int i = 0; i < 5; ++i) {
            Items[i] = new orderedDLL(); //allocate all ordered DLLs.
            Categories[i].setTranslateX((-1.5+i)*205);
            Categories[i].setTranslateY(-300);
            buttonPane.getChildren().add(Categories[i]);
        }

        if(usebox == null) {
            usebox = new ItemUseBox();
            buttonPane.getChildren().add(usebox);
        }

        ItemUseBox.RefreshSellUsable();

        //interfacing code:
        itemBoxPane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:{
                    itemBox.useCurrent();
                    break;
                }
                case UP: {
                    itemBox.incrementCurrent(-1);
                    break;
                }
                case W:{
                    itemBox.incrementCurrent(-1);
                    break;
                }
                case DOWN: {
                    itemBox.incrementCurrent(1);
                    break;
                }
                case S: {
                    itemBox.incrementCurrent(1);
                    break;
                }
                case RIGHT:{
                    if(itemBoxPane.getParent() == buttonPane)
                        setItemBox(currentCategory + 1);
                    break;
                }
                case D:{
                    if(itemBoxPane.getParent() == buttonPane)
                        setItemBox(currentCategory + 1);
                    break;
                }
                case LEFT:{
                    if(itemBoxPane.getParent() == buttonPane)
                        setItemBox(currentCategory - 1);
                    break;
                }
                case A:{
                    if(itemBoxPane.getParent() == buttonPane)
                        setItemBox(currentCategory - 1);
                    break;
                }
            }
        });
        contentRoot.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                if(Game.currentCity != null)
                    Game.swapToCity(contentRoot);
                else
                    Game.swapToMap(contentRoot);
            }
        });
    }

    public static void setCanSell(boolean cansell){
        canSell = cansell;
        ItemUseBox.RefreshSellUsable();
    }

    public static void setItemBox(int i){
        if(i >= 0 && i < 5){
            itemBoxPane.requestFocus();
            Categories[currentCategory].setPlain();
            currentCategory = i;
            Categories[currentCategory].setBold();
            if(itemDisplay != null)
                contentRoot.getChildren().remove(itemDisplay);
            itemBox = new inventoryBox(Items[i]);
            itemBox.setCurrent(0);
            if(Items[i].getSize() != 0)
                itemBox.setCurrent(0);
            itemBoxPane.setContent(itemBox);
            itemBox.setVgrow(itemBoxPane, Priority.ALWAYS);
        }
        ItemUseBox.RefreshSellUsable();
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

    public void Remove(Item toRemove){
        orderedDLL itemType = null;
        if (toRemove instanceof Consumable)
            itemType = Items[0];
        else if (toRemove instanceof Weapon)
            itemType = Items[1];
        else if (toRemove instanceof Armor)
            itemType = Items[2];
        else if (toRemove instanceof Accessory)
            itemType = Items[3];
        else if (toRemove instanceof questItem)
            itemType = Items[4];
        if (itemType == null) //if this isn't actually an item, we can't insert it safely.
            return;
        itemType.Remove(toRemove.returnKey());
    }

    public StackPane getContentRoot(){
        return contentRoot;
    }

    private class categoryButton extends StackPane{
        private Text buttonText;
        private int buttonVal;
        private Rectangle buttonShape;

        public categoryButton(int val, String name){
            setAlignment(Pos.CENTER);
            setMaxSize(200, 75);
            buttonVal = val;
            buttonShape = new Rectangle(200, 75);
            buttonShape.setFill(Color.LIGHTGRAY);
            buttonText = new Text(name);
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 32));
            getChildren().addAll(buttonShape, buttonText);

            buttonShape.setOnMouseReleased(event -> {
                setItemBox(buttonVal);
            });
            buttonText.setOnMouseReleased(event -> {
                setItemBox(buttonVal);
            });
        }

        public void setPlain(){
            buttonShape.setFill(Color.LIGHTGRAY);
        }

        public void setBold(){
            buttonShape.setFill(Color.DARKGRAY);
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
                inventoryBox.useCurrent();
            });
    }

        public void setPlain() {
            buttonShape.setFill(Color.LIGHTGRAY);
            buttonShape.setOpacity(.5);
        }

        public void setBold() {
            buttonShape.setOpacity(.8);
            buttonShape.setFill(Color.DARKGRAY);
            contentRoot.getChildren().remove(itemDisplay);
            itemDisplay = item.getItemDisplay(true);
            contentRoot.getChildren().add(itemDisplay);
            buttonPane.toFront();
        }

        public boolean Use(){
            return item.Use(characterScreen.getCurrentCharacter());
        }

        public String getKey(){
            return item.returnKey();
        }
}

    private static class inventoryBox extends VBox {
        private static int currentItem;
        private static inventoryButton [] itemArray;
        private static int sumItems;

        public inventoryBox(orderedDLL toadd) {
            currentItem = 0;
            this.setAlignment(Pos.CENTER);
            sumItems = toadd.getSize();
            if(sumItems == 0){
                Inventory.itemBoxPane.setVmin(1);
                Inventory.itemBoxPane.setVmax(1);
            }
            else {
                Inventory.itemBoxPane.setVmin(0);
                Inventory.itemBoxPane.setVmax(1);
                Inventory.itemBoxPane.setVvalue(0);
            }
            if(sumItems != 0)
                itemArray = new inventoryButton[sumItems];
            else
                itemArray = null;
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
                itemBoxPane.requestFocus();
                itemArray[currentItem].setPlain();
                currentItem = which;
                itemArray[currentItem].setBold();
            }
            ItemUseBox.RefreshSellUsable();
        }
        public static void incrementCurrent(int toIncrement){
            if(currentItem + toIncrement >= 0 && currentItem + toIncrement < sumItems && sumItems != 0) {
                itemArray[currentItem].setPlain();
                currentItem += toIncrement;
                itemArray[currentItem].setBold();
            }
            if(toIncrement > 0 && (currentItem+1) > (sumItems*itemBoxPane.getVvalue() + itemBoxPane.getHeight()*(1-itemBoxPane.getVvalue())/46)) {
                itemBoxPane.setVvalue(itemBoxPane.getVvalue() + 1.0/((sumItems-1)-itemBoxPane.getHeight()/46));
            }
            else if(toIncrement < 0 && currentItem < (sumItems*itemBoxPane.getVvalue() - itemBoxPane.getHeight()*(itemBoxPane.getVvalue())/46)) {
                itemBoxPane.setVvalue(itemBoxPane.getVvalue() - 1.0/((sumItems-1)-itemBoxPane.getHeight()/46));
            }
            ItemUseBox.RefreshSellUsable();
        }

        public static void useCurrent(){
            if(!characterScreen.isMainPaneIsActive()){
                if(itemArray[currentItem] != null) {
                    if (itemArray[currentItem].item.Use(characterScreen.getCurrentCharacter())) {
                        Items[currentCategory].Remove(itemArray[currentItem].getKey());
                    }
                }
            }
            else if(itemArray != null) {
                if (canSell) {
                    sellCurrent();
                } else {
                    rotarySelectionPane Selector = new rotarySelectionPane(Game.Player.getParty(),
                            itemArray[currentItem].item);
                    Rectangle tint = new Rectangle(3000, 3000, Color.BLACK);
                    tint.setOpacity(.75);
                    contentRoot.getChildren().addAll(tint, Selector);
                    Selector.requestFocus();
                    contentRoot.setOnKeyReleased(event -> {
                        if (event.getCode() == KeyCode.ENTER) { //the user used the item in question
                            if (rotarySelectionPane.isDone()) {
                                Items[currentCategory].Remove(itemArray[currentItem].getKey());
                                setItemBox(currentCategory);
                                contentRoot.setOnMouseReleased(event1 -> {
                                }); //revert to doing nothing
                                contentRoot.setOnKeyReleased(event1 -> { //revert to standard escape functionality.
                                    if (event1.getCode() == KeyCode.ESCAPE) {
                                        Game.swapToMap(contentRoot);
                                    }
                                });
                                contentRoot.getChildren().removeAll(Selector, tint);
                                itemBoxPane.requestFocus();
                            }
                        } else if (event.getCode() == KeyCode.ESCAPE) {
                            contentRoot.getChildren().removeAll(Selector, tint);
                            contentRoot.setOnMouseReleased(event1 -> {
                            }); //revert to doing nothing
                            contentRoot.setOnKeyReleased(event1 -> { //revert to standard escape functionality.
                                if (event1.getCode() == KeyCode.ESCAPE) {
                                    Game.swapToMap(contentRoot);
                                }
                            });
                            itemBoxPane.requestFocus();
                        }
                    });
                    contentRoot.setOnMouseReleased(event -> {
                        if (rotarySelectionPane.isDone()) {
                            Items[currentCategory].Remove(itemArray[currentItem].getKey());
                            setItemBox(currentCategory);
                            contentRoot.setOnMouseReleased(event1 -> {
                            }); //revert to doing nothing
                            contentRoot.setOnKeyReleased(event1 -> { //revert to standard escape functionality.
                                if (event1.getCode() == KeyCode.ESCAPE) {
                                    Game.swapToMap(contentRoot);
                                }
                            });
                            contentRoot.getChildren().removeAll(Selector, tint);
                            itemBoxPane.requestFocus();
                        }
                    });
                }
            }
        }

        private Line generateLine() {
            Line line = new Line();
            line.setEndX(400);
            line.setStroke(Color.WHITE);
            return line;
        }

        public static void dropCurrent(){
            if(itemArray != null) {
                Items[currentCategory].Remove(itemArray[currentItem].getKey());
                setItemBox(currentCategory);
            }
        }

        public static void sellCurrent(){
            if(itemArray != null){
                if(itemArray[currentItem].item.CanBeSold()){
                    Game.Player.addCoins(itemArray[currentItem].item.getValue());
                    Item toSell = (Item)Items[currentCategory].Retrieve(itemArray[currentItem].getKey()).returnData();
                    if(toSell.Decrement(-1) == 0) {
                        dropCurrent();
                        Inventory.contentRoot.getChildren().remove(itemDisplay);
                    }
                    else{
                        Inventory.contentRoot.getChildren().remove(itemDisplay);
                        Inventory.itemDisplay = itemArray[currentItem].item.getItemDisplay(true);
                        Inventory.contentRoot.getChildren().add(itemDisplay);
                    }
                    Game.battle.playMedia("loot");
                }
            }
        }
    }

    private static class ItemUseBox extends StackPane{
        private static Text sellText; //we need a reference to this because it can potentially be unavailable to players.
        private static Text goldText; //we also need a reference to this to possibly update it.

        public ItemUseBox(){
            Text texttoadd;
            setAlignment(Pos.CENTER);
            setTranslateX(-450);
            setTranslateY(75);
            Rectangle background = new Rectangle(300, 400);
            background.setFill(Color.GRAY);
            getChildren().add(background);
            GridPane buttonpane = new GridPane();
            buttonpane.setAlignment(Pos.CENTER);
            getChildren().add(buttonpane);
            //USE BUTTON
            Rectangle use = new Rectangle(250, 50);
            use.setFill(Color.LIGHTGRAY);
            use.setTranslateY(-150);
            use.setOnMouseClicked(event ->{
                if(canSell){
                    canSell = false;
                    inventoryBox.useCurrent();
                    canSell = true;
                }
                inventoryBox.useCurrent();
                RefreshSellUsable();
            });
            use.setOnMouseEntered(event1 -> {
                use.setFill(Color.DARKGRAY);
            });
            use.setOnMouseExited(event ->{
                use.setFill(Color.LIGHTGRAY);
            });
            buttonpane.getChildren().add(use);
            texttoadd = new Text("Use");
            texttoadd.setTranslateY(-150);
            texttoadd.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            texttoadd.setOnMouseClicked(event -> {
                if(canSell){
                    canSell = false;
                    inventoryBox.useCurrent();
                    canSell = true;
                }
                inventoryBox.useCurrent();
                RefreshSellUsable();
            });
            texttoadd.setOnMouseEntered(event1 -> {
                use.setFill(Color.DARKGRAY);
            });
            texttoadd.setFocusTraversable(false);
            getChildren().add(texttoadd);
            //DROP BUTTON
            Rectangle drop = new Rectangle(250, 50);
            drop.setTranslateY(-75);
            drop.setFill(Color.LIGHTGRAY);
            drop.setOnMouseClicked(event ->{
                inventoryBox.dropCurrent();
            });
            drop.setOnMouseEntered(event1 -> {
                drop.setFill(Color.DARKGRAY);
            });
            drop.setOnMouseExited(event ->{
                drop.setFill(Color.LIGHTGRAY);
            });
            buttonpane.getChildren().add(drop);
            texttoadd = new Text("Drop");
            texttoadd.setTranslateY(-75);
            texttoadd.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            texttoadd.setOnMouseClicked(event -> {
                inventoryBox.dropCurrent();
            });
            texttoadd.setOnMouseEntered(event1 -> {
                drop.setFill(Color.DARKGRAY);
            });
            getChildren().add(texttoadd);
            //SELL BUTTON
            Rectangle sell = new Rectangle(250, 50);
            sell.setFill(Color.LIGHTGRAY);
            sell.setOnMouseClicked(event ->{
                Game.swapToMap(contentRoot);
            });
            sell.setOnMouseEntered(event1 -> {
                sell.setFill(Color.DARKGRAY);
            });
            sell.setOnMouseExited(event ->{
                sell.setFill(Color.LIGHTGRAY);
            });
            buttonpane.getChildren().add(sell);
            sellText = new Text("Sell");
            sellText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            sellText.setOnMouseClicked(event -> {
                if(canSell){
                    itemBox.sellCurrent();
                }
            });
            sellText.setOnMouseEntered(event1 -> {
                sell.setFill(Color.DARKGRAY);
            });
            getChildren().add(sellText);
            //BACK BUTTON
            Rectangle back = new Rectangle(250, 50);
            back.setFill(Color.LIGHTGRAY);
            back.setTranslateY(75);
            back.setOnMouseClicked(event ->{
                if(Game.currentCity != null)
                    Game.swapToCity(contentRoot);
                else
                    Game.swapToMap(contentRoot);
            });
            back.setOnMouseEntered(event1 -> {
                back.setFill(Color.DARKGRAY);
            });
            back.setOnMouseExited(event ->{
                back.setFill(Color.LIGHTGRAY);
            });
            buttonpane.getChildren().add(back);
            texttoadd = new Text("Back");
            texttoadd.setTranslateY(75);
            texttoadd.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            texttoadd.setOnMouseClicked(event -> {
                if(Game.currentCity != null)
                    Game.swapToCity(contentRoot);
                else
                    Game.swapToMap(contentRoot);
            });
            texttoadd.setOnMouseEntered(event1 -> {
                back.setFill(Color.DARKGRAY);
            });
            getChildren().add(texttoadd);

            goldText = new Text();
            goldText.setTextAlignment(TextAlignment.CENTER);
            goldText.setFill(Color.BLACK);
            goldText.setTranslateY(145);
            goldText.setTranslateX(0);
            goldText.setFont(Font.font(("Tw Cen MT Condensed"), FontWeight.SEMI_BOLD, 20));
            getChildren().add(goldText);
        }

        public static void RefreshSellUsable(){
            if(Game.Player != null)
                goldText.setText("Gold: " + Game.Player.getCoins());
            if(itemBox.itemArray == null) {
                sellText.setFill(Color.RED);
                return;
            }
            if(itemBox.itemArray[itemBox.currentItem] == null) {
                sellText.setFill(Color.RED);
                return;
            }
            if(canSell && itemBox.itemArray[itemBox.currentItem].item.canBeSold){
                    sellText.setFill(Color.BLACK);
            }
            else{
                sellText.setFill(Color.RED);
            }
        }
    }

    public static ScrollPane getItemBoxPane(int category){
        setItemBox(category);
        //.getChildren().remove(itemBoxPane);
        return itemBoxPane;
    }

    public static StackPane getItemDisplay() {
        return itemBox.itemArray[itemBox.currentItem].item.getItemDisplay(true);
    }

    public static void returnItemBoxPane(){
        buttonPane.getChildren().add(itemBoxPane);
    }
}
