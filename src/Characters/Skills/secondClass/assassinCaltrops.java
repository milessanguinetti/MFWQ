package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.statusEffects.spdBuff;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class assassinCaltrops extends Skill{
    public assassinCaltrops(){
        super("Caltrops",
                "Throws out a handful of caltrops, slowing and damaging the caster's foes.", 10);
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 10;
    }

    @Override //damages and slows enemies
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Caster.getWeaponDamage(true)/4, "Neutral");
        Defender.addStatus(new spdBuff(7, .5f));
    }
}
