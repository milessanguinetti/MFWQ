package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.damageMultiplier;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
//makes the character all but immune to damage for 10 turns
public class defenderUnbreakableShield extends Skill {
    public defenderUnbreakableShield(){
        super("Unbreakable Shield", "The caster assumes a defensive stance to guard themself with their shield.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        if(!toCheck.hasWeaponType("Shield", false)) //can only be used with a shield
            return false;
        return toCheck.getSP() >= 30;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new damageMultiplier(.5f, 5));
    }
}
