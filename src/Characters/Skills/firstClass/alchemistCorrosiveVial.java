package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.armorBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/2015.
 */
public class alchemistCorrosiveVial extends Skill{
    public alchemistCorrosiveVial(){
        super("Corrosive Vial",
                "Hurls a caustic vial at the enemy, melting away armor.", 10);
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
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if((toCheck.getSP() >= 10))
            return true; //has sp for this skill
        return false;
    }

    @Override
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new armorBuff(10, .5f));
        //halves armor for 10 turns.
    }
}
