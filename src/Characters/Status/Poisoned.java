package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/1/15.
 */
public class Poisoned extends statusEffectData implements endOfTurn {
    int Damage;

    public Poisoned(int damage, int duration){
        super("Poisoned", duration);
        Damage = damage;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println( " is poisoned.");
        toAffect.takeAbsoluteDamage(Damage);
    }
}
