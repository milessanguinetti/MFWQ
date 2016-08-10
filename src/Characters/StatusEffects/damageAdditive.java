package Characters.statusEffects;

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
            Name = "Damage Taken +" + Increase;
        else
            Name = "Damage Taken " + Increase;
    }

    public boolean isPositive(){
        if(Increase > 0)
            return true;
        else
            return false;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(toCalculate + Increase > 0)
            return toCalculate + Increase;
        return 1; //can't increase damage below 1.
    }
}
