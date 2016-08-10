package Characters.statusEffects;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 3/18/15.
 */
//interface for effects that change a character's stats
public interface statChange {
    //method that changes a given character's stats
    public void changeStats(gameCharacter toAffect);
}
