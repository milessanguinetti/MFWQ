package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.neutralEvade;
import Characters.statusEffects.spdBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class rogueQuickenReflexes extends Skill {
    public rogueQuickenReflexes(){
        super("Quicken Reflexes",
                "The caster intensely focuses, increasing their speed and chance to dodge.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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
        if(toCheck.getSP() < 15)
            return false;
        return true;
    }

    @Override //20% chance to dodge and 150% speed buff for 10 turns.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new neutralEvade(20, 10));
        Defender.addStatus(new spdBuff(10, 1.5f));
    }
}
