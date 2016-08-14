package Profile;

import Characters.Inventory.Inventory;
import Characters.playerCharacter;
import Characters.rotarySelectionPane;
import Structures.Structure;
import Structures.orderedLLL;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * Created by Spaghetti on 7/29/2016.
 */
public class characterScreen {
    private static StackPane contentRoot;
    private static MainPane mainPane; //the primary pane for viewing stats and jobs/skills/equipment
    private static SelectionPane selectionPane; //the pane for selecting different jobs/skills/equipment
    private static boolean mainPaneIsActive = true;
    private static statViewObject currentStatViewObject; //the currently selected statviewobject.
    private static playerCharacter [] currentParty;
    private static int currentCharacter;

    public characterScreen(){
        contentRoot = new StackPane();
        contentRoot.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(3000, 3000);
        background.setFill(Color.BLACK);
        contentRoot.getChildren().add(background);
        mainPane = new MainPane();
        mainPane.addDisplays();
        selectionPane = new SelectionPane();
    }

    public static boolean isMainPaneIsActive(){
        return mainPaneIsActive;
    }

    public static void swapToMainPane(){
        if(!mainPaneIsActive){
            contentRoot.getChildren().remove(selectionPane);
        }
        contentRoot.getChildren().remove(mainPane); //avoid potential duplicates.
        contentRoot.getChildren().add(mainPane);
        mainPane.initializeDisplay();
        mainPane.requestFocus();
        mainPaneIsActive = true;
    }

    public static void swapToSelectionPane(){
        if(mainPaneIsActive){
            contentRoot.getChildren().remove(mainPane);
            contentRoot.getChildren().remove(selectionPane);
            contentRoot.getChildren().add(selectionPane);
            selectionPane.requestFocus();
            mainPaneIsActive = false;
        }
    }

    public static StackPane getContentRoot(){
        return contentRoot;
    }

    public static playerCharacter getCurrentCharacter(){
        if(currentParty == null){
            currentParty = Game.Player.getParty();
            for(int i = 0; i < 4; ++i) { //ensure that we have a current player character so we don't cause segfaults
                if (currentParty[i] != null) { //in the following lines of code.
                    currentCharacter = i;
                    break;
                }
            }
        }
        return currentParty[currentCharacter];
    }

    private static class MainPane extends GridPane{
        private static playerCharacter Current;
        private static playerCharacter leftOfCurrent;
        private static playerCharacter rightOfCurrent;
        private static Text nameText;
        private static Text levelRaceExpText;
        private static selectableStatViewObject primaryClass;
        private static selectableStatViewObject secondaryClass;
        private static selectableStatViewObject passive;
        private static selectableStatViewObject rightweap;
        private static selectableStatViewObject leftweap;
        private static selectableStatViewObject equippedarmor;
        private static selectableStatViewObject accessory1;
        private static selectableStatViewObject accessory2;
        private static unselectableStatViewObject hp;
        private static unselectableStatViewObject sp;
        private static unselectableStatViewObject armor;
        private static unselectableStatViewObject vit;
        private static unselectableStatViewObject str;
        private static unselectableStatViewObject dex;
        private static unselectableStatViewObject inte;
        private static unselectableStatViewObject fth;
        private static unselectableStatViewObject spd;

