package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.statusEffects.armorBuff;
import Characters.statusEffects.dexBuff;
import Characters.statusEffects.spdBuff;
import Characters.statusEffects.strBuff;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class assassinKillingIntent extends Skill{
    public assassinKillingIntent(){
        super("Killing Intent",
                "Dark magic that overcomes the caster with a desire to kill, dramatically increasing speed and dexterity for one turn.", 30);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 30;
    }

    @Override //increases speed 5 fold for one turn after it is cast.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new dexBuff(1, 2f));
        Caster.addStatus(new spdBuff(1, 5f));
    }
}
