package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.delayedDamage;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerArcingArrow extends Skill{
    public archerArcingArrow(){
        super("Arcing Arrow",
                "Lobs a target high into the sky that hits for heavy damage two turns later.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
            return false;
        return toCheck.getSP() >= 20; //doesn't have SP for this spell
    }

    @Override //deal two times dex + weapon damage after 3 turns counting the one this is casted on
    //property matches the caster's weapon property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new delayedDamage(
                2 * (Caster.getWeaponDamage(true) + Caster.getTempDex()),
                3, "Arcing Arrow", Caster.getWeaponProperty(true)));
    }
}
