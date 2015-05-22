package Characters.Skills.secondClass;

import Characters.Properties.Unholy;
import Characters.Skills.Skill;
import Characters.Status.fthBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class idolatorConvert extends Skill{
    public idolatorConvert(){
        super("Convert",
                "Converts allies to the user's dark faith, changing their property to Unholy.", 60);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(60);
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 60;
    }

    @Override //buffs faith and changes property to unholy
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.setTempProperty(new Unholy());
        Defender.addStatus(new fthBuff(6, 1.25f));
    }
}
