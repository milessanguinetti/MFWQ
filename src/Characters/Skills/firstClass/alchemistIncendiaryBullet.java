package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Burning;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//Essentially performs a basic weapon attack with a gun (scales with dex), but also
//burns them for 5 turns based on the caster's int.
public class alchemistIncendiaryBullet extends Skill{
    public alchemistIncendiaryBullet(){
        super("Incendiary Bullet",
                "Shoot the target with an incendiary bullet that sets it aflame.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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
        if(toCheck.getSP() <= 15)
            return false; //SP requirements
        if(!toCheck.hasWeaponType("Gun", false))
            return false; //skill can only be used with a gun.
        return true; //otherwise, the skill can be used
    }

    @Override //deal dex + weapon damage damage. target burns for int damage for 5 turns
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(Caster.getWeaponDamage(false) +
                Caster.getTempDex()), Caster.getWeaponProperty(false));
        Defender.addStatus(new Burning(Caster.getTempInt(), 5));
    }
}
