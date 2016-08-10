package Characters.statusEffects;

import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class damageMultiplier extends statusEffectData implements damageEffect{
    float Reduction; //percentage of damage reduced

    public damageMultiplier(float reduction, int duration){
        super(duration);
        Reduction = reduction;
        Name = "Damage Taken x" + Reduction;
    }

    public boolean isPositive(){
        if(Reduction < 1)
            return true;
        else
            return false;
    }

    public int calculateDamage(int toCalculate, String Property) {
            return Math.round(toCalculate * Reduction);
    }
}
