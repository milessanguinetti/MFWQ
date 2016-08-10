package Characters.statusEffects;

import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class hitLimitedDamageReduction extends statusEffectData implements damageEffect{
    float Reduction; //percentage of damage reduced
    int hitsRemaining; //how many hits this effect remains for.

    public hitLimitedDamageReduction(float reduction, int duration, int hits){
        super("x" + reduction + " damage for " + hits, duration);
        Reduction = reduction;
        hitsRemaining = hits;
    }

    public boolean isPositive(){
        if(Reduction > 1)
            return false;
        else
            return true;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(hitsRemaining > 0)
            toCalculate = Math.round(toCalculate * Reduction);
        --hitsRemaining;
        return toCalculate;
    }
}
