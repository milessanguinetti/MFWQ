package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.fthBuff;
import Characters.statusEffects.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class crusaderDeusVult extends Skill{
    public crusaderDeusVult(){
        super("Deus Vult",
                "Utter a prayer to God, blessing the caster with increased strength and faith.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 50;
    }

    @Override //increase strength and faith by 50% each for 10 turns after it is cast
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new strBuff(11, 1.5f));
        Caster.addStatus(new fthBuff(11, 1.5f));
    }
}
