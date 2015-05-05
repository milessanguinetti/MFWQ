package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//increases both strength and dex by a fixed value
public class additivePhysicalBuff extends statusEffectData implements statChange{
    int Value; //the boost to str/dex

    public additivePhysicalBuff(int duration, int value){
        super("additivePhysicalBuff", duration);
        Value = value;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.incrementTemp(0, Value);
        toAffect.incrementTemp(1, Value); //increase both str and dex by the value
    }
}
