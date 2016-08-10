package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class spPoison extends statusEffectData implements endOfTurn{
    private int Damage;

    public spPoison(int damage, int duration){
        super("Neurotoxin", duration);
        Damage = damage;
    }

    @Override
    public boolean isPositive(){
        return false;
    }

    @Override
    public void endOfTurnEffects(gameCharacter toAffect) {
        Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " is afflicted by a neurotoxin!");
        toAffect.takeAbsoluteDamage(Damage);
        toAffect.subtractSP(Damage);
    }
}
