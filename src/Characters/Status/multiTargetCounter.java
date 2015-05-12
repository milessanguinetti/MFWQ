package Characters.Status;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class multiTargetCounter extends Counter{
    float Multiplier; //keeps track of how much damage to deal.

    public multiTargetCounter(int duration, float damageMultiplier){
        super(duration);
        Multiplier = damageMultiplier;
    }

    @Override //deal weapon damage + strength times multiplier
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Defender.printName();
        System.out.println(" countered!");
        Attacker.takeDamage(Math.round(Multiplier * (Defender.getTempStr()
                        + Defender.getWeaponDamage(true))), Defender.getWeaponProperty(true));
    }
}
