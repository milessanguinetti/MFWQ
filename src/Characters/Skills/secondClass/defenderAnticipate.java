package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.singleTargetCounter;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class defenderAnticipate extends Skill {
    public defenderAnticipate(){
        super("Anticipate",
                "Anticipates a single enemy's attacks, countering any that directly hit the user.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        return toCheck.getSP() >= 10;
    }

    @Override //for 3 turns after the spell is cast, counters for str + weapon damage
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.setCounter(new singleTargetCounter(4, Defender));
    }

}
