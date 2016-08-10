package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class berserkCounter extends Counter{
    public berserkCounter(int duration){
        super(duration);
    }

    @Override //deal weapon damage + strength times 2
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        if((Defender.getHP() * 2) > Defender.getHPCap()){
            Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " countered with a raging attack!");
            Attacker.takeDamage(2 * (Defender.getTempStr() + Defender.getWeaponDamage(true)),
                    Defender.getWeaponProperty(true));
        }
    }
}
