package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class exponentialDoT extends statusEffectData implements endOfTurn{
    int Damage; //integer for keeping track of how much damage this effect deals
    String Message; //
    float Multiplier;

    public exponentialDoT(int damage, int duration, String message, float multiplier){
        super(message, duration);
        Damage = damage;
        Multiplier = multiplier;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        Damage = Math.round(Multiplier * Damage);
        toAffect.printName();
        if(Damage > 0) {
            System.out.print("'s " + Message + " condition worsened!");
            toAffect.takeAbsoluteDamage(Damage);
        }
        else {
            System.out.print(" was healed by " + Message + " as the effect grew stronger!");
            toAffect.takeAbsoluteDamage(Damage);
        }
    }
}
