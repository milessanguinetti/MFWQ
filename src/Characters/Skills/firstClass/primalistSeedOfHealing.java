package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.delayedDamage;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistSeedOfHealing extends Skill{
    public primalistSeedOfHealing(){
        super("Seed of Healing",
                "Plants a seed of healing near the target that blooms two turns later, greatly healing them", 20);
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
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 20; //doesn't have SP for this spell
    }

    @Override //deal two times dex + weapon damage after 3 turns counting the one this is casted on
    //property matches the caster's weapon property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new delayedDamage(-4 * Caster.getTempInt(),
                3, "Seed of Healing", Caster.getWeaponProperty(true)));
    }
}
