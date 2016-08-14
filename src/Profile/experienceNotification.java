package Profile;

import Characters.gameCharacter;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import java.util.Random;

/**
 * Created by Spaghetti on 7/20/2016.
 */
public class experienceNotification extends StackPane{
    private static characterViewWindow characters;
    private static expBar expBar;
    private static expBar jexpBar;
    private static expText expText;
    private static boolean active = false;
    private static boolean animated;
    private static boolean displayingmessage;

    public experienceNotification(){
        setAlignment(Pos.CENTER);
        Rectangle backdrop = new Rectangle(3000, 3000);
        backdrop.setFill(Color.BLACK);
        backdrop.setOpacity(.3);
        Rectangle background = new Rectangle(800, 500);
        background.setFill(Color.BLACK);
        expBar = new expBar(true);
        expBar.setTranslateX(-170);
        expBar.setTranslateY(-35);
        jexpBar = new expBar(false);
        jexpBar.setTranslateX(230);
        jexpBar.setTranslateY(-35);
        expText = new expText();
        expText.setTranslateY(130);
        getChildren().addAll(backdrop, background, expText, jexpBar, expBar);
    }

    public boolean isActive(){
        return active;
    }

    public boolean handleInput(){
        if(!active)
            return false;
        if(!animated) {
            if(gainEventQueueNode.RecursivePop()) {
                active = false;
                Game.lootNotificationToFront();
            }
        }
        return true;
    }

    public static void setText(String toset){
        expText.setText(toset);
    }

    public void updateAndAnimate(){
        active = true;
        if(characters != null)
            getChildren().remove(characters);
        characters = new characterViewWindow(Game.Player.getParty());
        //getChildren().removeAll(expBar, jexpBar); //ensure that these remain on top.
        getChildren().add(characters);
        jexpBar.toFront();
        expBar.toFront();
        jexpBar.setText("");
        expBar.setText("");
    }

    public static void queueExpEvent(String message, float start, float end, boolean isBaseExp, int level, int jlevel){
        new gainEventQueueNode(message, start, end, isBaseExp, level, jlevel);
    }

    private class characterViewWindow extends StackPane{
        private gameCharacter [] party;
        private int currentCharacter = 0;

        public characterViewWindow(gameCharacter [] characters){
            setTranslateY(-150);
            setAlignment(Pos.CENTER);
            party = characters;
            Rectangle background = new Rectangle(500, 150);
            background.setFill(Color.LIGHTGRAY);
            getChildren().add(background);

            for(int i = 0; i < 4; ++i){
                if(party[i] != null){
                    party[i].setTranslateY(0);
                    party[i].setTranslateX(100*i-150);
                    party[i].Animate(true);
                    getChildren().add(party[i]);
                }
            }
        }

        public gameCharacter returnCurrentCharacter(){
            return party[currentCharacter];
        }

        public void incrementCurrentCharacter(){
            if(party[currentCharacter] != null){
                party[currentCharacter].setPlain();
            }
            while (currentCharacter != 3 && (party[currentCharacter] == null ||
                    (party[currentCharacter] != null && !party[currentCharacter].isAlive()))) {
                ++currentCharacter;
            }
            if(party[currentCharacter] != null){
                party[currentCharacter].setTargeted();
            }
        }
    }

    private class expBar extends StackPane{
        private Text levelText;
        private Rectangle bar;
        private Rectangle barClip;
        private boolean isBaseExp;
        private int [] dingrandomizer = new int[30]; //randomizes the level up animation
        private int current = 0; //current integer index in dingrandomizer

