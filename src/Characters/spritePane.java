package Characters;

        import javafx.animation.*;
        import javafx.geometry.Pos;
        import javafx.geometry.Rectangle2D;
        import javafx.scene.effect.ColorAdjust;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.StackPane;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import javafx.scene.text.FontWeight;
        import javafx.scene.text.Text;
        import javafx.util.Duration;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.Serializable;
        import java.nio.file.Files;
        import java.nio.file.Paths;

/**
 * Created by Miles Sanguinetti on 7/29/2015.
 */
public class spritePane extends StackPane implements Serializable{
    protected String Name; //character name; stored here for access to spritesheet images.
    private transient characterSpriteAnimation charAnimation = null;
    private transient ColorAdjust colorAdjust;
    private int numAnimations = 0;

    public spritePane(){
        setAlignment(Pos.CENTER); //center any added children.
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/sprites/testsprites.png"))){
            ImageView thisSprite = new ImageView(new Image(imginput));
            thisSprite.setViewport(new Rectangle2D(0, 0, 96, 96)); //initalize viewport to starting parameters
            charAnimation = new characterSpriteAnimation(thisSprite); //animate sprite
            colorAdjust = new ColorAdjust();
            thisSprite.setEffect(colorAdjust);
            getChildren().add(thisSprite); //add sprite to the stackpane.
        }

