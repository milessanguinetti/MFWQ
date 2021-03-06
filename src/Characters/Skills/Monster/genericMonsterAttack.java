package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class genericMonsterAttack extends Skill {
    public genericMonsterAttack(){
        super("its sharp claws", "", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        return; //generic attacks cause no SP loss.
    }

    @Override
    public boolean notUsableOnDead() {
        return true;
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
        return true;
    }

    @Override //a generic attack based on the caster's highest offensive stat
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        //first off, we determine the caster's highest offensive stat value
        int largestvalue = Caster.getTempStr();
        if(largestvalue < Caster.getTempDex())
            largestvalue = Caster.getTempDex();
        if(largestvalue < Caster.getTempInt())
            largestvalue = Caster.getTempInt();
        if(largestvalue < Caster.getTempFth())
            largestvalue = Caster.getTempFth();
        Defender.takeDamage(largestvalue, "Neutral");
        //we deal damage based on the largest value of neutral property
    }
}
