package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Poisoned;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class primalistBogMiasma extends Skill {
    public primalistBogMiasma(){
        super("Bog Miasma",
                "Summons a cloud of foul miasma to poison multiple foes.", 20);
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

    @Override //deal 20% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(4);
        if (!(Roll == 3)) { //3/4 of the time, inflict standard poison
                Defender.addStatus(new Poisoned(Caster.getTempInt(), 5));
        }
        //otherwise the skill fails to poison.
    }
}
