package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class armorBuff extends statusEffectData implements statChange{
    float Value; //the boost to armor

    public armorBuff(int duration, float value){
        super("armorBuff", duration);
        Value = value;
        if(Value < 1)
            Name = "armorDebuff";
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(6, Value);
    }
}
