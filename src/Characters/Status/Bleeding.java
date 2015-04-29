package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class Bleeding extends statusEffectData implements endOfTurn {
    int Damage;

    public Bleeding(int damage, int duration){
        super("Bleeding", duration);
        Damage = damage;
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println( " is bleeding.");
        toAffect.takeAbsoluteDamage(Damage);
    }
}
