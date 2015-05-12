package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class berserkerIronHurricane extends Skill{
    public berserkerIronHurricane(){
        super("Iron Hurricane",
                "Assaults several foes with a storm of slashes. Does more damage the less health the user has.", 35);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(35);
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.hasWeaponType("Bow", true)) //can only be used with right handed melee weapons
            return false;
        if(toCheck.hasWeaponType("2h Staff", true))
            return false;
        return toCheck.getSP() >= 35;
    }

    @Override //deal 150% - 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float multiplier = Caster.getHP();
        multiplier /= Caster.getHPCap();
        multiplier = (2f - multiplier);
        Defender.takeDamage(Math.round(multiplier *(Caster.getTempStr() + Caster.getWeaponDamage(true))),
                Caster.getWeaponProperty(true));
    }
}
