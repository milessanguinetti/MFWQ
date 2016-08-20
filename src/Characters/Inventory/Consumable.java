package Characters.Inventory;

import Characters.combatEffect;
import Characters.gameCharacter;
import Structures.battleData;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Created by Miles Sanguinetti on 3/29/15.
 */
public abstract class Consumable extends Item implements combatEffect {
    public Consumable(){}

    public Consumable(String name, String description){
        super(name, description);
    }

    public void performAnimation(Pane toAnimateOn, gameCharacter Attacker, gameCharacter Defender){
        battleData.setAnimating(true);
        double returntox = Attacker.getTranslateX();
        double returntoy = Attacker.getTranslateY();
        Timeline timeline = new Timeline();
        Attacker.setAttacking(1000);
        ImageView icon = getIcon();
        int xoffset = -80;
        if(Attacker.isPlayerSide())
            xoffset = 80;
        icon.setTranslateX(Attacker.getTranslateX() + xoffset);
        icon.setTranslateY(Attacker.getTranslateY() - 80);
        toAnimateOn.getChildren().add(icon);
        //attacking animation
        final KeyValue kv1 = new KeyValue(icon.opacityProperty(), 0);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
        timeline.getKeyFrames().add(kf1);
        timeline.setOnFinished(event -> {
            battleData.setAnimating(false);
            Attacker.setWaiting();
            toAnimateOn.getChildren().remove(icon);
            icon.setOpacity(1);
        });
        timeline.play();
    }

    @Override
    public String getDescription(){
        return Description;
    }

    @Override
    public void spLoss(gameCharacter Caster) {
         //items unilaterally have no SP cost
    }

    @Override
    public void printName() {
        System.out.print(itemName);
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.isAlive();
    }

    @Override
    public StackPane buildSpecificItemDisplay() {
        return null; //consumables do not have a specific item display to build at this time.
    }
}
