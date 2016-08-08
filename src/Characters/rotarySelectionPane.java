package Characters;

import Characters.Classes.characterClass;
import Characters.Inventory.*;
import Characters.Skills.Passive.passiveSkill;
import Profile.characterScreen;
import Structures.LLLnode;
import com.sun.org.apache.xerces.internal.dom.ParentNode;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created by Spaghetti on 8/3/2016.
 */
//a class for displaying a list of selectable objects (passive skills + classes at time of writing)
//in a user-friendly custom pane for use in the characterscreen menu
public class rotarySelectionPane extends StackPane{
    private static final int twoleftx = -200; //these variables denote x and y coordinates of points
    private static final int twolefty = -66; //that we use to set translate the coordinates of the different
    private static final int oneleftx = -300; //nodes as they rotate across the pane.
    private static final int onelefty = 66;
    private static final int centerx = 0;
    private static final int centery = 200;
    private static final int onerightx = 300;
    private static final int onerighty = 66;
    private static final int tworightx = 200;
    private static final int tworighty = -66;
    private static final int backx = 0;
    private static final int backy = -150;

    private static playerCharacter displayingCharacter;
    private static rotarySelectionIcon Current;
    private static boolean isDone;
    private static boolean isAnimating;
    private static rotarySelectionPane mostRecent;

