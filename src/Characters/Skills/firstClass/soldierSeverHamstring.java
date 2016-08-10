package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.spdBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class soldierSeverHamstring extends Skill{
    public soldierSeverHamstring(){
        super("Sever Hamstring",
                "Severs the target's hamstring with an edged weapon, dramatically limiting their speed.", 10);
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
        if(toCheck.getSP() < 10)
            return false; //doesn't have SP for this
        if(!toCheck.hasWeaponType("2h Edged", true) && !toCheck.hasWeaponType("1h Edged", true))
            return false; //hamstring can only be used with bladed weapons.
        return true;
    }

    @Override //deal str + right weapon damage and halves speed
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempStr() +
                Caster.getWeaponDamage(true)), Caster.getWeaponProperty(true));
        Defender.addStatus(new spdBuff(5, .5f));
    }
}
