package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.spRegen;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class inquisitorAbsolution extends Skill {
    public inquisitorAbsolution(){
        super("Absolution", "Absolves the target of their sins, restoring SP over time.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        if(!(toCheck.getSP() >= 20))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //regen 20% max SP over 5 turns.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new spRegen(5));
        Defender.printName();
        System.out.println("'s SP regeneration was increased!");
    }
}
