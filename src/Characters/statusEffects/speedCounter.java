package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class speedCounter extends Counter{
    private Counter oldCounter; //the counter that the character had before assuming this one

    public speedCounter(Counter toset){
        super(1);
        oldCounter = toset;
    }

    @Override //deal weapon damage + strength times multiplier
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Defender.setCounter(oldCounter);
    }

    @Override
    public boolean canEvadeAttack(gameCharacter Attacker, gameCharacter Defender) {
        if(Attacker.getTempSpd() < Defender.getTempSpd()){
            Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() +
                    " nimbly evaded the attack and countered!");
            Attacker.takeDamage(Defender.getWeaponDamage(true) + Defender.getTempSpd() - Attacker.getTempSpd(),
                    Defender.getWeaponProperty(true));
            return true;
        }
        return false;
    }
}
