package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles on 5/5/2015.
 */
public class Burning extends statusEffectData implements endOfTurn{
    private int Damage;

    public Burning(int damage, int duration){
        super("Burning", duration);
        Damage = damage;
    }

    public boolean isPositive(){
        return false;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " burned for " + Damage + " damage.");
        toAffect.takeDamage(Damage, "Fire");
    }
}
