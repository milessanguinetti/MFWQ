package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/4/2015.
 */
public class soothingLight extends statusEffectData implements endOfTurn{
    int Healing; //integer for keeping track of how much damage this poison instance
    //deals on a turn-to-turn basis.

    public soothingLight(int healing, int duration){
        super("Soothing Light", duration);
        Healing = healing;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println( " is bathed in soothing light.");
        toAffect.takeAbsoluteDamage(Healing * -1);
    }
}
