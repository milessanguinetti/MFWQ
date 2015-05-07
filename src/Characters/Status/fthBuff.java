package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class fthBuff extends statusEffectData implements statChange{
    float Value; //the boost to faith

    public fthBuff(int duration, float value){
        super(duration);
        Value = value;
        if(Value < 1)
            Name = "fthDebuff";
        else
            Name = "fthBuff";
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(5, Value);
    }
}
