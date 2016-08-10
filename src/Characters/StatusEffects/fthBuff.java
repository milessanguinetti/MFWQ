package Characters.statusEffects;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class fthBuff extends statusEffectData implements statChange{
    private float Value; //the boost to faith

    public fthBuff(int duration, float value){
        super(duration);
        Value = value;
        Name = "Faith x" + value;
    }

    public boolean isPositive(){
        if(Value < 1)
            return false;
        else
            return true;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(5, Value);
    }
}
