package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistCallStorm extends Skill{
    public primalistCallStorm(){
        super("Call Storm",
                "Evokes water spirits to strike the enemy with a flood of water.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        return toCheck.getSP() >= 30;
    }

    @Override //deal 80% damage calculated by int.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round((.8f) * Caster.getTempInt()), "Water");
        //deal 80% of int damage of water property
    }
}
