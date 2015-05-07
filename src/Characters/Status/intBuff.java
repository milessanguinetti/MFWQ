package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class intBuff extends statusEffectData implements statChange{
    float Value; //the boost to int

    public intBuff(int duration, float value){
        super(duration);
        Value = value;
        if(Value < 1)
            Name = "intDebuff";
        else
            Name = "intBuff";
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(4, Value);
    }

}
