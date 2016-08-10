package Characters.Skills;

import Characters.Monsters.babyKobold;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/28/15.
 */
public class Flee extends Skill {
    public Flee(){
        super("", "", 0);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(0);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
    }

    @Override
    public int getAoE() {
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //poops out a baby
    public void takeAction(gameCharacter Caster, gameCharacter Defender)throws fleeObject {
        Random Rand = new Random();
        if(Rand.nextInt(2) == 1)
            throw new fleeObject();
        else {
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " fled!");
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " could not escape!");
        }
    }
}
