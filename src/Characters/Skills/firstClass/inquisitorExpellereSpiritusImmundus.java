package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Battle;
import Profile.Game;

/**
 * Created by Miles on 5/5/2015.
 */
public class inquisitorExpellereSpiritusImmundus extends Skill{
    public inquisitorExpellereSpiritusImmundus(){
        super("Expellere Spiritus Immundus",
                "Drives out dark spirits, dealing holy damage and SP damage to undead and unholy foes.", 15);
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
        if((toCheck.getSP() >= 15))
            return true; //has sp for this skill
        return false;
    }

    @Override //deal 4x faith damage, but only to undead and unholy foes.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.hasProperty("Unholy") || Defender.hasProperty("Undead")) {
            Defender.takeDamage((Caster.getTempFth() * 4), "Holy"); //deal faith-based holy damage
            Defender.subtractSP(Caster.getTempFth() * 4); //deal SP damage
        }
        else
            Game.battle.getInterface().printLeftAtNextAvailable("The spell had no effect on " + Defender.getName() + ".");

    }
}
