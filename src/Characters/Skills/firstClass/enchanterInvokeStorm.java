package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeStorm extends Skill{
    public enchanterInvokeStorm(){
        super("Invoke Storm",
                "Electrocutes the target with a thunderbolt, dealing double damage on aqueous foes.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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

    @Override //2.2 * int damage (with staff mod) apply a
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1; //staff damage modifier
        int waterMod = 1; //deals double damage if the target is water property
        if(Caster.hasWeaponType("1h staff", false) || Caster.hasWeaponType("2h staff", false))
            staffMod = 1.5f; //magic with staves deals 150% damage.
        if(Defender.prop) //START HERE
        Defender.takeDamage(Math.round(Caster.getTempInt() * 2 * staffMod), "Neutral");
    }
}
