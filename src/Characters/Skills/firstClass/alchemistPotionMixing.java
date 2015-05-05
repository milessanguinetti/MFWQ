package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class alchemistPotionMixing extends Skill {
    int aoeVal; //whether the potion is single target or multitarget based on our roll
    int efficacyval; //whether the potion heals for 1 or 2 times intellect

    public alchemistPotionMixing(){
        super("Potion Mixing",
                "Hurriedly mixes potions that very in number and efficacy, but scale with intellect.", 15);
        Random Rand = new Random();
        int Roll = Rand.nextInt(4); //roll a value between 0 and 3, inclusive.
        efficacyval = (Roll % 2) + 1; //efficacy is either 1 or 2.
        aoeVal = Roll/2; //aoeval is either 0 or 1.
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return aoeVal;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 15)
            return true;
        return false;
    }

    @Override //heals the target for efficacy val * int
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(Caster.getTempInt()*-efficacyval);
    }
}
