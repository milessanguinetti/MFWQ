package Characters.Skills.firstClass;

import Characters.Properties.Water;
import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeWater extends Skill{
    public enchanterInvokeWater(){
        super("Invoke Water",
                "Soaks the target with a torrent of water, altering their property.", 10);
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
        if((toCheck.getSP() >= 10))
            return true; //has sp for this skill
        return false;
    }

    @Override //.4 * int damage (with staff mod) and change property to water.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1;
        if(Caster.hasWeaponType("1h staff", false) || Caster.hasWeaponType("2h staff", false))
            staffMod = 1.5f; //magic with staves deals 150% damage.
        Defender.takeDamage(Math.round(Caster.getTempInt() * .4f * staffMod), "Water");
        Defender.setTempProperty(new Water()); //change property to water
    }
}
