package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class alchemistPotionMixing extends Skill {

    public alchemistPotionMixing(){
        super("Potion Mixing",
                "Hurriedly mixes potions that very in number and efficacy, but scale with intellect.", 15);
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
        Random Rand = new Random();
        return Rand.nextInt(1); //roll a value between 0 and 1, inclusive.
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 15)
            return true;
        return false;
    }

    @Override //heals the target for efficacy val * int
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int efficacyval = Rand.nextInt(2) + 1; //roll a value between 1 and 2, inclusive.
        Defender.takeAbsoluteDamage(Caster.getTempInt()*-efficacyval);
    }
}
