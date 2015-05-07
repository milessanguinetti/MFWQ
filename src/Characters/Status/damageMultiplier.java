package Characters.Status;

import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class damageMultiplier extends statusEffectData implements damageEffect{
    float Reduction; //percentage of damage reduced

    public damageMultiplier(float reduction, int duration){
        super(duration);
        Reduction = reduction;
        if(Reduction < 1)
            Name = "damageMultIncrease";
        else
            Name = "damageMultDecrease";
    }

    public int calculateDamage(int toCalculate, String Property) {
            return Math.round(toCalculate * Reduction);
    }
}
