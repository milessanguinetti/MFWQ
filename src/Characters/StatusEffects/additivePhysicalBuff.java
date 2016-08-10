package Characters.statusEffects;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//increases both strength and dex by a fixed value
public class additivePhysicalBuff extends statusEffectData implements statChange{
    int Value; //the boost to str/dex

    public additivePhysicalBuff(int duration, int value){
        super("Physical Damage +" + value, duration);
        Value = value;
        if(Value < 0)
            Name = "Physical Damage " + value;
    }

    @Override
    public boolean isPositive(){
        if(Value > 0)
            return true;
        else
            return false;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.incrementTemp(0, Value);
        toAffect.incrementTemp(1, Value); //increase both str and dex by the value
    }
}
