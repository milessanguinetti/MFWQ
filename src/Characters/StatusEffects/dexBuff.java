package Characters.statusEffects;

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
        Name = "Dexterity x" + value;
    }

    public boolean isPositive(){
        if(Value > 1)
            return true;
        else
            return false;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(1, Value);
    }
}
