package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class spPoison extends statusEffectData implements endOfTurn{
    int Damage;

    public spPoison(int damage, int duration){
        super("spPoison", duration);
        Damage = damage;
    }

    @Override
    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println(" is afflicted by a neurotoxin!");
        toAffect.takeAbsoluteDamage(Damage);
        toAffect.subtractSP(Damage);
    }
}
