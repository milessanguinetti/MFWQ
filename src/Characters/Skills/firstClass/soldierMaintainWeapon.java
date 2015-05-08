package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class soldierMaintainWeapon extends Skill {
    public soldierMaintainWeapon(){
        super("Maintain Weapon", "Sharpens the caster's weapon, increasing the damage of strength-based weapons.", 10);
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
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!(toCheck.getSP() >= 10))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //20% strength buff for 10 turns
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new strBuff(10, 1.4f));
        Caster.printName();
        System.out.println(" sharpened their weapon!");
    }
}
