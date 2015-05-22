package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeStone extends Skill{

    public enchanterInvokeStone(){
        super("Invoke Stone",
                "Invokes the power of an earthquake; hits a random number of enemies.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        Random Rand = new Random();
        return Rand.nextInt(3) + 1; //roll a value between 0 and 1, inclusive.
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 20)
            return true;
        return false;
    }

    @Override //deals damage equal to the caster's int of organic property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Caster.getTempInt(), "Organic");
    }
}
