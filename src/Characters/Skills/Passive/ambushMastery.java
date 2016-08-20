package Characters.Skills.Passive;

import Characters.gameCharacter;
import Characters.statusEffects.neutralEvade;
import Characters.statusEffects.spdBuff;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class ambushMastery extends passiveSkill{
    public ambushMastery(){
        super("Ambush Mastery",
                "Years of assassination experience grant the user greatly enhanced speed for the first turn of combat.");
    }

    @Override
    public void passiveEffect(gameCharacter toEffect) {
        toEffect.addStatus(new spdBuff(1, 5f));
    }
}
