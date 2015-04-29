package Characters.Skills;

import Characters.gameCharacter;

/**
 * Created by Miles on 4/29/2015.
 */
public class rogueEnvenomedBlade extends Skill{
    public rogueEnvenomedBlade(){
        super("Envenomed Blade",
                "Strikes the target with a poisoned weapon. Can cause a variety of negative effects.", 10);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(10);
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
        return true;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round((5/4)*(Caster.getTempStr() +
                (Caster).getWeaponDamage(true))), "Neutral");
    }
}
