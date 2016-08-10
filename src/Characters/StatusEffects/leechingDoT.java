package Characters.statusEffects;

import Characters.gameCharacter;
import Profile.Game;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class leechingDoT extends statusEffectData implements endOfTurn{
    private gameCharacter toHeal; //whoever we are healing
    private int Damage;

    public leechingDoT(int damage, int duration, gameCharacter toheal){
        super("Leech", duration);
        Damage = damage;
        toHeal = toheal;
    }

    public boolean isPositive(){
        return false;
    }

    @Override
    public void endOfTurnEffects(gameCharacter toAffect) {
        Game.battle.getInterface().printLeftAtNextAvailable(toAffect.getName() + " was harmed by a leeching affliction!");
        toAffect.takeAbsoluteDamage(Damage);
        toHeal.takeAbsoluteDamage(-Damage);
    }
}
