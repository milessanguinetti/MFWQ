package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class idolatorThunderHammer extends Skill{
    int AoE = 0;

    public idolatorThunderHammer(){
        super("Thunder Hammer",
                "Slams the target with a pagan god's power, hitting multiple targets with blunt weapons "
                + "and dealing bonus damage to aquatic foes.", 45);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(45);
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
        if(toCheck.hasWeaponType("1h Blunt", true) || toCheck.hasWeaponType("2h Blunt", true))
            AoE = 1; //set aoe to one with a blunt weapon.
        else
            AoE = 0;
        return toCheck.getSP() >= 45;
    }

    @Override //deals (str + fth + weapon damage) of unholy property, heals for same amount.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Multiplier = 1;
        if (Defender.hasProperty("Water"))
            Multiplier = 2;
        Defender.takeDamage(Caster.getTempStr()
                + Caster.getTempFth() + Caster.getWeaponDamage(true), "Unholy");
    }
}
