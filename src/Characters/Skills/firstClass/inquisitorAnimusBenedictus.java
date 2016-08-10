package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.fthBuff;
import Characters.statusEffects.intBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class inquisitorAnimusBenedictus extends Skill{
    public inquisitorAnimusBenedictus(){
        super("Animus Benedictus",
                "Blesses the target's spirit, increasing faith and intelligence.", 15);
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
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 15;
    }

    @Override //buff faith and int by 25%
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new fthBuff(5, 1.25f));
        Defender.addStatus(new intBuff(5, 1.25f));
    }
}
