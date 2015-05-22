package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class crusaderLayingOnofHands extends Skill{
    public crusaderLayingOnofHands(){
        super("Laying On of Hands",
                "Performs a sacred ritual that greatly heals the target, but uses half of the caster's SP.", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.multiplySP(.5f); //halve sp.
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
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //heal for half of remaining sp * Faith/20
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage((Caster.getTempFth()/-20) * (Caster.getSP()/2));
    }
}
