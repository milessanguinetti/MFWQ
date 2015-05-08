package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class delayedDamage extends statusEffectData implements endOfTurn{
    int Damage; //integer for keeping track of how much damage this effect deals
    String Message; //a message for when the effect hits.
    String Property; //the property of the damage that we will deal.


    public delayedDamage(int damage, int duration, String message, String property){
        super(message, duration);
        Damage = damage;
        Property = property;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        if(Turns == 0) { //if this is the last turn...
            toAffect.printName();
            if(Damage > 0) {
                System.out.print(" was hit by " + Message + "!");
                toAffect.takeDamage(Damage, Property);
            }
            else {
                System.out.print(" was healed by " + Message + "!");
                toAffect.takeAbsoluteDamage(Damage);
            }
        }
    }
}
