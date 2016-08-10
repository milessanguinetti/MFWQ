package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class delayedSwitchSides extends statusEffectData implements endOfTurn{
    boolean goesToPlayerSide;

    public delayedSwitchSides(int duration, boolean goestoplayerside){
        super(duration);
        goesToPlayerSide = goestoplayerside;
    }

    public boolean isPositive(){
        if(goesToPlayerSide)
            return true;
        else
            return false;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        if(Turns == 0) {
            Game.battle.nullCombatant(toAffect);
            //remove toAffect from the side that they are currently on.
            if(goesToPlayerSide)
                Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " joined the enemy!");
            else
                Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " joined you!");
            Game.battle.addMinion(goesToPlayerSide, toAffect);
        }
    }
}
