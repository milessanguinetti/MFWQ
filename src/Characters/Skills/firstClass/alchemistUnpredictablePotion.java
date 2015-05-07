package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.*;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class alchemistUnpredictablePotion extends Skill{
    public alchemistUnpredictablePotion(){
        super("Unpredictable Potion",
                "Gives the target an unpredictable potion, randomly boosting offensive stats.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if((toCheck.getSP() >= 10))
            return true; //has sp for this skill
        return false;
    }

    @Override //.4 * int damage (with staff mod) and change property to water.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(8);
        if(Roll == 1 || Roll == 3) {
            Defender.addStatus(new strBuff(5, 1.5f));
            Defender.addStatus(new intBuff(5, 1.5f));
        }
        else if(Roll == 2 || Roll == 4){
            Defender.addStatus(new dexBuff(5, 1.5f));
            Defender.addStatus(new fthBuff(5, 1.5f));
        }
        else if(Roll == 5){
            Defender.addStatus(new strBuff(5, 1.5f));
            Defender.addStatus(new fthBuff(5, 1.5f));
        }
        else if(Roll == 6){
            Defender.addStatus(new intBuff(5, 1.5f));
            Defender.addStatus(new dexBuff(5, 1.5f));
        }
        else if(Roll == 7){ //you're really lucky; all stats are doubled.
            Defender.addStatus(new intBuff(5, 2f));
            Defender.addStatus(new fthBuff(5, 2f));
            Defender.addStatus(new strBuff(5, 2f));
            Defender.addStatus(new dexBuff(5, 2f));
            Defender.addStatus(new spdBuff(5, 2f));
        }
        else{ //roll == 0 //you're really unlucky and all of your stats are halved.
            Defender.addStatus(new intBuff(5, .5f));
            Defender.addStatus(new fthBuff(5, .5f));
            Defender.addStatus(new strBuff(5, .5f));
            Defender.addStatus(new dexBuff(5, .5f));
            Defender.addStatus(new spdBuff(5, .5f));
        }

    }
}
