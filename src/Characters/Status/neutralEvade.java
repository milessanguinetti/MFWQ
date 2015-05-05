package Characters.Status;

import Structures.statusEffectData;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class neutralEvade extends statusEffectData implements damageEffect{
    int Percent; //percentage change to avoid an attack.

    public neutralEvade(int percent, int duration){
        super("neutralEvade", duration);
        Percent = percent;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(Property.equals("Neutral")) { //can only dodge neutral damage
            Random Rand = new Random();
            Percent -= Rand.nextInt(100);
            if(Percent > 0){
                System.out.println("The attack was evaded!");
                return 0;
            }
        }
        return toCalculate;
    }
}
