package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class soldierBash extends Skill {
    public soldierBash(){
        super("Bash", "Bashes the target with an equipped weapon.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(5);
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
        return toCheck.getSP() >= 5;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(1.25f*(Caster.getTempStr() +
                ((playerCharacter)Caster).getWeaponDamage(true))), Caster.getWeaponProperty(true));
    }
}
