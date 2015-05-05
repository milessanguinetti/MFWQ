package Characters.Skills;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
public class inquisitorSoothingLight extends Skill{
    public inquisitorSoothingLight(){
        super("Soothing Light",
                "Bathes the target in soothing light to heal injuries over time.", 10);
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
        Defender.takeAbsoluteDamage(Math.round(Caster.getTempFth()*-.8f));

    }
}
