package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeStorm extends Skill{
    public enchanterInvokeStorm(){
        super("Invoke Storm",
                "Electrocutes the target with a thunderbolt, dealing double damage on aqueous foes.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        if((toCheck.getSP() >= 15))
            return true; //has sp for this skill
        return false;
    }

    @Override //2 * int damage (with staff mod); double if target is water property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1; //staff damage modifier
        int waterMod = 1; //deals double damage if the target is water property
        if(Caster.hasWeaponType("1h staff", false) || Caster.hasWeaponType("2h staff", false))
            staffMod = 1.5f; //magic with staves deals 150% damage.
        if(Defender.hasProperty("Water"))
            waterMod = 2; //lightning does 2x damage v.s. water property
        Defender.takeDamage(Math.round(Caster.getTempInt() * 2 * staffMod * waterMod), "Neutral");
    }
}
