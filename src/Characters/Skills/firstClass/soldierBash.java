package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class soldierBash extends Skill {
    public soldierBash(){
        super("Bash",
                "Bashes the target with an equipped weapon. Hits harder with blunt weapons.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(5);
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
        return toCheck.getSP() >= 5;
    }

    @Override //deal 150% - 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Caster.hasWeaponType("2h Blunt", true) || Caster.hasWeaponType("1h Blunt", true))
            Defender.takeDamage(Math.round(1.5f *(Caster.getTempStr() + Caster.getWeaponDamage(true))),
                    Caster.getWeaponProperty(true));
        else //does less damage with weapons that aren't blunt.
            Defender.takeDamage(Math.round(1.25f*(Caster.getTempStr() +
                    Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
    }
}
