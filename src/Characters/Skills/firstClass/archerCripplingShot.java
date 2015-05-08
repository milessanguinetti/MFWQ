package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.dexBuff;
import Characters.Status.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerCripplingShot extends Skill{
    public archerCripplingShot(){
        super("Crippling Shot",
                "Targets a foes limbs with an arrow, decreasing their strength and dexterity.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        if(!toCheck.hasWeaponType("Bow", true))
            return false; //crippling shot can only be used with bow
        return toCheck.getSP() >= 10;
    }

    @Override //deal 80% dex + weapon damage, debuff strength and dex
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(.8f * (Caster.getTempDex() +
                Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
        Defender.addStatus(new dexBuff(5, .8f));
        Defender.addStatus(new strBuff(5, .8f));
    }
}
