package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.spRegen;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class lichSoulDrain extends Skill{
    public lichSoulDrain(){
        super("Soul Drain",
                "Drains away an enemy's soul, restoring the caster's SP over 3 turns.", 20);
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
        if((toCheck.getSP() >= 20))
            return true; //has sp for this skill
        return false;
    }

    @Override //3 * int damage (with staff mod) apply a SP regen
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMod = 1;
        if(Caster.hasWeaponType("1h staff", false) || Caster.hasWeaponType("2h staff", false))
            staffMod = 1.5f; //magic with staves deals 150% damage.
        Defender.takeDamage(Math.round(Caster.getTempInt() * 3 * staffMod), "Undead");
        Caster.addStatus(new spRegen(3));
    }
}
