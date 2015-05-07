package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistRejuvenatingBreeze extends Skill{
    public primalistRejuvenatingBreeze(){
        super("Rejuvenating Breeze",
                "Rejuvenates' several allies' SP with a calming breeze.", 10);
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
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() < 10)
            return false; //doesn't have SP for this
        return true;
    }

    @Override //regenerate the defender's mana equal to the caster's int.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.subtractSP(Caster.getTempInt() * -1);
    }
}
