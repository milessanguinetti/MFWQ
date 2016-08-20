package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class healingCounter extends Counter{
    public healingCounter(int duration){
        super(duration);
    }

    @Override //heal for half of faith
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " healed some of their wounds!");
        Defender.subtractHP(Defender.getTempFth()/2);
    }

    @Override
    public boolean canEvadeAttack(gameCharacter Attacker, gameCharacter Defender) {
        return false;
    }
}
