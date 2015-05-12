package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/4/2015.
 */
public class healOverTime extends statusEffectData implements endOfTurn{
    int Healing; //integer for keeping track of how much healing this status effect
    //does on a turn-to-turn basis.
    String flavor;

    public healOverTime(int healing, int duration){
        super("healOverTime", duration);
        flavor = " is bathed in soothing light.";
        Healing = healing;
    }

    public healOverTime(int healing, int duration, String flavortext){
        super("healOverTime", duration);
        flavor = flavortext;
        Healing = healing;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println(flavor);
        toAffect.takeAbsoluteDamage(Healing * -1);
    }
}
