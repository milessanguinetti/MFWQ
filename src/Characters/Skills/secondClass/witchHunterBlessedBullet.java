package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class witchHunterBlessedBullet extends Skill{
    public witchHunterBlessedBullet(){
        super("Blessed Bullet",
                "Blasts several foes with a blessed bullet unleashes an explosion of holy light.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        if(!toCheck.hasWeaponType("Gun", false)) //can only be used with a shield
            return false;
        return toCheck.getSP() >= 30;
    }

    @Override //deal 1.5 * (dex + gun) damage of holy property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(1.5f *(Defender.getTempDex() + Defender.getWeaponDamage(false))),
                "Holy");
    }
}
