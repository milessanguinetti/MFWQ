package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//regens 20% of SP at the end of each turn
public class spRegen extends statusEffectData implements endOfTurn{
    public spRegen(int duration){
        super("Magic Regeneration", duration);
    }

    @Override
    public boolean isPositive(){
        return true;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.subtractSP(Math.round(toAffect.getSPCap() * -.2f));
        Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " regenerated some SP.");
    }
}
