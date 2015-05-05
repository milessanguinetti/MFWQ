package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class strBuff extends statusEffectData implements statChange{
    float Value; //the boost to strength

    public strBuff(int duration, float value){
        super("strBuff", duration);
        Value = value;
    }

    @Override
    public void changeStats(gameCharacter toAffect) {
        toAffect.tempMultiply(0, Value);
    }
}
