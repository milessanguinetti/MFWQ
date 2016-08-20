package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.statusEffects.speedCounter;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class assassinPoise extends Skill {
    public assassinPoise(){
        super("Poise",
                "Adopts a ready stance to completely evade and counter the next attack, provided that the attacker is slower.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 50;
    }

    @Override //temporarily replaces the defender's counter spell with one that evades and counters if the user's
    //speed is higher than that of their attacker. Deals more damage the greater the difference is.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.setCounter(new speedCounter(Caster.getCounter()));
    }
}
