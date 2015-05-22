package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class crusaderHolyBlade extends Skill{
    public crusaderHolyBlade(){
        super("Holy Blade", "Strikes the target with a weapon infused with righteous might.", 35);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(35);
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
        if(toCheck.hasWeaponType("Bow", true)) //can only be used with right handed melee weapons
            return false;
        if(toCheck.hasWeaponType("2h Staff", true))
            return false;
        return toCheck.getSP() >= 35;
    }

    @Override //deals 1.5 * (str + fth + weapon damage) of holy property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(1.5f *(Caster.getTempStr()
                + Caster.getTempFth() + Caster.getWeaponDamage(true))), "Holy");
    }
}
