package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageAdditive;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class soldierDefend extends Skill{
    public soldierDefend(){
        super("Defend",
                "Assumes a defensive stance for a turn, reducing damage taken.", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //takes no SP to cast.
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
        return true;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new damageAdditive(
                Math.round(.75f * Caster.getWeaponDamage(true)), 2));
        //reduces damage taken by .75 for the turn this is casted and the next
    }

}
