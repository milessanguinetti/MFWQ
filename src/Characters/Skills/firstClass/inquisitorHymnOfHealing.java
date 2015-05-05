package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
//an area of effect healing spell based on faith.
public class inquisitorHymnOfHealing extends Skill {
    public inquisitorHymnOfHealing(){
        super("Hymn of Healing",
                "Sings a sacred hymn to heal the casters party. Scales with faith.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
    }

    @Override
    public boolean isOffensive() {
        return false;
    }

    @Override
    public int getAoE() {
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(toCheck.getSP() >= 30)
            return true;
        return false;
    }

    @Override //heals the entire party for .8 * faith
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeAbsoluteDamage(Math.round(Caster.getTempFth()*-.8f));
    }
}
