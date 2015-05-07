package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageMultiplier;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguineti on 5/7/15.
 */
public class inquisitorDecretum extends Skill{
    public inquisitorDecretum(){
        super("Decretum",
                "Burdens the target with a holy judgment, increasing damage taken for one turn.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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
        if(toCheck.getSP() < 15)
            return false; //doesn't have SP for this
        return true;
    }

    @Override //make the target take 150% damage for a turn (after the turn it is cast).
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new damageMultiplier(1.5f, 2));
    }
}
