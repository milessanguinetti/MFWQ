package Characters.Status;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class poisonCounter extends Counter{
    public poisonCounter(int duration){
        super(duration);
    }

    @Override //deal weapon damage + strength times multiplier
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Defender.printName();
        System.out.println(" poisoned their attacker!");
        Attacker.addStatus(new Poisoned(3, 2 * Defender.getWeaponDamage(true)));
    }
}
