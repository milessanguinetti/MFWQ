package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Burning;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class crusaderMartyrsBurningBlade extends Skill{
    public crusaderMartyrsBurningBlade(){
        super("Martyr's Burning Blade",
                "Sacrifices 1/5 of the user's maximum health to deal heavy fire damage to a single foe "
                + "and light them aflame.", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.takeAbsoluteDamage(Caster.getHPCap()/5);
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
        return toCheck.getSP() >= 0;
    }

    @Override //deals (str + fth + weapon damage) of unholy property, heals for same amount.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Caster.getTempStr()
                + Caster.getTempFth() + Caster.getWeaponDamage(true), "Fire");
        Defender.addStatus(new Burning(Caster.getTempFth(), 6));
    }
}
