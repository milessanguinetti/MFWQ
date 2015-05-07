package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class dexBuff extends statusEffectData implements statChange{
    float Value; //the boost to dex

    public dexBuff(int duration, float value){
        super(duration);
        Value = value;
        if(Value < 1)
            Name = "dexDebuff";
        else
            Name = "dexBuff";
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(1, Value);
    }
}
