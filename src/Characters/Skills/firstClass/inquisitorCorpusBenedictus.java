package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.dexBuff;
import Characters.Status.strBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class inquisitorCorpusBenedictus extends Skill{
    public inquisitorCorpusBenedictus(){
        super("Corpus Benedictus",
                "Blesses the target's body, increasing strength and dexterity.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
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

    @Override //buff strength and dexterity by 25%
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new strBuff(5, 1.25f));
        Defender.addStatus(new dexBuff(5, 1.25f));
    }
}