        catch (IOException e){
            System.out.println("Error loading " + Name + "'s sprite.");
        }
    }

    public void rebuildTransientValues(){
        setAlignment(Pos.CENTER); //center any added children.
        try(InputStream imginput = Files.newInputStream(Paths.get("resources/sprites/testsprites.png"))){
            ImageView thisSprite = new ImageView(new Image(imginput));
            thisSprite.setViewport(new Rectangle2D(0, 0, 96, 96)); //initalize viewport to starting parameters
            charAnimation = new characterSpriteAnimation(thisSprite); //animate sprite
            colorAdjust = new ColorAdjust();
            thisSprite.setEffect(colorAdjust);
            getChildren().add(thisSprite); //add sprite to the stackpane.
        }

        catch (IOException e){
            System.out.println("Error loading " + Name + "'s sprite.");
        }
    }

    public void animateDamage(int toTake){
        //getChildren().remove(damageText);
        Text damageText = new Text();
        damageText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 40));
        damageText.setOpacity(1);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), new KeyValue(damageText.opacityProperty(), 1)));
        final KeyValue kv1 = new KeyValue(damageText.translateYProperty(), -100, Interpolator.EASE_OUT); //y property
        final KeyValue kv2 = new KeyValue(damageText.translateXProperty(), 20*getScaleX(), Interpolator.EASE_IN); //x property
        final KeyValue kv3 = new KeyValue(damageText.opacityProperty(), 0, Interpolator.EASE_IN); //opacity
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
        timeline.getKeyFrames().add(kf);
        damageText.setOpacity(0);
        if(toTake < 0){ //healing case
            damageText.setText("" + toTake*-1);
            damageText.setFill(Color.GREEN);
        }
        else{ //damage case
            damageText.setText("" + toTake);
            damageText.setFill(Color.RED);
        }
        getChildren().add(damageText);
        timeline.setDelay(Duration.millis(numAnimations*300));
        timeline.setOnFinished(event -> {
            getChildren().remove(damageText);
            --numAnimations;
        });
        timeline.play();
        ++numAnimations;
    }

    public void animateStatusCondition(String Name, boolean isBeneficial){
        //getChildren().remove(damageText);
        Text damageText = new Text(Name);
        damageText.setFont(Font.font("Tw Cen MT Condensed", FontWeight.EXTRA_BOLD, 40));
        damageText.setOpacity(1);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(0), new KeyValue(damageText.opacityProperty(), 1)));
        final KeyValue kv1 = new KeyValue(damageText.translateYProperty(), -100, Interpolator.EASE_OUT); //y property
        final KeyValue kv2 = new KeyValue(damageText.translateXProperty(), 20*getScaleX(), Interpolator.EASE_IN); //x property
        final KeyValue kv3 = new KeyValue(damageText.opacityProperty(), 0, Interpolator.EASE_IN); //opacity
        final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2, kv3);
        timeline.getKeyFrames().add(kf);
        damageText.setOpacity(0);
        if(isBeneficial){ //beneficial case
            damageText.setFill(Color.GREEN);
        }
        else{ //negative case
            damageText.setFill(Color.RED);
        }
        getChildren().add(damageText);
        timeline.setDelay(Duration.millis(numAnimations*300));
        timeline.setOnFinished(event -> {
            getChildren().remove(damageText);
            --numAnimations;
        });
        timeline.play();
        ++numAnimations;
    }

    public void setWaiting(){
        charAnimation.setGraphicMode(0);
    }

    public void setGettingHit(){
        charAnimation.setGraphicMode(1);
    }

    public void setAttacking(int duration){
        charAnimation.setGraphicMode(2, duration);
    }

    public void setDead(){
        charAnimation.setGraphicMode(3);
    }

    private static class characterSpriteAnimation extends Transition {

        private final ImageView imageView;
        private int count = 6; //either 6 in the case of anything other than death or 1 in the case of death.
        private int Mode; //0 = waiting; 1 = getting hit; 2 = attacking; 3 = dead.
        private int lastIndex; //the previous index visited

        public characterSpriteAnimation(ImageView imageView) {
            this.imageView = imageView;
            setCycleDuration(javafx.util.Duration.millis(800));
            setInterpolator(Interpolator.LINEAR);
        }

        @Override
        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                if(this.imageView.getScaleX() == -1)
                    imageView.setViewport(new Rectangle2D(480 - index * 96, Mode * 96, 96, 96));
                else
                    imageView.setViewport(new Rectangle2D(index * 96, Mode * 96, 96, 96));
                lastIndex = index;
            }
        }

        private void setGraphicMode(int toSet){
            lastIndex = -1; //ensure that we aren't going to skip a frame after switching.
            if(toSet != 3){
                count = 6; //standard number of frames for a given animation.
                if(toSet == 0) {
                    stop();
                    setCycleCount(Animation.INDEFINITE); //waiting repeats infinitely
                    setCycleDuration(javafx.util.Duration.millis(800)); //over 800 ms
                    play();
                }
                else{
                    stop();
                    setCycleCount(1); //attack and take damage repeat once
                    setCycleDuration(javafx.util.Duration.millis(1400)); //over 1400ms
                    play();
                }
            }
            else
                count = 1; //death only has one frame.
            Mode = toSet; //and set mode to whatever was passed in.
        }

        private void setGraphicMode(int toSet, int duration){
            lastIndex = -1; //ensure that we aren't going to skip a frame after switching.
            if(toSet != 3){
                count = 6; //standard number of frames for a given animation.
                if(toSet == 0) {
                    setCycleDuration(javafx.util.Duration.millis(duration)); //use the passed duration.
                    setCycleCount(Animation.INDEFINITE); //waiting repeats infinitely
                }
                else{
                    stop();
                    setCycleDuration(javafx.util.Duration.millis(duration)); //use the passed duration.
                    setCycleCount(1); //attack and take damage repeat once
                    play();
                }
            }
            else
                count = 1; //death only has one frame.
            Mode = toSet; //and set mode to whatever was passed in.
        }

        public ImageView getSprite(){
            return imageView;
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
    public void initializeAnimation(){
        if(charAnimation == null){ //if we're loading a save and consequently missing our transient vars
            colorAdjust = new ColorAdjust();
            setAlignment(Pos.CENTER); //center any added children.
            try(InputStream imginput = Files.newInputStream(Paths.get("resources/sprites/testsprites.png"))){
                ImageView thisSprite = new ImageView(new Image(imginput));
                thisSprite.setEffect(colorAdjust);
                thisSprite.setViewport(new Rectangle2D(0, 0, 96, 96)); //initalize viewport to starting parameters
                charAnimation = new characterSpriteAnimation(thisSprite); //animate sprite
                getChildren().add(thisSprite); //add sprite to the stackpane.
            }

            catch (IOException e){
                System.out.println("Error loading " + Name + "'s sprite.");
            }
        }
    }

    public void setPlain(){
        colorAdjust.setBrightness(0);
    }

    public void setTargeted(){
        colorAdjust.setBrightness(-.4);
    }
    public void setTurn(){
        colorAdjust.setBrightness(.4);
    }

    public ImageView getSprite(){
        return charAnimation.getSprite();
    }
}
