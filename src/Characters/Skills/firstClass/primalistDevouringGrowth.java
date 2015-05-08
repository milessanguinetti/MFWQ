package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.exponentialDoT;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class primalistDevouringGrowth extends Skill{
    public primalistDevouringGrowth(){
        super("Devouring Growth",
                "Assaults the target with consuming seeds that deal more damage as time goes on.", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 15;
    }

    @Override //A DoT that deals half of the caster's weapon damage, increasing by 150% each turn for 4 turns
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.addStatus(new exponentialDoT(Caster.getWeaponDamage(true)/2, 4, "Devouring Growth", 1.5f));
    }
}
