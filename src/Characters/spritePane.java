package Characters;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Miles on 7/29/2015.
 */
public class spritePane extends StackPane implements Serializable{
    protected String Name; //character name; stored here for access to spritesheet images.
    private transient characterSpriteAnimation charAnimation = null;

    public spritePane(){
        setAlignment(Pos.CENTER); //center any added children.
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/sprites/testsprites.png"))){
            ImageView thisSprite = new ImageView(new Image(imginput));
            thisSprite.setViewport(new Rectangle2D(0, 0, 96, 96)); //initalize viewport to starting parameters
            charAnimation = new characterSpriteAnimation(thisSprite); //animate sprite
            getChildren().add(thisSprite); //add sprite to the stackpane.
        }

        catch (IOException e){
            System.out.println("Error loading " + Name + "'s sprite.");
        }
    }

    protected void setWaiting(){
        charAnimation.setGraphicMode(0);
    }

    protected void setGettingHit(){
        charAnimation.setGraphicMode(1);
    }

    protected void setAttacking(){
        charAnimation.setGraphicMode(2);
    }

    protected void setDead(){
        charAnimation.setGraphicMode(3);
    }

    private static class characterSpriteAnimation extends Transition {

        private final ImageView imageView;
        private int count = 6; //either 6 in the case of anything other than death or 1 in the case of death.
        private int Mode; //0 = waiting; 1 = getting hit; 2 = attacking; 3 = dead.
        private int lastIndex; //the previous index visited
        private boolean isFlipped = false; //a boolean value to track whether or not an image needs to be flipped

        public characterSpriteAnimation(ImageView imageView) {
            this.imageView = imageView;
            setCycleDuration(javafx.util.Duration.millis(800));
            setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                if(isFlipped)
                    imageView.setViewport(new Rectangle2D(480 - index * 96, Mode * 96, 96, 96));
                else
                    imageView.setViewport(new Rectangle2D(index * 96, Mode * 96, 96, 96));
                lastIndex = index;
            }
        }

        public void setGraphicMode(int toSet){
            if(toSet != 3){
                if(toSet == 0) {
                    //setCycleCount(Animation.INDEFINITE); //waiting repeats infinitely
                    setCycleDuration(javafx.util.Duration.millis(800)); //over 800 ms
                }
                else{
                    //setCycleCount(1); //attack and take damage repeat once
                    setCycleDuration(javafx.util.Duration.millis(1400)); //over 1400ms
                }
                count = 6; //standard number of frames for a given animation.
            }
            else
                count = 1; //death only has one frame.
            Mode = toSet; //and set mode to whatever was passed in.
            setCycleCount(Animation.INDEFINITE); //PLACEHOLDER
        }

        protected void Flip(boolean toFlip){
            isFlipped = toFlip;
            if(isFlipped)
                imageView.setScaleX(-1);
            else
                imageView.setScaleX(1);
        }
    }

    //if true, plays a sprite animation in its default waiting stance; otherwise stops the animation
    public void Animate(boolean AnimationVar){
        if(AnimationVar) {
            charAnimation.setGraphicMode(0); //set to waiting
            charAnimation.play();
        }
        else
            charAnimation.stop();
    }

    //wrapper function that passes a boolean to char animation for sprite flipping purposes
    public void Flip(boolean toFlip){
        if(charAnimation == null){
            setAlignment(Pos.CENTER); //center any added children.
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/sprites/testsprites.png"))){
                ImageView thisSprite = new ImageView(new Image(imginput));
                thisSprite.setViewport(new Rectangle2D(0, 0, 96, 96)); //initalize viewport to starting parameters
                charAnimation = new characterSpriteAnimation(thisSprite); //animate sprite
                getChildren().add(thisSprite); //add sprite to the stackpane.
            }

            catch (IOException e){
                System.out.println("Error loading " + Name + "'s sprite.");
            }
        }
        charAnimation.Flip(toFlip);
    }
}
