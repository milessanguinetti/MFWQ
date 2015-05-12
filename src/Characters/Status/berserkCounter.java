package Characters.Status;

import Characters.gameCharacter;

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
            Defender.printName();
            System.out.println(" countered with a raging attack!");
            Attacker.takeDamage(2 * (Defender.getTempStr() + Defender.getWeaponDamage(true)),
                    Defender.getWeaponProperty(true));
        }
    }
}