        public MainPane(){
            setAlignment(Pos.CENTER);
            nameText = new Text();
            nameText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 36));
            nameText.setFill(Color.GOLDENROD);
            nameText.setTextAlignment(TextAlignment.CENTER);
            //nameText.setTranslateY(-350);
            //nameText.setTranslateX(0);
            levelRaceExpText = new Text();
            levelRaceExpText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 24));
            levelRaceExpText.setFill(Color.WHITE);
            levelRaceExpText.setTextAlignment(TextAlignment.CENTER);
            //levelRaceExpText.setTranslateY(-300);

            for(int i = 0; i < 4; ++i) { //ensure that we have a current player character so we don't cause segfaults
                if (Game.Player.getParty()[i] != null) { //in the following lines of code.
                    Current = Game.Player.getParty()[i];
                    break;
                }
            }

            setOnKeyReleased(event -> {
                if(event.getCode() == KeyCode.ENTER ){
                    if(currentStatViewObject != null) {
                        currentStatViewObject.performAction();
                    }
                }
                else if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                    if(currentStatViewObject != null){
                        if(currentStatViewObject == primaryClass || currentStatViewObject == secondaryClass
                                || currentStatViewObject == passive){
                            currentStatViewObject.setPlain();
                            currentStatViewObject = null;
                            spritesPane.highLightChar(true);
                        }
                        else{
                            currentStatViewObject.setCurrentToAdjacent(0);
                        }
                    }
                }
                else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
                    if(currentStatViewObject != null){
                        currentStatViewObject.setCurrentToAdjacent(3);
                    }
                    else if(leftOfCurrent != null){
                        changeCharacter(false);
                    }
                }
                else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                    if(currentStatViewObject != null){
                        currentStatViewObject.setCurrentToAdjacent(2);
                    }
                    else{
                        currentStatViewObject = secondaryClass;
                        secondaryClass.setHighLit();
                        spritesPane.highLightChar(false);
                    }
                }
                else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                    if(currentStatViewObject != null){
                        currentStatViewObject.setCurrentToAdjacent(1);
                    }
                    else if (rightOfCurrent != null){
                        changeCharacter(true);
                    }
                }
                else if(event.getCode() == KeyCode.ESCAPE){
                    if(Game.currentMap != null)
                        Game.swapToMap(contentRoot);
                    else if(Game.currentCity != null)
                        Game.swapToCity(this);
                    else
                        Game.swapToOverworld(contentRoot);
                }

            });
        }

        public static void addDisplays(){
            try {
                primaryClass = new selectableStatViewObject(null, null,
                        Current.getClass().getMethod("getPrimaryClassesPane"), Current);
                secondaryClass = new selectableStatViewObject(null, primaryClass,
                        Current.getClass().getMethod("getSecondaryClassesPane"), Current);
                passive = new selectableStatViewObject(null, secondaryClass, Current.getClass().getMethod("getSkillPane"), Current);
                hp = new unselectableStatViewObject(null, null);
                sp = new unselectableStatViewObject(null, hp);
                armor = new unselectableStatViewObject(null, sp);
                vit = new unselectableStatViewObject(hp, null);
                str = new unselectableStatViewObject(sp, vit);
                dex = new unselectableStatViewObject(armor, str);
                inte = new unselectableStatViewObject(vit, null);
                fth = new unselectableStatViewObject(str, inte);
                spd = new unselectableStatViewObject(dex, fth);
                rightweap = new selectableStatViewObject(null, null, Game.Player.getClass().getMethod("getItemBoxPane", int.class), Game.Player);
                leftweap = new selectableStatViewObject(null, rightweap, Game.Player.getClass().getMethod("getItemBoxPane", int.class), Game.Player);
                equippedarmor = new selectableStatViewObject(null, leftweap, Game.Player.getClass().getMethod("getItemBoxPane", int.class), Game.Player);
                accessory1 = new selectableStatViewObject(leftweap, null, Game.Player.getClass().getMethod("getItemBoxPane", int.class), Game.Player);
                accessory2 = new selectableStatViewObject(leftweap, accessory1, Game.Player.getClass().getMethod("getItemBoxPane", int.class), Game.Player);

                rightweap.setAdjacent(accessory1, 2);
                leftweap.setAdjacent(accessory1, 2);
                equippedarmor.setAdjacent(accessory2, 2);
                hp.setTwoWayAdjacent(primaryClass, 0);
                sp.setTwoWayAdjacent(secondaryClass, 0);
                armor.setTwoWayAdjacent(passive, 0);
                inte.setTwoWayAdjacent(rightweap, 2);
                fth.setTwoWayAdjacent(leftweap, 2);
                spd.setTwoWayAdjacent(equippedarmor, 2);

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setTranslateY(0);
                vbox.setSpacing(10);
                vbox.getChildren().add(nameText);
                vbox.getChildren().add(levelRaceExpText);
                vbox.getChildren().add(new spritesPane());
                //vbox.getChildren().addAll();
                HBox [] hboxs = new HBox[6];
                for(int i = 0; i < 6; ++i){
                    hboxs[i] = new HBox();
                    hboxs[i].setAlignment(Pos.CENTER);
                    hboxs[i].setSpacing(10);
                    //hboxs[i].setSpacing();
                    vbox.getChildren().add(hboxs[i]);
                }
                hboxs[0].getChildren().addAll(primaryClass, secondaryClass, passive);
                hboxs[1].getChildren().addAll(hp, sp, armor);
                hboxs[2].getChildren().addAll(vit, str, dex);
                hboxs[3].getChildren().addAll(inte, fth, spd);
                hboxs[4].getChildren().addAll(rightweap, leftweap, equippedarmor);
                hboxs[5].getChildren().addAll(accessory1, accessory2);
                mainPane.getChildren().add(vbox);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println(e.getClass());
            }
            initializeDisplay(Current);
        }

        public static void initializeDisplay(){
            currentParty = Game.Player.getParty();
            if(currentStatViewObject != null)
                currentStatViewObject.setPlain();
            currentStatViewObject = null;
            spritesPane.highLightChar(true);
            spritesPane.setLeft(leftOfCurrent, null);
            leftOfCurrent = null;
            for(int i = 0; i < 4; ++i)
                if(currentParty[i] != null) {
                    spritesPane.setCenter(currentParty[currentCharacter], currentParty[i]);
                    currentCharacter = i;
                    break;
                }
            for(int i = currentCharacter+1; i < 4; ++i){
                if(currentParty[i] != null){
                    spritesPane.setRight(rightOfCurrent, currentParty[i]);
                    rightOfCurrent = currentParty[i];
                    break;
                }
            }

            initializeDisplay(currentParty[currentCharacter]);
        }

        public static void initializeDisplay(playerCharacter current) {
            nameText.setText(current.getName());
            levelRaceExpText.setText("Level: " + current.getLevel() + "          Race: " + current.getRace()
                    + current.getExp());
            //currentParty = Game.Player.getParty();
            currentStatViewObject = null;
            Current = current;
            //updates all stat view objects with the current character.
            primaryClass.setText("Primary Class: " + Current.getCurrentClassName());
            secondaryClass.setText("Secondary Class: " + Current.getSecondaryClassName());
            passive.setText("Passive: " + Current.getPassiveName());
            hp.setText("HP: " + Current.getHP() + "/" + Current.getHPCap());
            sp.setText("SP: " + Current.getSP() + "/" + Current.getSPCap());
            armor.setText("Armor: " + Current.getArmor());
            vit.setText("Vit: " + Current.getVit());
            str.setText("Str: " + Current.getStr());
            dex.setText("Dex: " + Current.getDex());
            inte.setText("Int: " + Current.getInt());
            fth.setText("Fth: " + Current.getFth());
            spd.setText("Spd: " + Current.getSpd());
            if (Current.getRight() != null)
                rightweap.setText("Right Weapon: " + Current.getRight().returnKey());
            else {
                rightweap.setText("Right weapon: None");
            }
            if (Current.getLeft() != null)
                leftweap.setText("Left Weapon: " + Current.getLeft().returnKey());
            else {
                leftweap.setText("Left weapon: None");
            }
            equippedarmor.setText("Chest Armor: " + Current.getArmorName());
            accessory1.setText("Accessory 1: " + Current.getAccessoryName(1));
            accessory2.setText("Accessory 2: " + Current.getAccessoryName(2));
        }

        private static void changeCharacter(boolean isRight){
            if(isRight){
                if(rightOfCurrent != null){
                    spritesPane.setLeft(leftOfCurrent, Current);
                    spritesPane.setCenter(Current, rightOfCurrent);
                    leftOfCurrent = currentParty[currentCharacter];
                    for(int i = currentCharacter+1;  i < 4; ++i){
                        if(currentParty[i] == rightOfCurrent)
                            currentCharacter = i;
                    }
                    playerCharacter temp = rightOfCurrent;
                    rightOfCurrent = null;
                    for(int i = currentCharacter+1; i < 4; ++i){
                        if(currentParty[i] != null){
                            rightOfCurrent = currentParty[i];
                            break;
                        }
                    }
                    spritesPane.setRight(temp, rightOfCurrent);
                }
            }
            else{
                if(leftOfCurrent != null){
                    spritesPane.setRight(rightOfCurrent, Current);
                    spritesPane.setCenter(Current, leftOfCurrent);
                    rightOfCurrent = currentParty[currentCharacter];
                    for(int i = currentCharacter-1; i >= 0; --i){
                        if(currentParty[i] == leftOfCurrent)
                            currentCharacter = i;
                    }
                    playerCharacter temp = leftOfCurrent;
                    leftOfCurrent = null;
                    for(int i = currentCharacter-1; i >= 0; --i){
                        if(currentParty[i] != null){
                            leftOfCurrent = currentParty[i];
                            break;
                        }
                    }
                    spritesPane.setLeft(temp, leftOfCurrent);
                }
            }
            initializeDisplay(currentParty[currentCharacter]);
        }

        public static int decideCurrentItemCategory(){
            if(currentStatViewObject == rightweap || currentStatViewObject == leftweap)
                return 1; //weapons are category 1
            if(currentStatViewObject == equippedarmor)
                return 2; //armor is category 3
            else
                return 3; //accessories are category 4 and are the only other case where this would be called
        }

        public static int decideSkillOrClass(){
            if(currentStatViewObject == primaryClass)
                return 1;
            if(currentStatViewObject == secondaryClass)
                return 2;
            else
                return 3; //skill case
        }
    }

    private static class spritesPane extends HBox{
        private static StackPane center;
        private static StackPane left;
        private static StackPane right;
        private static Rectangle centerHighlit;
        private static Polygon leftButton;
        private static Polygon rightButton;

        public spritesPane(){
            setAlignment(Pos.CENTER);
            setSpacing(100);
            Rectangle Darken;
            //Initialize left pane.
            left = new StackPane();
            Darken = new Rectangle(500, 250);
            Darken.setFill(Color.BLACK);
            Darken.setOpacity(.4);
            leftButton = new Polygon(0,0, -60, 60, 0, 120);
            leftButton.setFill(Color.GRAY);
            left.setAlignment(Pos.CENTER_RIGHT);
            left.getChildren().addAll(Darken, leftButton);
            left.setMaxSize(500, 250);
            left.setOnMouseClicked(event -> {
                MainPane.changeCharacter(false);
            });
            //Initialize center pane.
            center = new StackPane();
            Darken = new Rectangle(150, 150);
            Darken.setFill(Color.BLACK);
            centerHighlit = new Rectangle(155, 155);
            centerHighlit.setFill(Color.GOLDENROD);
            center.setAlignment(Pos.CENTER);
            center.getChildren().addAll(centerHighlit, Darken);
            //Initialize right pane;
            right = new StackPane();
            Darken = new Rectangle(500, 250);
            Darken.setFill(Color.BLACK);
            Darken.setOpacity(.4);
            rightButton = new Polygon(0,0, 50, 50, 0, 100);
            rightButton.setFill(Color.GRAY);
            right.setAlignment(Pos.CENTER_LEFT);
            right.getChildren().addAll(Darken, rightButton);
            right.setMaxSize(500, 250);
            right.setOnMouseClicked(event -> {
                MainPane.changeCharacter(true);
            });

            getChildren().addAll(left, center, right);
        }

        public static void setCenter(playerCharacter toremove, playerCharacter toadd){
            if(toremove != null) {
                center.getChildren().remove(toremove);
                toremove.Animate(false);
            }
            if(toadd != null) {
                toadd.setTranslateX(0);
                toadd.setTranslateY(0);
                center.getChildren().add(toadd);
                toadd.Animate(true);
                toadd.toFront();
                centerHighlit.setOpacity(1);
            }
        }

        public static void setLeft(playerCharacter toremove, playerCharacter toadd){
            if(toremove != null) {
                left.getChildren().remove(toremove);
                if(toremove != getCurrentCharacter())
                    toremove.Animate(false);
            }
            if(toadd != null) {
                toadd.setTranslateX(50);
                toadd.setTranslateY(0);
                left.getChildren().add(toadd);
                toadd.toBack();
                leftButton.setFill(Color.GOLDENROD);
            }
            else{
                leftButton.setFill(Color.GRAY);
            }
        }

        public static void setRight(playerCharacter toremove, playerCharacter toadd){
            if(toremove != null) {
                right.getChildren().remove(toremove);
                if(toremove != getCurrentCharacter())
                    toremove.Animate(false);
            }
            if(toadd != null) {
                toadd.setTranslateX(-50);
                toadd.setTranslateY(0);
                right.getChildren().add(toadd);
                toadd.toBack();
                rightButton.setFill(Color.GOLDENROD);
            }
            else{
                rightButton.setFill(Color.GRAY);
            }
        }

        public static void highLightChar(boolean isHighlit){
            if(isHighlit)
                centerHighlit.setVisible(true);
            else{
                centerHighlit.setVisible(false);
            }
        }
    }

    private static class SelectionPane extends GridPane{
        private static int mode;
        private static Node primaryObject;
        private static Node secondaryObject;
        private static Text title;

        public SelectionPane(){
            setAlignment(Pos.CENTER);
            title = new Text();
            title.setTranslateY(-350);
            title.setTextAlignment(TextAlignment.CENTER);
            title.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));
            title.setFill(Color.WHITE);
            getChildren().add(title);

            setOnKeyReleased(event -> {
                if(event.getCode() == KeyCode.ESCAPE){
                    getChildren().removeAll(primaryObject, secondaryObject);
                    MainPane.initializeDisplay();
                    if(mode == 1){
                        Inventory.returnItemBoxPane();
                    }
                    swapToMainPane();
                }
                else{
                    if(mode == 1){
                        getChildren().remove(secondaryObject);
                        secondaryObject = Inventory.getItemDisplay();
                        if(secondaryObject != null) {
                            secondaryObject.setTranslateX(500);
                            secondaryObject.setTranslateY(0);
                            selectionPane.getChildren().add(secondaryObject);
                        }
                        if(event.getCode() == KeyCode.ENTER){
                            getChildren().removeAll(primaryObject, secondaryObject);
                            MainPane.initializeDisplay();
                            Inventory.returnItemBoxPane();
                            swapToMainPane();
                        }
                    }
                    else if(mode == 2){
                        if(rotarySelectionPane.isDone()){
                            getChildren().remove(primaryObject);
                            MainPane.initializeDisplay();
                            swapToMainPane();
                        }
                    }
                }
            });
            setOnMouseReleased(event -> {
                if(rotarySelectionPane.isDone() && mode == 2){
                    getChildren().remove(primaryObject);
                    MainPane.initializeDisplay();
                    swapToMainPane();
                }
            });
        }

        public static void setMode(Object toCallFrom, Method toCall) {
            try {
                swapToSelectionPane();
                if(toCallFrom instanceof Inventory){
                    mode = 1;
                    primaryObject = (Node) toCall.invoke(toCallFrom, MainPane.decideCurrentItemCategory());
                    selectionPane.getChildren().add(primaryObject);
                    primaryObject.requestFocus();
                    secondaryObject = Inventory.getItemDisplay();
                    if(secondaryObject != null) {
                        secondaryObject.setTranslateX(500);
                        secondaryObject.setTranslateY(0);
                        selectionPane.getChildren().add(secondaryObject);
                        switch (MainPane.decideCurrentItemCategory()){
                            case 1:{
                                title.setText("Select a new weapon for " + getCurrentCharacter().getName());
                                break;
                            }
                            case 2:{
                                title.setText("Select new armor for " + getCurrentCharacter().getName());
                                break;
                            }
                            case 3:{
                                title.setText("Select a new accessory for " + getCurrentCharacter().getName());
                                break;
                            }
                        }
                    }
                    else{
                        title.setText("No other items available!");
                    }
                }
                else{
                    mode = 2;
                    switch (MainPane.decideSkillOrClass()){
                        case 1:{
                            title.setText("Select a primary class for " + getCurrentCharacter().getName());
                            primaryObject = (Node) toCall.invoke(toCallFrom);
                            break;
                        }
                        case 2:{
                            title.setText("Select a secondary class for " + getCurrentCharacter().getName());
                            primaryObject = (Node) toCall.invoke(toCallFrom);
                            break;
                        }
                        case 3:{
                            title.setText("Select a new passive skill for " + getCurrentCharacter().getName());
                            primaryObject = (Node) toCall.invoke(toCallFrom);
                            break;
                        }
                    }
                    selectionPane.getChildren().add(primaryObject);
                    primaryObject.requestFocus();
                }
            }
            catch (Exception e){
                System.out.println(toCall.getParameterCount());

                System.out.println(e.getMessage());
            }
        }
    }

    private static abstract class statViewObject extends StackPane{
        private statViewObject [] Adjacent = new statViewObject[4];
        private Rectangle highLit;
        private Text displayText;

        public statViewObject(int xsize, int ysize, statViewObject above, statViewObject toleft){
            setAlignment(Pos.CENTER);
            setMaxSize(xsize, ysize);
            setMinSize(xsize, ysize);
            highLit = new Rectangle(xsize+3, ysize+3);
            highLit.setFill(Color.GOLDENROD);
            highLit.setVisible(false);
            Rectangle background = new Rectangle(xsize, ysize);
            background.setFill(Color.BLACK);
            displayText = new Text();
            displayText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
            displayText.setFill(Color.WHITE);
            getChildren().addAll(highLit, background, displayText);

            if(above != null){ //add possible above object and set y orientation accordingly
                Adjacent[0] = above;
                Adjacent[0].setAdjacent(this, 2);
            }
            if(toleft != null){ //add possible left object and set x orientation accordingly
                Adjacent[3] = toleft;
                Adjacent[3].setAdjacent(this, 1);
            }
        }

        public void setAdjacent(statViewObject toSet, int index){
            Adjacent[index] = toSet;
        }

        public void setTwoWayAdjacent(statViewObject toSet, int index){
            Adjacent[index] = toSet;
            switch (index){
                case 0:{
                    toSet.setAdjacent(this, 2);
                    break;
                }
                case 1:{
                    toSet.setAdjacent(this, 3);
                    break;
                }
                case 2:{
                    toSet.setAdjacent(this, 0);
                    break;
                }
                case 3:{
                    toSet.setAdjacent(this, 1);
                    toSet.setAdjacent(this, 1);
                    break;
                }
            }
        }

        public void setCurrentToAdjacent(int index){
            if(Adjacent[index] != null){
                setPlain();
                currentStatViewObject = Adjacent[index];
                currentStatViewObject.setHighLit();
            }
        }

        public void setHighLit(){
            /*System.out.println(displayText.getText() + " selected.");
            System.out.println(getTranslateX());
            System.out.println(highLit.getX());*/
            /*setAlignment(Pos.CENTER);
            getChildren().add(new Rectangle(20, 20, Color.ALICEBLUE));*/
            highLit.setVisible(true);
        }

        public void setPlain(){
            highLit.setVisible(false);
        }

        public void setText(String text){
            if(text.length() >= 30) {
                text = text.substring(0, 30);
                text += "...";
            }
            displayText.setText(text);
        }

        public abstract void performAction(); //either does nothing or performs an action. Called when the user hits the
        //return key with this display object selected. Generally makes a call to the selection pane.
    }

    private static class unselectableStatViewObject extends statViewObject{
        public unselectableStatViewObject(statViewObject above, statViewObject below){
            super(200, 60, above, below);
        }

        @Override
        public void performAction(){
            //do literally nothing, since this is just a stat and you can't manually change it.
        }
    }

    private static class selectableStatViewObject extends statViewObject{
        private Method toCall;
        private Object toCallFrom;

        public selectableStatViewObject(statViewObject above, statViewObject below
                , Method tocall, Object tocallfrom){
            super(300, 60, above, below);
            toCall = tocall;
            toCallFrom = tocallfrom;
        }

        @Override
        public void performAction(){
            if(toCallFrom instanceof playerCharacter){
                toCallFrom = currentParty[currentCharacter]; //ensure that we are calling from the current character.
            }
            SelectionPane.setMode(toCallFrom, toCall);
        }
    }
}
