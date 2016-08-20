package Characters.Skills;

import Characters.combatEffect;
import Characters.gameCharacter;
import Structures.Data;
import Structures.battleData;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Created by Miles Sanguinetti on 3/22/15.
 */
public abstract class Skill implements Data, combatEffect, Serializable {
    protected String skillName;
    private String Description;
    private int spCost;

    //default constructor
    public Skill(){}

    //special constructor
    public Skill(String passedName, String passedDescription, int passedCost){
        skillName = passedName;
        Description = passedDescription;
        spCost = passedCost;
    }

    public void performAnimation(Pane toAnimateOn, gameCharacter Attacker, gameCharacter Defender){
        Timeline Animation = buildSpecificAnimation(toAnimateOn, Attacker, Defender);
        Attacker.toFront();
        if(Animation != null) {
            battleData.setAnimating(true);
            Animation.setOnFinished(event -> {
                battleData.setAnimating(false);
            });
            Animation.play();
        }
    }

    //build an animation specific to this skill. What's presently below is a placeholder.
    protected Timeline buildSpecificAnimation(Pane toAnimateOn, gameCharacter Attacker, gameCharacter Defender){
        if(getAoE() != -1) {
            double returntox = Attacker.getTranslateX();
            double returntoy = Attacker.getTranslateY();
            Timeline timeline = new Timeline();
            Attacker.setAttacking(800);
            //attacking animation
            //TEST
            double toMoveTo = Defender.getTranslateX() - 150;
            if(Defender.getTranslateX() < 0)
                toMoveTo = Math.round(Math.round(Defender.getTranslateX()) + 150);
            double apex = Math.min(Attacker.getTranslateY(), Defender.getTranslateY()) - 100;

            final KeyFrame kf0 = new KeyFrame(Duration.millis(400),
                    new KeyValue(Attacker.translateYProperty(), apex, new Interpolator() {
                        @Override
                        protected double curve(double t) {
                            return Math.sqrt(t);
                        }
                    }),
                    new KeyValue(Attacker.translateXProperty(), (Attacker.getTranslateX()+toMoveTo)/2));
            timeline.getKeyFrames().add(kf0);
            //TEST
            final KeyValue kv1 = new KeyValue(Attacker.translateYProperty(), Defender.getTranslateY(),
                    new Interpolator() {
                        @Override
                        protected double curve(double t) {
                            return t*t*t*t;
                        }
                    }); //y property
            final KeyValue kv2 = new KeyValue(Attacker.translateXProperty(), toMoveTo); //x property
            final KeyFrame kf1 = new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Attacker.updateAnimation();
                    Defender.updateAnimation();
                }
            }, kv1, kv2);
            timeline.getKeyFrames().add(kf1);
            //returning animation
            final KeyValue kv4 = new KeyValue(Attacker.translateXProperty(), returntox, Interpolator.EASE_OUT); //x property
            final KeyValue kv5 = new KeyValue(Attacker.translateYProperty(), returntoy, Interpolator.EASE_OUT); //y property
            final KeyFrame kf2 = new KeyFrame(Duration.millis(1200), kv4, kv5);
            timeline.getKeyFrames().add(kf2);
            return timeline;
        }
        return null;
    }

    @Override
    public void Display() {
        System.out.println(skillName + ':');
        System.out.println(Description);
    }

    @Override
    public String getDescription(){
        return Description;
    }

    public int getSPCost(){
        return spCost;
    }

    @Override
    public void Display(int indent) {
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(skillName + ':');
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println(Description);
        for(int i = 0; i < indent; ++i){
            System.out.print("      ");
        }
        System.out.println("MP Cost: " + spCost);
    }

    @Override
    public void printName(){
        System.out.print(skillName);
    }

    @Override
    public String returnKey() {
        return skillName;
    }

    @Override
    public void setKey(String name) {
        skillName = name;
    }

    @Override
    public int compareTo(String toCompare) {
        return skillName.compareTo(toCompare);
    }

    @Override
    public int compareTo(Data toCompare) {
        return skillName.compareTo(toCompare.returnKey());
    }

    @Override
    public void writeOut(PrintWriter toWrite) {
        //TO BE IMPLEMENTED
    }
}