    public rotarySelectionPane(LLLnode toCopy){
        isDone = false;
        isAnimating = false;
        setAlignment(Pos.CENTER);
        if(toCopy == null){
            Text emptyText = new Text("This character has no available passive skills.");
            emptyText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 40));
            emptyText.setFill(Color.GOLDENROD);
            getChildren().add(emptyText);
        }
        else{
            Current = new skillSelectionIcon((passiveSkill)toCopy.returnData());
            Current.setTranslateX(centerx);
            Current.setTranslateY(centery);
            getChildren().add(Current);
            rotarySelectionIcon temp = Current;

            Current.setFullOrPartialDescription(true);

            while(toCopy.getNext() != null){
                toCopy = toCopy.getNext();
                temp.Next = new skillSelectionIcon((passiveSkill) toCopy.returnData());
                temp.Next.Prev = temp;
                temp = temp.Next;
            }
            if(Current.Next != null){
                Current.Next.setTranslateX(onerightx);
                Current.Next.setTranslateY(onerighty);
                getChildren().add(Current.Next);
                Current.Next.toFront();
                if(Current.Next.Next != null){
                    Current.Next.Next.setTranslateX(tworightx);
                    Current.Next.Next.setTranslateY(tworighty);
                    Current.Next.Next.setOpacity(.5);
                    getChildren().add(Current.Next.Next);
                }
            }
            applyKeySettings();
        }
    }

    public rotarySelectionPane(LLLnode toCopy, boolean isForPrimary){
        isDone = false;
        setAlignment(Pos.CENTER);
        if(toCopy == null){
            Text emptyText = new Text("This character has no available classes.");
            emptyText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 40));
            emptyText.setFill(Color.GOLDENROD);
            getChildren().add(emptyText);
        }
        else{
            Current = new classSelectionIcon((characterClass)toCopy.returnData(), isForPrimary);
            Current.setTranslateX(centerx);
            Current.setTranslateY(centery);
            getChildren().add(Current);
            rotarySelectionIcon temp = Current;

            Current.setFullOrPartialDescription(true);

            while(toCopy.getNext() != null){
                toCopy = toCopy.getNext();
                temp.Next = new classSelectionIcon((characterClass)toCopy.returnData(), isForPrimary);
                temp.Next.Prev = temp;
                temp = temp.Next;
                temp.setOpacity(0);
                temp.setTranslateX(backx);
                temp.setTranslateY(backy);
                getChildren().add(temp);
            }
            if(Current.Next != null){
                Current.Next.setTranslateX(onerightx);
                Current.Next.setTranslateY(onerighty);
                Current.Next.setOpacity(1);
                Current.Next.toFront();
                if(Current.Next.Next != null){
                    Current.Next.Next.setTranslateX(tworightx);
                    Current.Next.Next.setTranslateY(tworighty);
                    Current.Next.Next.setOpacity(.5);
                }
            }
            applyKeySettings();
        }
    }

    public rotarySelectionPane(playerCharacter [] party, Item toUse){
        isDone = false;
        mostRecent = this;
        Current = null; //reset current.
        setAlignment(Pos.CENTER);
        int numCharacters = 0;
        for(int i = 0; i < 4; ++i) {
            if(party[i] != null){
                if(Current != null) {
                    Current.Next = new characterSelectionIcon(party[i], toUse);
                    Current.Next.Prev = Current;
                    Current = Current.Next;
                }
                if(numCharacters == 0) {
                    Current = new characterSelectionIcon(party[i], toUse);
                    Current.setFullOrPartialDescription(true);
                    Current.setTranslateX(centerx);
                    Current.setTranslateY(centery);
                    Current.toFront();
                }
                else if(numCharacters == 1) {
                    Current.setTranslateX(onerightx);
                    Current.setTranslateY(onerighty);
                    Current.toFront();
                }
                else if(numCharacters == 2) {
                    Current.setTranslateX(tworightx);
                    Current.setTranslateY(twolefty);
                    Current.setOpacity(.5);
                }
                else{
                    Current.setTranslateX(backx);
                    Current.setTranslateY(backy);
                    Current.setOpacity(0);
                }
                ++numCharacters;
                getChildren().add(Current);
            }
        }
        for(int i = 1; i < numCharacters; ++i){
            Current = Current.Prev;
        }
        applyKeySettings();
    }

    private void applyKeySettings(){
        //add a gamecharacter sprite to the pane. this obviously isn't a key setting, but the handling on the method
        //is identical for all constructors, so the code is placed in this method.
        mostRecent = this;
        setDisplayingCharacter(characterScreen.getCurrentCharacter());

        setOnKeyReleased(event -> {
            if(isAnimating)
                return;
            switch (event.getCode()){
                case ENTER:{
                    Current.performFunction();
                    break;
                }
                case RIGHT:{
                    if(Current.Next != null)
                        Current.Rotate(false);
                    break;
                }
                case D:{
                    if(Current.Next != null)
                        Current.Rotate(false);
                    break;
                }
                case LEFT:{
                    if(Current.Prev != null)
                        Current.Rotate(true);
                    break;
                }
                case A:{
                    if(Current.Prev != null)
                        Current.Rotate(true);
                    break;
                }
            }
        });
    }

    public static void setDisplayingCharacter(playerCharacter toDisplay){
        if(displayingCharacter != null){
            displayingCharacter.Animate(false);
            mostRecent.getChildren().remove(displayingCharacter);
        }
        displayingCharacter = toDisplay;
        toDisplay.setTranslateX(0);
        toDisplay.setTranslateY(0);
        mostRecent.getChildren().add(toDisplay);
        toDisplay.toBack();
        toDisplay.Animate(true);
    }

    public static boolean isDone(){
        if(isDone && displayingCharacter != null) //stop animation if we're leaving this screen.
            displayingCharacter.Animate(false);
        return isDone && !isAnimating;
    }

    //base class for dispay icons on the pane
    private static abstract class rotarySelectionIcon extends VBox{
        private rotarySelectionIcon Next;
        private rotarySelectionIcon Prev;
        protected Text Description;
        protected static Text errorText;

        public rotarySelectionIcon(String name, String description){
            setAlignment(Pos.CENTER);
            setMinSize(350, 400);
            setMaxSize(350, 400);
            Text Name = new Text(name);
            Name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 30));
            Name.setFill(Color.GOLDENROD);
            getChildren().add(Name);
            Description = new Text(description);
            Description.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 20));
            Description.setFill(Color.WHITE);
            Description.setWrappingWidth(350);
            Description.setTextAlignment(TextAlignment.CENTER);
            getChildren().add(Description);

            setOnMouseReleased(event -> {
                characterSelectionIcon.ignoreOne = false;
                if(Current == this){
                    performFunction();
                }
                else{
                    if(Current.Prev == this){
                        Current.Rotate(true);
                    }
                    else if(Current.Next == this){
                        Current.Rotate(false);
                    }
                }
            });
        }

        public abstract void setFullOrPartialDescription(boolean isFull);

        public void Rotate(boolean toRight){
            if(errorText != null){
                getChildren().remove(errorText);
                errorText = null;
            }
            isAnimating = true;
            Current.setFullOrPartialDescription(false);
            if(toRight){ //case for rotation to right.
                //center rotation
                (generatePathTransition(Current, onerightx, onerighty)).play();
                if(Current.Prev.Prev != null) { //two left rotation
                    (generatePathTransition(Current.Prev.Prev, oneleftx, onerighty)).play();
                    (generateOpacityTimeline(Current.Prev.Prev, 1f)).play();
                    if(Current.Prev.Prev.Prev != null){ //three left rotation
                        (generatePathTransition(Current.Prev.Prev.Prev, twoleftx, twolefty)).play();
                        (generateOpacityTimeline(Current.Prev.Prev.Prev, .5f)).play();
                    }
                }
                if(Current.Next != null) { //one right rotation
                    (generatePathTransition(Current.Next, tworightx, tworighty)).play();
                    (generateOpacityTimeline(Current.Next, .5f)).play();
                    if(Current.Next.Next != null){ //two right rotation
                        (generatePathTransition(Current.Next.Next, backx, backy)).play();
                        (generateOpacityTimeline(Current.Next.Next, 0f)).play();
                    }
                }
                //one left rotation
                PathTransition last = generatePathTransition(Current.Prev, centerx, centery);
                last.setOnFinished(event -> {
                    isAnimating = false;
                    Current.setFullOrPartialDescription(true);
                });
                last.play();
                Current = Current.Prev;
            }
            else{ //case for rotation to left.
                //center rotation
                (generatePathTransition(Current, oneleftx, onelefty)).play();
                if(Current.Next.Next != null) { //two right rotation
                    (generatePathTransition(Current.Next.Next, onerightx, onerighty)).play();
                    (generateOpacityTimeline(Current.Next.Next, 1f)).play();
                    if(Current.Next.Next.Next != null){ //three right rotation
                        (generatePathTransition(Current.Next.Next.Next, tworightx, tworighty)).play();
                        (generateOpacityTimeline(Current.Next.Next.Next, .5f)).play();
                    }
                }
                if(Current.Prev != null) { //one left rotation
                    (generatePathTransition(Current.Prev, twoleftx, twolefty)).play();
                    (generateOpacityTimeline(Current.Prev, .5f)).play();
                    if(Current.Prev.Prev != null){ //two left rotation
                        (generatePathTransition(Current.Prev.Prev, backx, backy)).play();
                        (generateOpacityTimeline(Current.Prev.Prev, 0f)).play();
                    }
                }
                //one right rotation
                PathTransition last = generatePathTransition(Current.Next, centerx, centery);
                last.setOnFinished(event -> {
                    Current.setFullOrPartialDescription(true);
                    isAnimating = false;
                });
                last.play();
                Current = Current.Next;
            }
        }

        private PathTransition generatePathTransition(rotarySelectionIcon which, int endx, int endy){
            PathTransition toReturn = new PathTransition();
            toReturn.setCycleCount(1);
            toReturn.setDuration(Duration.millis(1000));
            toReturn.setNode(which);
            Path path = new Path();
            //adjustments for calculations
            path.getElements().add(new MoveTo(which.getTranslateX()+which.getWidth()/2,
                    which.getTranslateY()+which.getHeight()/2));
            path.getElements().add(new CubicCurveTo((endx + which.getTranslateX() + which.getWidth())/1.7,
                    (endy + which.getTranslateY() + which.getHeight())/1.7,
                    (endx + which.getTranslateX() + which.getWidth())/1.7,
                    (endy + which.getTranslateY() + which.getHeight())/1.7,
                    endx + which.getWidth()/2, endy + which.getHeight()/2));
            toReturn.setPath(path);
            toReturn.setInterpolator(Interpolator.EASE_OUT);
            return toReturn;
        }

        private Timeline generateOpacityTimeline(rotarySelectionIcon which, float endOpacity){
            if(endOpacity != 1.0){
                which.toBack();
            }
            else{
                which.toFront();
            }
            Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    new KeyValue(which.opacityProperty(), endOpacity)));
            return timeline;
        }

        public abstract void performFunction();
    }

    private static class classSelectionIcon extends rotarySelectionIcon {
        private characterClass thisClass;
        private boolean isForPrimaryClass;

        public classSelectionIcon(characterClass toCopy, boolean isforprimaryclass) {
            super(toCopy.getClassName(), "Level: " + toCopy.getJlevel());
            isForPrimaryClass = isforprimaryclass;
            thisClass = toCopy;
        }

        @Override
        public void setFullOrPartialDescription(boolean isFull){
            if(isFull){
                Description.setText("Level: " + thisClass.getJlevel() + '\n' + thisClass.getClassDescription());
            }
            else{
                Description.setText("Level: " + thisClass.getJlevel());
            }
        }

        @Override
        public void performFunction() {
            if (isForPrimaryClass) {
                characterScreen.getCurrentCharacter().setPrimaryClass(thisClass);
                isDone = true;
            } else {
                if (!characterScreen.getCurrentCharacter().setSecondaryClass(thisClass)) {
                    if(errorText == null) {
                        errorText = new Text("This class is selected as your primary class!");
                        errorText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 30));
                        errorText.setWrappingWidth(350);
                        errorText.setTranslateY(backy-250);
                        errorText.setFill(Color.RED);
                        errorText.setTextAlignment(TextAlignment.CENTER);
                        getChildren().add(errorText);
                    }
                } else {
                    isDone = true;
                }
            }

        }
    }

    private class skillSelectionIcon extends rotarySelectionIcon {
        private passiveSkill thisSkill;

        public skillSelectionIcon(passiveSkill toCopy) {
            super(toCopy.getSkillName(), "");
            thisSkill = toCopy;
        }

        @Override
        public void setFullOrPartialDescription(boolean isFull){
            if(isFull){
                Description.setText(thisSkill.getDescription());
            }
            else{
                Description.setText("");
            }
        }

        @Override
        public void performFunction() {
            characterScreen.getCurrentCharacter().setCurrentPassive(thisSkill);
            isDone = true;
        }
    }

    private static class characterSelectionIcon extends rotarySelectionIcon{
        private playerCharacter thisCharacter;
        private static Item toUse;
        private static boolean ignoreOne; //ensures that we do not immediately close the window.

        public characterSelectionIcon(playerCharacter toCopy, Item touse) {
            super(toCopy.getName(), "");
            thisCharacter = toCopy;
            toUse = touse;
            ignoreOne = true;
        }

        @Override
        public void setFullOrPartialDescription(boolean isFull){
            if(isFull){
                //update the character that we're currently displaying.
                setDisplayingCharacter(thisCharacter);

                if(toUse instanceof equipableItem){ //if this is an equipable item, we want to compare what the
                                                    //character currently has equipped with toUse.
                    if(toUse instanceof Weapon){
                        Description.setText("Current weapons: " + thisCharacter.getWeaponName(1) + " and " +
                                thisCharacter.getWeaponName(2) + "\n" + "Replace with " + toUse.returnKey() + "?");
                    }
                    else if(toUse instanceof Armor){
                        Description.setText("Current armor: " + thisCharacter.getArmorName() +
                                "\n" + "Replace with " + toUse.returnKey() + "?");
                    }
                    else if(toUse instanceof Accessory){
                        Description.setText("Current accessories: " + thisCharacter.getAccessoryName(1) + " and " +
                                thisCharacter.getAccessoryName(2) + "\n" + "Replace with " + toUse.returnKey() + "?");
                    }

                }
                else{ //otherwise, it's invariable a consumable, so we display the character's stats.
                    Description.setText("HP: " + thisCharacter.getHP() + "/" + thisCharacter.getHPCap() + "\n"
                            + "SP: " + thisCharacter.getSP() + "/" + thisCharacter.getSPCap());
                }
            }
            else{
                Description.setText("");
            }
        }

        @Override
        public void performFunction() {
            if(ignoreOne){ //ensures that we don't immediately close the window.
                ignoreOne = false;
                return;
            }
            if(toUse.Use(thisCharacter)){
                isDone = true;
            }
            else{
                if(errorText == null) {
                    errorText = new Text("This item cannot be used by " + thisCharacter.getName() + "!");
                    errorText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 30));
                    errorText.setWrappingWidth(300);
                    errorText.setTranslateY(backy-250);
                    errorText.setFill(Color.RED);
                    errorText.setTextAlignment(TextAlignment.CENTER);
                    getChildren().add(errorText);
                }
            }
        }
    }
}
