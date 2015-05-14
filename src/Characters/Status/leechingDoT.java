package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class leechingDoT extends statusEffectData implements endOfTurn{
    gameCharacter toHeal; //whoever we are healing
    int Damage;

    public leechingDoT(int damage, int duration, gameCharacter toheal){
        super("leechingDoT", duration);
        Damage = damage;
        toHeal = toheal;
    }

    @Override
    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.printName();
        System.out.println(" was harmed by a leeching affliction!");
        toAffect.takeAbsoluteDamage(Damage);
        toHeal.takeAbsoluteDamage(-Damage);
    }
}
