package Characters.Status;

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

    public void endOfTurnEffects(gameCharacter toAffect) {
        if(Turns == 0) {
            Game.Player.getCurrentBattle().nullCombatant(toAffect);
            //remove toAffect from the side that they are currently on.
            toAffect.printName();
            if(goesToPlayerSide)
                System.out.println(" joined the enemy!");
            else
                System.out.println(" joined you!");
            Game.Player.getCurrentBattle().addMinion(goesToPlayerSide, toAffect);
        }
    }
}
