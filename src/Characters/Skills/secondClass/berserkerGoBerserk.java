package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.armorBuff;
import Characters.statusEffects.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class berserkerGoBerserk extends Skill{
    public berserkerGoBerserk(){
        super("Go Berserk",
                "The caster enrages, dramatically increasing strength at the cost of most of their armor.", 10);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        return toCheck.getSP() >= 10;
    }

    @Override //increase strength by 100% each for 10 turns after it is cast
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new strBuff(11, 2f));
        Caster.addStatus(new armorBuff(11, .25f));
    }
}
