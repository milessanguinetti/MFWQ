package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class Bleeding extends statusEffectData implements endOfTurn {
    private int Damage;

    public Bleeding(int damage, int duration){
        super("Bleeding", duration);
        Damage = damage;
    }

    public boolean isPositive(){
        return false;
    }

    public void endOfTurnEffects(gameCharacter toEffect) {
        Game.battle.getInterface().printLeftAtNextAvailable(toEffect.getName() + " bled for " + Damage + " damage.");
        toEffect.takeAbsoluteDamage(Damage);
    }
}
