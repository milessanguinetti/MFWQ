package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 4/30/2015.
 */
public class Poison extends statusEffectData implements endOfTurn{
    int Damage;

    public Poison(int damage, int duration){
        super("Poisoned", duration);
        Damage = damage;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println( " is poisoned.");
        toAffect.takeAbsoluteDamage(Damage);
    }
}
