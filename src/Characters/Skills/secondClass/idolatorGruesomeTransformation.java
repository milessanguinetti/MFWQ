package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.fthBuff;
import Characters.Status.spdBuff;
import Characters.Status.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class idolatorGruesomeTransformation extends Skill{
    public idolatorGruesomeTransformation(){
        super("Gruesome Transformation",
                "Invokes the power of dark gods to increase strength and faith in proportion to how " +
                        "much higher they are than intelligence and dexterity, but slows the user", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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

    @Override //increase strength by faith/int and faith by str/dex
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        float strengthBuff = Caster.getTempFth(); //essentially turn temp faith into a float
        float faithBuff = Caster.getTempStr(); //essentially turn temp strength into a float
        Caster.addStatus(new strBuff(11, strengthBuff/Caster.getTempInt()));
        Caster.addStatus(new fthBuff(11, faithBuff/Caster.getTempDex()));
        Caster.addStatus(new spdBuff(11, .75f));
    }
}
