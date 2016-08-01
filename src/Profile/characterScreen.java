package Profile;

import Characters.playerCharacter;
import Structures.Structure;
import Structures.orderedLLL;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * Created by Spaghetti on 7/29/2016.
 */
public class characterScreen {
    private static StackPane contentRoot;
    private static MainPane mainPane; //the primary pane for viewing stats and jobs/skills/equipment
    private static SelectionPane selectionPane; //the pane for selecting different jobs/skills/equipment
    private static boolean mainPaneIsActive = false;
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

    public static void swapToMainPane(){
        if(!mainPaneIsActive){
            contentRoot.getChildren().removeAll(selectionPane);
            contentRoot.getChildren().add(mainPane);
        }
        mainPane.initializeDisplay();
        mainPane.requestFocus();
        mainPaneIsActive = true;
    }

    public static void swapToSelectionPane(){
        if(mainPaneIsActive){
            contentRoot.getChildren().remove(mainPane);
            contentRoot.getChildren().add(selectionPane);
            selectionPane.requestFocus();
            mainPaneIsActive = false;
        }
    }

    public static ObservableList<Node> getMainPaneChildren(){
        return mainPane.getChildren();
    }

    public static StackPane getContentRoot(){
        return contentRoot;
    }

    private class MainPane extends GridPane{
        private playerCharacter Current;
        private playerCharacter leftOfCurrent;
        private playerCharacter rightOfCurrent;
        private selectableStatViewObject primaryClass;
        private selectableStatViewObject secondaryClass;
        private selectableStatViewObject passive;
        private selectableStatViewObject rightweap;
        private selectableStatViewObject leftweap;
        private selectableStatViewObject equippedarmor;
        private selectableStatViewObject accessory1;
        private selectableStatViewObject accessory2;
        private unselectableStatViewObject hp;
        private unselectableStatViewObject sp;
        private unselectableStatViewObject armor;
        private unselectableStatViewObject vit;
        private unselectableStatViewObject str;
        private unselectableStatViewObject dex;
        private unselectableStatViewObject inte;
        private unselectableStatViewObject fth;
        private unselectableStatViewObject spd;



