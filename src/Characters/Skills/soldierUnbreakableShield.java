package Characters.Skills;

import Characters.Status.unbreakableShield;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
//makes the character all but immune to damage for 10 turns
public class soldierUnbreakableShield extends Skill{
    public soldierUnbreakableShield(){
        super("Unbreakable Shield", "The caster guards themselves with all of their might.", 100);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(100);
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
        Defender.addStatus(new unbreakableShield());
    }
}
