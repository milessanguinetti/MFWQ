package Characters.statusEffects;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class intBuff extends statusEffectData implements statChange{
    private float Value; //the boost to int

    public intBuff(int duration, float value){
        super(duration);
        Value = value;
        if(Value > 0)
            Name = "Intellect +" + value;
        else
            Name = "Intellect " + value;
    }

    public boolean isPositive(){
        if(Value < 0)
            return false;
        else
            return true;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(4, Value);
    }
}
