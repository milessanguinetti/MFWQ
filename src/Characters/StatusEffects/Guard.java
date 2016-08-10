package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class Guard extends statusEffectData implements damageEffect{
    private gameCharacter toRedirect; //who we are redirecting to
    private float Percent; //percentage of damage being redirected.

    public Guard(float percent, int duration, gameCharacter Guarder){
        super("Guard", duration);
        Percent = percent;
        toRedirect = Guarder;
    }

    public boolean isPositive(){
        return true;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(toRedirect.isAlive()) { //this only does anything is the guardian is alive
            Game.battle.getInterface().printLeftAtNextAvailable(toRedirect.getName() + " took most of the hit!");
            toRedirect.takeDamage(Math.round(toCalculate * Percent), Property);
            //the guardian takes damage equal to however much they are avoiding.
            //this damage is effected by their armor and other variables, however.
            return Math.round(toCalculate * (1 - Percent)); //take the damage
        }
        return toCalculate;
    }
}
