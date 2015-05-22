package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterInvokeSteel extends Skill{
    public enchanterInvokeSteel(){
        super("Invoke Steel",
                "Invokes the very steel of the user's right hand weapon for an intelligence-based attack.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
    }

    @Override
    public boolean notUsableOnDead(){
        return true; //not usable on dead characters
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
        return toCheck.getSP() >= 10;
    }

    @Override //deal 125% damage calculated by int and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(1.25f*(Caster.getTempInt() +
                Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
    }
}
