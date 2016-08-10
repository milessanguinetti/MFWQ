package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class spCounter extends Counter{
    public spCounter(int duration){
        super(duration);
    }

    @Override //regenerate 10% of SP
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " transformed the attack's energy into SP!");
        Defender.subtractSP(Defender.getSPCap()/-10);
    }
}
