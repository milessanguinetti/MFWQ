package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/1/15.
 */
public class deliriantPoison extends statusEffectData implements statChange {
    public deliriantPoison(int duration){
        super("Deliriant Poison", duration);
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.incrementTemp(0, .75f);
        toAffect.incrementTemp(1, .75f);
        toAffect.incrementTemp(2, .5f);
        //reduce str and dex by 25% and speed by 50%
    }
}
