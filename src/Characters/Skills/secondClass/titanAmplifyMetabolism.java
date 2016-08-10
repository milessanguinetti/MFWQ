package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.healOverTime;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class titanAmplifyMetabolism extends Skill{
    public titanAmplifyMetabolism(){
        super("Amplify Metabolism",
                "Amplifies the user's metabolism, healing nearly half health over 3 turns.", 15);
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
        return toCheck.getSP() >= 15;
    }

    @Override //heals the caster for 45% health over 3 turns, counting the one it is cast on.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new healOverTime(Math.round(.15f * Caster.getHPCap()), 3,
                "'s wounds begin to close."));
    }
}
