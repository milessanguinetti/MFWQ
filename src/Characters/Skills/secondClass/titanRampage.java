package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.Status.multiTargetCounter;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class titanRampage extends Skill{
    public titanRampage(){
        super("Rampage",
                "Hits the entire enemy team with a massive strike. Attacks against the user will trigger" +
                " a violent response.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
        Caster.setCounter(new multiTargetCounter(2, .5f));
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
        return toCheck.getSP() >= 20;
    }

    @Override //for 1 turns after the spell is cast, counters for .5(str + weapon damage)
              //also deals 50% weapon damage + str
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(.5f * (Caster.getWeaponDamage(true)
                + Caster.getTempStr())), "Neutral");
    }
}
