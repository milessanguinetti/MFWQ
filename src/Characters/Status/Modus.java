package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
//homunculus modi; an individual class because they last a very long
//time by default and only one can be active at a time.
public class Modus extends statusEffectData implements statChange{
    int Which; //an integer to keep track of which modus we're using.

    public Modus(int which){
        super("Modus", 200); //lasts 200 turns, effectively an infinite buff.
        Which = which;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        if(Which == 0) { //gigas modus case.
            toAffect.tempMultiply(6, 1.5f); //boosts armor by 50%
            toAffect.tempMultiply(2, .5f); //halves speed
        }
        else if(Which == 1){

        }
        else if(Which == 2){

        }
        else{

        }
    }
}
