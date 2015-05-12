package Characters.Status;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class healingCounter extends Counter{
    public healingCounter(int duration){
        super(duration);
    }

    @Override //heal for half of faith
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Defender.printName();
        System.out.println(" healed some of their wounds!");
        Defender.subtractHP(Defender.getTempFth()/2);
    }
}
