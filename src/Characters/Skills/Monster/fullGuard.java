package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.statusEffects.Guard;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class fullGuard extends Skill{
    gameCharacter toGuard;

    //default constructor
    public fullGuard(){
        super("Guard",
                "", 0);
    }

    //constructor with a specified target.
    public fullGuard(gameCharacter toguard){
        super("Guard", "", 0);
        toGuard = toguard;
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        //this skill uses no sp
    }

    @Override
    public boolean notUsableOnDead() {
        return true;
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
        return true;
    }

    @Override //shield an ally for 75% damage taken; increase their armor by 25%
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(toGuard == Caster) {
            Caster.printName();
            Game.battle.getInterface().printLeftAtNextAvailable(" cannot guard themself!");
        }
        else{ //case for when the caster is guarding an ally
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " defends " + toGuard.getName() +
            " with its life!");
            toGuard.addStatus(new Guard(1, 21, Caster));
        }
    }
}
