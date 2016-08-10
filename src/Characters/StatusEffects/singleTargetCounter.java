package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class singleTargetCounter extends Counter{
    private gameCharacter Target;

    public singleTargetCounter(int duration, gameCharacter toCounter){
        super(duration);
        Target = toCounter;
    }

    @Override //deal weapon damage + strength
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        if(Attacker == Target){
            Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " countered " + Defender.getName()
                +"'s attack!");
            Target.takeDamage(Defender.getTempStr() + Defender.getWeaponDamage(true),
                    Defender.getWeaponProperty(true));
        }
    }
}
