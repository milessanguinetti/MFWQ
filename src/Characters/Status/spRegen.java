package Characters.Status;

import Characters.gameCharacter;
import Structures.statusEffectData;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//regens 20% of SP at the end of each turn
public class spRegen extends statusEffectData implements endOfTurn{
    public spRegen(int duration){
        super("spRegen", duration);
    }

    public void endOfTurnEffects(gameCharacter toAffect) {
        toAffect.subtractSP(Math.round(toAffect.getSPCap() * -.2f));
        toAffect.printName();
        System.out.println(" regenerated some SP.");
    }
}
