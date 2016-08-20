package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class poisonCounter extends Counter{
    public poisonCounter(int duration){
        super(duration);
    }

    @Override //deal weapon damage + strength times multiplier
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " poisoned " + Attacker.getName() +
            " in defense!");
        Attacker.addStatus(new Poisoned(3, 2 * Defender.getWeaponDamage(true)));
    }

    @Override
    public boolean canEvadeAttack(gameCharacter Attacker, gameCharacter Defender) {
        return false;
    }
}
