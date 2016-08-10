package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/1/15.
 */
public class Poisoned extends statusEffectData implements endOfTurn {
    private int Damage; //integer for keeping track of how much damage this poison instance
                //deals on a turn-to-turn basis.

    public Poisoned(int damage, int duration){
        super("Poisoned", duration);
        Damage = damage;
    }

    @Override
    public boolean isPositive(){
        return false;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " took " + Damage +
                " damage from poison!");
        toAffect.takeAbsoluteDamage(Damage);
    }
}
