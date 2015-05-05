package Characters.Skills.Passive;

import Characters.Status.hitLimitedDamageReduction;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class armorOfStone extends passiveSkill{
    public armorOfStone(){
        super("Armor of Stone",
                "Absorbs the heft of the first tack received by the caster.");
    }

    @Override
    public void passiveEffect(gameCharacter toEffect) {
        toEffect.addStatus(new hitLimitedDamageReduction(.25f, 10, 1));
    }
}
