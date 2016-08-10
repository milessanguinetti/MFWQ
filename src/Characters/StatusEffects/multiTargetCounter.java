package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class multiTargetCounter extends Counter{
    private float Multiplier; //keeps track of how much damage to deal.

    public multiTargetCounter(int duration, float damageMultiplier){
        super(duration);
        Multiplier = damageMultiplier;
    }

    @Override //deal weapon damage + strength times multiplier
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " countered!");
        Attacker.takeDamage(Math.round(Multiplier * (Defender.getTempStr()
                        + Defender.getWeaponDamage(true))), Defender.getWeaponProperty(true));
    }
}
