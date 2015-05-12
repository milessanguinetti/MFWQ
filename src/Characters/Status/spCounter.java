package Characters.Status;

import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class spCounter extends Counter{
    public spCounter(int duration){
        super(duration);
    }

    @Override //regenerate 10% of SP
    public void executeCounter(gameCharacter Attacker, gameCharacter Defender) {
        Defender.printName();
        System.out.println(" regenerated some SP!");
        Defender.subtractSP(Defender.getSPCap()/-10);
    }
}
