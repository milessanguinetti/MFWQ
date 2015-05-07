package Characters.Status;

import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class damageAdditive extends statusEffectData implements damageEffect{
    int Increase; //damage that we are adding.

    public damageAdditive(int increase, int duration){
        super(duration);
        Increase = increase;
        if(Increase > 0)
            Name = "damageAddIncrease";
        else
            Name = "damageAddDecrease";
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(toCalculate + Increase > 0)
            return toCalculate + Increase;
        return 1; //can't increase damage below 1.
    }
}