        public expBar(boolean isbaseexp){
            isBaseExp = isbaseexp;

            Random rand = new Random();
            for(int i = 0; i < 30; ++i)
                dingrandomizer[i] = 5 + rand.nextInt(10);
            setAlignment(Pos.CENTER_LEFT);
            setMaxSize(300, 50);
            Rectangle background = new Rectangle(300, 50);
            background.setFill(Color.DARKGRAY);
            Rectangle barBackground = new Rectangle(280, 40);
            barBackground.setFill(Color.BLACK);
            barBackground.setTranslateX(10);
            bar = new Rectangle(280, 40);

            levelText = new Text();
            levelText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.BOLD, 30));
            levelText.setTranslateX(-50);
            getChildren().add(levelText);
            if(isBaseExp) {
                bar.setFill(Color.ORANGE);
                levelText.setFill(Color.ORANGE);
            }
            else {
                bar.setFill(Color.BLUE);
                levelText.setFill(Color.BLUE);
            }
            bar.setTranslateX(10);
            bar.setVisible(false);
            barClip = new Rectangle(280, 40);
            bar.setClip(barClip);
            barClip.setWidth(280);
            getChildren().addAll(background, barBackground, bar);
        }

        public void animateDingStar(){
            ++current;
            Polygon star = new Polygon();
            star.setTranslateX(270);
            star.setTranslateY(-55+dingrandomizer[current%30]*5);
            star.getPoints().addAll(new Double[]{10.0, 0.0, 12.5, 8.0, 20.0, 8.0, 13.8, 12.5, 16.0, 20.0, 10.0, 15.5,
            4.0, 20.0, 6.2, 12.5, 0.0, 8.0, 9.5, 8.0}); //draw a star
            if(isBaseExp)
                star.setFill(Color.ORANGE);
            else
                star.setFill(Color.BLUE);
            star.setRotate(dingrandomizer[current%30]*.1f-.3f);
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            final KeyValue kv1 = new KeyValue(star.translateYProperty(), -215 + dingrandomizer[(current)%30]*23,
                    Interpolator.EASE_OUT); //y property
            /*final KeyValue kv2 = new KeyValue(star.translateYProperty(),  -50 + dingrandomizer[current%10]*6,
                    Interpolator.EASE_IN);*/
            final KeyValue kv3 = new KeyValue(star.translateXProperty(), 170 +dingrandomizer[(current+10)%30]*17,
                    Interpolator.EASE_IN); //x property
            final KeyValue kv4 = new KeyValue(star.opacityProperty(), 0.35, Interpolator.EASE_IN); //opacity
            final KeyFrame kf1 = new KeyFrame(Duration.millis(700), kv1, kv3);
            final KeyFrame kf2 = new KeyFrame(Duration.millis(700), e ->{
                getChildren().remove(star);
            });
            final KeyFrame kf3 = new KeyFrame(Duration.millis(700), kv4);
            timeline.getKeyFrames().add(kf1);
            timeline.getKeyFrames().add(kf2);
            timeline.getKeyFrames().add(kf3);
            getChildren().add(star);
            timeline.play();
        }

        public void animateExpGain(float start, float end, String message, int level){
            bar.setVisible(true);
            levelText.setText(level + "");
            animated = true;
            barClip.setWidth(280*start);
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            final KeyValue kv1 = new KeyValue(barClip.widthProperty(), 280*end, Interpolator.EASE_IN); //width property
            final KeyFrame kf1 = new KeyFrame(Duration.millis(200 + 400*(end-start)), kv1);
            final KeyFrame kf2 = new KeyFrame(Duration.millis(200 + 400*(end-start)), e ->{
                experienceNotification.expText.setText(message);
                if(end == 1.0) {
                    if(start != 1.0){
                        levelText.setText((level+1) +"");
                        for(int i = dingrandomizer[current%30]/4 + 6; i > 0; --i)
                            animateDingStar();
                    }
                    barClip.setWidth(0);
                }
                animated = false;
            });
            timeline.getKeyFrames().add(kf1);
            timeline.getKeyFrames().add(kf2);
            timeline.play();
        }

        public void setText(String toset){
            levelText.setText(toset);
        }
    }

    private class expText extends StackPane{
        private Text messagetext;

        public expText(){
            setAlignment(Pos.TOP_LEFT);
            setMaxSize(600, 150);
            Rectangle background = new Rectangle(600, 150);
            background.setFill(Color.GRAY);
            messagetext = new Text();
            messagetext.setTranslateY(20);
            messagetext.setWrappingWidth(600);
            messagetext.setTextAlignment(TextAlignment.CENTER);
            messagetext.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));
            getChildren().addAll(background, messagetext);
        }

        public void setText(String toset){
            if(!toset.matches(""))
                displayingmessage = true;
            messagetext.setText(toset);
        }
    }


    private static class gainEventQueueNode{
        private String Message;
        private float Start;
        private float End;
        private expBar toSend;
        private int Level;
        private int jobLevel;
        private gainEventQueueNode Next;
        private static gainEventQueueNode head;
        private static gainEventQueueNode tail;

        public gainEventQueueNode(String message, float start, float end, boolean isBaseExp, int level, int joblevel){
            //System.out.println("initializing experience event node");
            if(head == null) {
                head = this;
                tail = this;
            }
            else {
                tail.Next = this;
                tail = this;
            }

            Message = message;
            Start = start;
            End = end;
            Level = level;
            if(isBaseExp) {
                toSend = expBar;
                jobLevel = joblevel;
            }
            else
                toSend = jexpBar;
        }

        public static boolean RecursivePop(){
            /*
            int size = 0;
            gainEventQueueNode current = head;
            while(current != null){
                ++size;
                current = current.Next;
            }
            System.out.println(size + " experience events queued.");*/
            if(head != null){
                if(head.toSend == expBar)
                    jexpBar.setText(head.jobLevel + "");
                head.toSend.animateExpGain(head.Start, head.End, head.Message, head.Level);
                head = head.Next;
                return false;
            }
            return true;
        }
    }

}
