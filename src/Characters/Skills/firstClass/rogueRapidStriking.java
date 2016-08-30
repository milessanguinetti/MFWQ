package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Battle;
import Profile.Game;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//physical attack based on speed and strength. Hits speed/10 + 1 times for 40% of (weapon damage + str)
public class rogueRapidStriking extends Skill {
    public rogueRapidStriking(){
        super("Rapid Strike",
                "Attacks the target repeatedly with an equipped weapon, striking more times if the user is faster.", 40);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(40);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 40;
    }

    @Override //hit more times based on speed
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Strikes = (Caster.getTempSpd() + 10) / 10; //strike once per ten speed
        Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " struck " + Strikes + " times!");
        for (int i = Strikes; i > 0; --i) {
            Defender.takeDamage(Math.round(.4f * (Caster.getTempStr() +
                    Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
        }
    }

    @Override
    //build an animation specific to this skill. What's presently below is a placeholder.
    protected Timeline buildSpecificAnimation(Pane toAnimateOn, gameCharacter Attacker, gameCharacter Defender){
        double returntox = Attacker.getTranslateX();
        double returntoy = Attacker.getTranslateY();
        double originalFacing = Attacker.getScaleX();
        int Strikes = (Attacker.getTempSpd() + 10) / 10; //the attack strikes once per ten speed

        Timeline timeline = new Timeline();

        double apex = Math.min(Attacker.getTranslateY(), Defender.getTranslateY()) - 100;
        double toMoveTo = Defender.getTranslateX() - 150;
        if(Defender.getTranslateX() < 0)
            toMoveTo = Math.round(Math.round(Defender.getTranslateX()) + 150);
        final KeyFrame kf0 = new KeyFrame(Duration.millis(400),
                    new KeyValue(Attacker.translateYProperty(), apex, new Interpolator() {
                        @Override
                        protected double curve(double t) {
                            return Math.sqrt(t);
                        }
                    }),
                    new KeyValue(Attacker.translateXProperty(), (Attacker.getTranslateX()+toMoveTo)/2),
                    new KeyValue(Attacker.opacityProperty(), 0));
        timeline.getKeyFrames().add(kf0);

        Attacker.setAttacking(400);
        for(int i = 0; i <= 2*Strikes-2; ++i){;
            final int verticalOffset = (i*125)%200;
            final boolean isLastStrike = i == 2*Strikes-2;
            if(i%2 == 0) {
                final KeyFrame strikeAnimation = new KeyFrame(Duration.millis(400+300*(i/2)),
                        new KeyValue(Attacker.translateXProperty(), Defender.getTranslateX() + 300 - 600 * ((i/2)% 2)),
                        new KeyValue(Attacker.translateYProperty(), Defender.getTranslateY() + verticalOffset));
                timeline.getKeyFrames().add(strikeAnimation);
            }
            else{
                final KeyFrame strikeAnimation = new KeyFrame(Duration.millis(410+300*(i/2)),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (!isLastStrike) {
                                    Attacker.setScaleX(-1 * Attacker.getScaleX());
                                    Attacker.setAttacking(300);
                                    FadeTransition ft = new FadeTransition(Duration.millis(150), Attacker);
                                    ft.setFromValue(0);
                                    ft.setToValue(1);
                                    ft.setCycleCount(2);
                                    ft.setAutoReverse(true);
                                    ft.play();

                                    Defender.setGettingHit();
                                } else {
                                    Attacker.setOpacity(0);
                                }
                            }
                        },
                        new KeyValue(Attacker.translateYProperty(), Defender.getTranslateY() - verticalOffset));
                timeline.getKeyFrames().add(strikeAnimation);
            }
        }

            //TEST
        final KeyFrame kf2 = new KeyFrame(Duration.millis(450+300*(Strikes-1)), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Attacker.setScaleX(originalFacing);
                Attacker.updateAnimation();
                Defender.updateAnimation();
                Attacker.setWaiting();
                Attacker.setTranslateX(returntox);
                Attacker.setTranslateY(returntoy);
            }},
                new KeyValue(Attacker.opacityProperty(), 1, Interpolator.EASE_IN));
        timeline.getKeyFrames().add(kf2);

        return timeline;
    }
}
