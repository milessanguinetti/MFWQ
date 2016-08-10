package Characters.Skills.Passive;

import Characters.statusEffects.neutralEvade;
import Characters.statusEffects.spdBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
//gives the user a 20% chance to dodge and a 50% speed bonus for 3 turns each fight.
public class Alertness extends passiveSkill{
    public Alertness(){
        super("Alertness",
                "At the start of combat, gives the user three turns of enhanced speed and chance to dodge.");
    }

    @Override
    public void passiveEffect(gameCharacter toEffect) {
        toEffect.addStatus(new neutralEvade(20, 3));
        toEffect.addStatus(new spdBuff(3, 1.5f));
    }
}
