package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Guard;
import Characters.statusEffects.armorBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class defenderBulwark extends Skill{
    public defenderBulwark(){
        super("Bulwark",
                "Guards an ally, shielding them from attacks for 10 turns and slightly boosting their armor.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        return toCheck.getSP() >= 20;
    }

    @Override //shield an ally for 75% damage taken; increase their armor by 25%
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender == Caster) {
            Caster.printName();
            System.out.println(" cannot guard themself!");
        }
        else{ //case for when the caster is guarding an ally
            Defender.addStatus(new Guard(.75f, 11, Caster));
            Defender.addStatus(new armorBuff(11, .25f));
        }
    }
}