        public MainPane(){
            setAlignment(Pos.CENTER);
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
                        Current = leftOfCurrent;
                        for(int i = 0; i < 4; ++i)
                            if(currentParty[i] == Current)
                                currentCharacter = i;
                        initializeDisplay(Current);
                    }
                }
                else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                    if(currentStatViewObject != null){
                        currentStatViewObject.setCurrentToAdjacent(2);
                    }
                    else{
                        currentStatViewObject = primaryClass;
                        primaryClass.setHighLit();
                    }
                }
                else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                    if(currentStatViewObject != null){
                        currentStatViewObject.setCurrentToAdjacent(1);
                    }
                    else if (rightOfCurrent != null){
                        Current = rightOfCurrent;
                        for(int i = 0; i < 4; ++i)
                            if(currentParty[i] == Current)
                                currentCharacter = i;
                        initializeDisplay(Current);
                    }
                }
                else if(event.getCode() == KeyCode.ESCAPE){
                    if(Game.currentMap != null)
                        Game.mainmenu.getCurrentGame().swapToMap(contentRoot);
                    else
                        Game.mainmenu.getCurrentGame().swapToOverworld(contentRoot);
                }

            });
        }

        public void addDisplays(){
            try {
                Method test = Current.getClass().getMethod("getClasses");
                Object testOutput = test.invoke(Current);
                ((orderedLLL)testOutput).Display();

                primaryClass = new selectableStatViewObject(null, null, Current.getClass().getMethod("getClasses"), Current);
                primaryClass.setTranslateX(-450);
                secondaryClass = new selectableStatViewObject(null, primaryClass, Current.getClass().getMethod("getClasses"), Current);
                passive = new selectableStatViewObject(null, secondaryClass, Current.getClass().getMethod("getPassives"), Current);
                hp = new unselectableStatViewObject(null, null);
                hp.setTranslateX(-450);
                hp.setTranslateY(150);
                sp = new unselectableStatViewObject(null, hp);
                armor = new unselectableStatViewObject(null, sp);
                vit = new unselectableStatViewObject(hp, null);
                str = new unselectableStatViewObject(sp, vit);
                dex = new unselectableStatViewObject(armor, str);
                inte = new unselectableStatViewObject(vit, null);
                fth = new unselectableStatViewObject(str, inte);
                spd = new unselectableStatViewObject(dex, fth);
                rightweap = new selectableStatViewObject(null, null, Game.Player.getClass().getMethod("getConsumables"), Game.Player);
                rightweap.setTranslateX(-450);
                rightweap.setTranslateY(600);
                leftweap = new selectableStatViewObject(null, rightweap, Game.Player.getClass().getMethod("getConsumables"), Game.Player);
                equippedarmor = new selectableStatViewObject(null, leftweap, Game.Player.getClass().getMethod("getConsumables"), Game.Player);
                accessory1 = new selectableStatViewObject(rightweap, null, Game.Player.getClass().getMethod("getConsumables"), Game.Player);
                accessory2 = new selectableStatViewObject(equippedarmor, accessory1, Game.Player.getClass().getMethod("getConsumables"), Game.Player);

                leftweap.setAdjacent(accessory1, 2);
                hp.setTwoWayAdjacent(primaryClass, 0);
                sp.setTwoWayAdjacent(secondaryClass, 0);
                armor.setTwoWayAdjacent(passive, 0);
                inte.setTwoWayAdjacent(rightweap, 2);
                fth.setAdjacent(leftweap, 2);
                spd.setTwoWayAdjacent(equippedarmor, 2);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            initializeDisplay(Current);
        }

        public void initializeDisplay(){
            currentParty = Game.Player.getParty();
            for(int i = 0; i < 4; ++i)
                if(currentParty[i] != null)
                    currentCharacter = i;
            initializeDisplay(currentParty[currentCharacter]);
        }

        public void initializeDisplay(playerCharacter current) {
            currentParty = Game.Player.getParty();
            currentStatViewObject = primaryClass;
            primaryClass.setHighLit();
            Current = current;
            decideLeftOfCurrent();
            decideRightOfCurrent();
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

        private void decideLeftOfCurrent(){
            for(int i = currentCharacter-1; i > 0 ; --i){
                if(currentParty[i] != null){
                    leftOfCurrent = currentParty[i];
                    break;
                }
            }
        }

        private void decideRightOfCurrent(){
            System.out.println(currentCharacter);
            if(currentParty == null)
                System.out.println("Literally no party lol");
            for(int i = currentCharacter+1; i < 4; ++i){
                if(currentParty[i] != null){
                    rightOfCurrent = currentParty[i];
                    break;
                }
            }
        }
    }

    private static class SelectionPane extends GridPane{
        public SelectionPane(){

        }

        public static void setMode(Object toCallFrom, Method toCall){

        }
    }

    private abstract class statViewObject extends StackPane{
        private statViewObject [] Adjacent = new statViewObject[4];
        private Rectangle highLit;
        private Text displayText;

        public statViewObject(int xsize, int ysize, statViewObject above, statViewObject toleft){
            setAlignment(Pos.CENTER);
            setMaxSize(xsize, ysize);
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
                setTranslateY(Adjacent[0].getTranslateY() + ysize + 10);
                Adjacent[0].setAdjacent(this, 2);
            }
            if(toleft != null){ //add possible left object and set x orientation accordingly
                Adjacent[3] = toleft;
                setTranslateX(Adjacent[3].getTranslateX() + xsize + 10 );
                Adjacent[3].setAdjacent(this, 1);
            }

            getMainPaneChildren().add(this);
        }

        public void setAdjacent(statViewObject toSet, int index){
            Adjacent[index] = toSet;
        }

        public void setTwoWayAdjacent(statViewObject toSet, int index){
            Adjacent[index] = toSet;
            switch (index){
                case 0:{
                    toSet.setAdjacent(this, 2);
                }
                case 1:{
                    toSet.setAdjacent(this, 3);
                }
                case 2:{
                    toSet.setAdjacent(this, 0);
                }
                case 3:{
                    toSet.setAdjacent(this, 1);

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
            highLit.setVisible(true);
        }

        public void setPlain(){
            highLit.setVisible(false);
        }

        public void setText(String text){
            displayText.setText(text);
        }

        public abstract void performAction(); //either does nothing or performs an action. Called when the user hits the
        //return key with this display object selected. Generally makes a call to the selection pane.
    }

    private class unselectableStatViewObject extends statViewObject{
        public unselectableStatViewObject(statViewObject above, statViewObject below){
            super(200, 100, above, below);
        }

        @Override
        public void performAction(){
            //do literally nothing, since this is just a stat and you can't manually change it.
        }
    }

    private class selectableStatViewObject extends statViewObject{
        private Method toCall;
        private Object toCallFrom;

        public selectableStatViewObject(statViewObject above, statViewObject below
                , Method tocall, Object tocallfrom){
            super(200, 100, above, below);
            toCall = tocall;
            toCallFrom = tocallfrom;
        }

        @Override
        public void performAction(){
            //SelectionPane.setMode(toCallFrom, toCall);
        }
    }
}
