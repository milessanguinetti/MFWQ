package Profile;

import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
public class settingsScreen extends StackPane {
    private static StackPane sliderpane = new StackPane();
    private static int currentSlider;
    private static int numSliders;
    private static Slider [] Sliders;
    private static int sliderWidth;

    public settingsScreen(){
        numSliders = 3;
        setAlignment(Pos.CENTER);
        sliderpane.setAlignment(Pos.CENTER);
        Rectangle background = new Rectangle(3000, 3000);
        background.setFill(Color.BLACK);
        getChildren().add(background);
        Text Title = new Text("Settings");
        Title.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 65));
        Title.setFill(Color.GOLDENROD);
        Title.setTranslateY(-340);
        getChildren().add(Title);
        sliderWidth = 500;
        Sliders = new Slider[numSliders];
        Sliders[0] = new masterVolumeSlider();
        Sliders[1] = new musicVolumeSlider();
        Sliders[2] = new effectsVolumeSlider();

        Text [] labels = new Text[numSliders];
        labels[0] = new Text("Master Volume");
        labels[1] = new Text("Music Volume");
        labels[2] = new Text("Effects Volume");
        for(int i = 0; i < numSliders; ++i){
            labels[i].setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30));
            labels[i].setFill(Color.GOLDENROD);
            labels[i].setTranslateY(-220 + 200*i);
            getChildren().add(labels[i]);
            Sliders[i].setTranslateY(-150 + 200*i);
            sliderpane.getChildren().add(Sliders[i]);
        }
        applyButton apply = new applyButton();
        apply.setTranslateY(350);
        sliderpane.getChildren().add(apply);
        getChildren().add(sliderpane);
        Sliders[currentSlider].current.setHighlit(true);

        setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                if(currentSlider != 0) {
                    Sliders[currentSlider].current.setHighlit(false);
                    --currentSlider;
                    Sliders[currentSlider].current.setHighlit(true);
                }
            }
            else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
                Sliders[currentSlider].adjustCurrentSegment(-1);
            }
            else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                if(currentSlider != numSliders-1) {
                    Sliders[currentSlider].current.setHighlit(false);
                    ++currentSlider;
                    Sliders[currentSlider].current.setHighlit(true);
                }
            }
            else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                Sliders[currentSlider].adjustCurrentSegment(1);
            }
            else if(event.getCode() == KeyCode.ESCAPE) {
                if(Game.mainmenu.isMostRecentPane())
                    Game.mainmenu.getCurrentGame().swapToMainMenu(this);
                else if(Game.currentMap == null)
                    Game.mainmenu.getCurrentGame().swapToOverworld(this);
                else
                    Game.mainmenu.getCurrentGame().swapToMap(this);
            }
        });
    }

    public static void setCurrentSlider(Slider toset){
        for(int i = 0; i < numSliders; ++i){
            if(Sliders[i] == toset) {
                Sliders[currentSlider].current.setHighlit(false);
                currentSlider = i;
                Sliders[currentSlider].current.setHighlit(true);
            }
        }
    }

    public static boolean isCurrentSlider(Slider totest){
        return Sliders[currentSlider] == totest;
    }

    private abstract static class Slider extends HBox{
        protected sliderSegment current;

        public Slider(int numsegments){
            setMaxSize(sliderWidth, 100);

            for(int i = 0; i < numsegments; ++i){
                getChildren().add(initializeSegment(i));
            }
            current = ((sliderSegment)getChildren().get(numsegments-1));
            current.setSelected(true);
        }

        public void adjustCurrentSegment(int toAdjust){
            int currentIndex = 0;
            for(int i = 0; i < getChildren().size(); ++i){
                if(getChildren().get(i) == current)
                    currentIndex = i;
            }
            if(currentIndex+toAdjust >= 0 && currentIndex+toAdjust < getChildren().size()) {
                setCurrentSegment((sliderSegment) getChildren().get(currentIndex + toAdjust));
                current.setHighlit(true);
            }
        }

        public void setCurrentSegment(sliderSegment toset){
            if(current != toset) {
                if(current != null)
                    current.setSelected(false);
                current = toset;
                current.setSelected(true);
                performAction();
            }
        }

        public abstract void performAction();

        public abstract sliderSegment initializeSegment(int which);
    }

    private static class masterVolumeSlider extends Slider{
        public masterVolumeSlider(){
            super(6);
        }

        @Override
        public void performAction(){
            Game.mainmenu.getCurrentGame().setMasterVolume(current.getValue());
        }

        @Override
        public sliderSegment initializeSegment(int which){
            return new sliderSegment(this, sliderWidth/6, .2f*which, which + "");
        }
    }

    private static class musicVolumeSlider extends Slider{
        public musicVolumeSlider(){
            super(6);
        }

        @Override
        public void performAction(){
            if(current != null)
                Game.mainmenu.getCurrentGame().setMusicVolume(current.getValue());
        }

        @Override
        public sliderSegment initializeSegment(int which){
            return new sliderSegment(this, sliderWidth/6, .2f*which, which + "");
        }
    }

    private static class effectsVolumeSlider extends Slider{
        public effectsVolumeSlider(){
            super(6);
        }

        @Override
        public void performAction(){
            Game.battle.setSoundEffectVolume(current.getValue());
        }

        @Override
        public sliderSegment initializeSegment(int which){
            return new sliderSegment(this, sliderWidth/6, .2f*which, which + "");
        }
    }

    private static class sliderSegment extends StackPane{
        private float Value;
        private Slider Parent;
        private Rectangle Selected1;
        private Rectangle Selected2;

        public sliderSegment(Slider parent, int width, float value, String label){
            Parent = parent;
            Value = value;
            setAlignment(Pos.CENTER);
            Rectangle background = new Rectangle(width, 20);
            background.setFill(Color.GRAY);
            Selected1 = new Rectangle(30, 30);
            Selected1.setFill(Color.BLACK);
            Selected1.setOpacity(0);
            Selected2 = new Rectangle(35, 35);
            Selected2.setFill(Color.GRAY);
            Selected2.setOpacity(0);
            setMaxSize(width, 50);
            Text Label = new Text(label);
            Label.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 20));
            Label.setFill(Color.WHITE);
            Label.setTranslateY(30);
            getChildren().addAll(background, Selected2, Selected1, Label);

            setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent event) {
                    setCurrentSlider(parent);
                    startFullDrag();
                }
            });

            setOnMouseDragEntered(event -> {
                if(isCurrentSlider(Parent)) {
                    setHighlit(true);
                    Parent.setCurrentSegment(this);
                }
            });

            setOnMouseClicked(event -> {
                Parent.setCurrentSegment(this);
                setCurrentSlider(parent);
            });
        }

        public float getValue(){
            return Value;
        }

        public void setSelected(boolean isSelected){
            if(isSelected) {
                Selected1.setOpacity(1);
                Selected2.setOpacity(1);
            }
            else{
                Selected1.setOpacity(0);
                Selected2.setOpacity(0);
                setHighlit(false);
            }
        }

        public void setHighlit(boolean isHighlit){
            if(isHighlit)
                Selected1.setFill(Color.GOLDENROD);
            else
                Selected1.setFill(Color.BLACK);
        }
    }

    private static class applyButton extends StackPane {
        private Text buttonText;
        private Rectangle buttonShape;
        private LinearGradient buttonGradient;

        public applyButton() {
            buttonGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                    //add a reddened gradient to the menuButton item
                    new Stop(0, Color.BLACK),
                    new Stop(.2, Color.DARKRED),
                    new Stop(.8, Color.DARKRED),
                    new Stop(1, Color.BLACK)
            });

            buttonShape = new Rectangle(300, 50);
            buttonShape.setOpacity(.4);

            buttonText = new Text("Apply"); //initialize our text to the button's name
            buttonText.setTranslateY(-5);
            buttonText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 40));

            setMaxSize(300, 50);
            setAlignment(Pos.CENTER); //center the button's sub-components.
            getChildren().addAll(buttonShape, buttonText); //add the shape and text to the button.

            setPlain();

            setOnMouseEntered(event -> {
                setHighLit();
            });

            setOnMousePressed(event -> {
                setSelected();
            });

            setOnMouseReleased(event -> {
                setUnselected();
                if(Game.mainmenu.isMostRecentPane())
                    Game.mainmenu.getCurrentGame().swapToMainMenu(Game.settings);
                else if(Game.currentMap == null)
                    Game.mainmenu.getCurrentGame().swapToOverworld(Game.settings);
                else
                    Game.mainmenu.getCurrentGame().swapToMap(Game.settings);
            });

            setOnMouseExited(event -> {
                setPlain();
            });
        }

        public void setHighLit() {
            buttonShape.setFill(buttonGradient);
            buttonShape.setOpacity(.6);
            buttonText.setFill(Color.WHITE);
        }

        public void setPlain() {
            buttonShape.setFill(Color.BLACK);
            buttonShape.setOpacity(.4);
            buttonText.setFill(Color.WHITE);
        }

        public void setSelected(){
            buttonShape.setFill(Color.RED);
        }

        public void setUnselected(){
            buttonShape.setFill(buttonGradient);
        }
    }
}
