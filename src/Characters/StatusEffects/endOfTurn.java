package Characters.statusEffects;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
//interface for effects that happen at the end of one's turn
public interface endOfTurn {
    //end of turn calculation method
    public void endOfTurnEffects(gameCharacter toAffect);
}
