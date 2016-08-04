package Characters;

import Characters.Classes.characterClass;
import Characters.Skills.Passive.passiveSkill;
import Profile.characterScreen;
import Structures.LLLnode;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
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

    private static rotarySelectionIcon Current;
    private static boolean isDone;
    private static boolean isAnimating;

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
                if(Current.Next.Next != null){
                    Current.Next.Next.setTranslateX(tworightx);
                    Current.Next.Next.setTranslateY(tworighty);
                    Current.Next.Next.setOpacity(.5);
                }
            }
            applyKeySettings();
        }
    }

    private void applyKeySettings(){
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

    public static boolean isDone(){
        return isDone;
    }

    //base class for dispay icons on the pane
    private static abstract class rotarySelectionIcon extends StackPane{
        private rotarySelectionIcon Next;
        private rotarySelectionIcon Prev;
        protected static Text errorText;

        public rotarySelectionIcon(String name, String description){
            setMinSize(150, 200);
            setMaxSize(150, 200);
            setAlignment(Pos.CENTER);
            Text Name = new Text(name);
            Name.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 30));
            Name.setFill(Color.GOLDENROD);
            Name.setTranslateY(30);
            getChildren().add(Name);
            Text Description = new Text(description);
            Description.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 20));
            Description.setFill(Color.WHITE);
            Description.setTranslateY(60);
            Description.setWrappingWidth(150);
            Description.setTextAlignment(TextAlignment.CENTER);
            getChildren().add(Description);

            setOnMouseReleased(event -> {
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
            setOnMouseEntered(event -> {
                if(Current == this || Current.Prev == this || Current.Next == this){
                    this.toFront();
                }
                else{
                    this.toBack();
                }
            });
        }

        public void Rotate(boolean toRight){
            if(errorText != null){
                getChildren().remove(errorText);
                errorText = null;
            }
            isAnimating = true;
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
            path.getElements().add(new MoveTo(which.getTranslateX()+which.getWidth()/2,
                    which.getTranslateY()+which.getHeight()/2));
            path.getElements().add(new CubicCurveTo((endx + which.getTranslateX()) / 2,
                    (endy + which.getTranslateY()) / 2, (endx + which.getTranslateX()) /2,
                    (endy + which.getTranslateY()) / 2, endx, endy));
            toReturn.setPath(path);
            return toReturn;
        }

        private Timeline generateOpacityTimeline(rotarySelectionIcon which, float endOpacity){
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
        public void performFunction() {
            if (isForPrimaryClass) {
                characterScreen.getCurrentCharacter().setPrimaryClass(thisClass);
                isDone = true;
            } else {
                if (!characterScreen.getCurrentCharacter().setSecondaryClass(thisClass)) {
                    if(errorText == null) {
                        errorText = new Text("This class is selected as your primary class!");
                        errorText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 30));
                        errorText.setWrappingWidth(300);
                        errorText.setTranslateY(-150);
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
            super(toCopy.getSkillName(), toCopy.getDescription());
            thisSkill = toCopy;
        }

        @Override
        public void performFunction() {
            characterScreen.getCurrentCharacter().setCurrentPassive(thisSkill);
            isDone = true;
        }
    }
}
