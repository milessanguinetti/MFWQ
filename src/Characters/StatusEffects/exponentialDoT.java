package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class exponentialDoT extends statusEffectData implements endOfTurn{
    int Damage; //integer for keeping track of how much damage this effect deals
    String Message; //the DoT effect's name
    float Multiplier;

    public exponentialDoT(int damage, int duration, String message, float multiplier){
        super(message, duration);
        Damage = damage;
        Multiplier = multiplier;
    }

    public boolean isPositive(){
        if(Damage > 0)
            return false;
        else
            return true;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        Damage = Math.round(Multiplier * Damage);
        if(Damage > 0) {
            Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " took increasing damage from " +
                    Message + "!");
        }
        else {
            Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " was healed by " + Message +
                    " as the effect grew stronger!");
        }
        toAffect.takeAbsoluteDamage(Damage);
    }
}
