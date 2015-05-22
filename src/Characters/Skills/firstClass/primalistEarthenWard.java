package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageAdditive;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistEarthenWard extends Skill{
    public primalistEarthenWard(){
        super("Earthen Ward",
                "Shields the target with a ward of earth, wood and stone. Reduces damage taken.", 15);
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
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 15;
    }

    @Override //reduce damage taken for 5 turns after it is cast based on int
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new damageAdditive(
                Math.round(.3f * Caster.getTempInt()), 6));
        //defender takes 1/3 of the caster's int less damage per attack
    }
}
