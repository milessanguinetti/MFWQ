package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class spdBuff extends statusEffectData implements statChange{
    float Value; //the boost to speed

    public spdBuff(int duration, float value){
        super(duration);
        Value = value;
        if(Value < 1)
            Name = "spdDebuff";
        else
            Name = "spdBuff";
    }

    @Override //multiply temp speed by the value
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(2, Value);
    }

}
