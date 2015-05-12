package Characters.Status;

import Characters.gameCharacter;

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
            Defender.printName();
            System.out.println(" countered!");
            Target.takeDamage(Defender.getTempStr() + Defender.getWeaponDamage(true),
                    Defender.getWeaponProperty(true));
        }
    }
}
