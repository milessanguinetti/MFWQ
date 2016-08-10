package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.leechingDoT;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class lichSoulDevour extends Skill{
    public lichSoulDevour(){
        super("Soul Devour",
                "Consumes the spirits of several enemies, dealing damage both instantly and over time and healing."
                + " the lich.", 45);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(45);
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
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 45;
    }

    @Override //deals damage equal to twice the caster's int, leeches int * staff multiplier over 3 turns.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float staffMultiplier = 1;
        if(Caster.hasWeaponType("1h Staff", false) || Caster.hasWeaponType("2h Staff", false))
            staffMultiplier = 2;
        Defender.takeDamage(2 * Caster.getTempInt(), "Unholy");
        Defender.addStatus(new leechingDoT(Math.round(staffMultiplier * Caster.getTempInt()),
                3, Caster));
    }
}
