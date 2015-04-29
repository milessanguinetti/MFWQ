package Characters.Skills;

import Characters.gameCharacter;
import Characters.playerCharacter;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
//a general low-SP cleave attack.
public class soldierWideSlash extends Skill{
    public soldierWideSlash(){
        super("Wide Slash", "Slashes around the user to cleave through multiple foes.", 20);
    }
    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
    }

    @Override
    public boolean isOffensive() {
        return true;
    }

    @Override
    public int getAoE() {
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return true;
    }

    @Override //deal 80% of right weapon damage + strength
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(4 * (Caster.getTempStr() +
                Caster.getWeaponDamage(true))/5), "Neutral");
    }
}
