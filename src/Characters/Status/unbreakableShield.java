package Characters.Status;

import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class unbreakableShield extends statusEffectData implements damageEffect {

    public unbreakableShield(){
        super("Unbreakable Shield", 10);
    }

    public int calculateDamage(int toCalculate, String Property) {
        return Math.round(toCalculate*.75f);
    }
}
