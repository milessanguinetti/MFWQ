package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.healOverTime;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
public class inquisitorSoothingLight extends Skill {
    public inquisitorSoothingLight(){
        super("Soothing Light",
                "Bathes the target in soothing light to heal injuries over time.", 10);
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
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 10)
            return true;
        return false;
    }

    @Override //heals the entire party for .8 * faith
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new healOverTime(Caster.getTempFth(), 5));
    }
}
