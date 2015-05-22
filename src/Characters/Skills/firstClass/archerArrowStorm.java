package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerArrowStorm extends Skill{
    public archerArrowStorm(){
        super("Arrow Storm",
                "Rapidly shoots a barrage of arrows at the entire enemy party. Liable to miss.", 30);
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!toCheck.hasWeaponType("Bow", true))
            return false; //requires a bow.
        return toCheck.getSP() >= 30;
    }

    @Override //deal 60% damage calculated by dex and bow weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        if (!(Rand.nextInt(4) == 0)) { //25% of the time, the skill misses.
            Defender.takeDamage(Math.round((.6f) * (Caster.getTempDex() +
                    (Caster).getWeaponDamage(true))), Caster.getWeaponProperty(true));
            //deal 20% weapon/str damage of organic type
        }
    }
}
