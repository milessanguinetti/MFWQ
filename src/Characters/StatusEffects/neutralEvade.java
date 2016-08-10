package Characters.statusEffects;

import Profile.Game;
import Structures.statusEffectData;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class neutralEvade extends statusEffectData implements damageEffect{
    private int Percent; //percentage change to avoid an attack.

    public neutralEvade(int percent, int duration){
        super("Evasion", duration);
        Percent = percent;
    }

    @Override
    public boolean isPositive(){
        return true;
    }

    public int calculateDamage(int toCalculate, String Property) {
        if(Property.equals("Neutral")) { //can only dodge neutral damage
            Random Rand = new Random();
            Percent -= Rand.nextInt(100);
            if(Percent > 0){
                Game.battle.getInterface().printLeftAtNextAvailable("The attack was evaded!");
                return 0;
            }
        }
        return toCalculate;
    }
}
