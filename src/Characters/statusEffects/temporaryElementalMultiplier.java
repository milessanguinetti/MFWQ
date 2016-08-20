package Characters.statusEffects;

import Structures.statusEffectData;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class temporaryElementalMultiplier extends statusEffectData implements damageEffect{
    private String whichElement; //the element that this imposes a weakness or resistance to
    private float Multiplier; //the multiplicative value by which this multiplies damage

    public temporaryElementalMultiplier(String whichelement, float multiplier, int duration){
        super(duration);
        whichElement = whichelement;
        Multiplier = multiplier;
        if(Multiplier > 1)
            Name = whichElement + " Weakness";
        else
            Name = whichElement + " Resistance";
    }

    public boolean isPositive(){
        if(Multiplier < 1)
            return true;
        else
            return false;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(Property.matches(whichElement))
            return Math.round(toCalculate * Multiplier);
        else
            return toCalculate;
    }

}
