package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageAdditive;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class roguePilferDefenses extends Skill{
    public roguePilferDefenses(){
        super("Pilfer Defenses",
                "Steals the target's armor, increasing their damage taken while decreasing the user's.", 15);
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
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 15;
    }

    @Override //increases target damage taken by str/5 while decreasing the user's by the same value
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new damageAdditive(Caster.getTempStr()/5, 5));
        Caster.addStatus(new damageAdditive(-Caster.getTempStr()/5, 5));
    }
}
