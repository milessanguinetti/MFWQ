package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles on 5/5/2015.
 */
public class Burning extends statusEffectData implements endOfTurn{
    int Damage;

    public Burning(int damage, int duration){
        super("Burning", duration);
        Damage = damage;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println( " is aflame.");
        toAffect.takeAbsoluteDamage(Damage);
    }
}
