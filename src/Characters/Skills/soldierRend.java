package Characters.Skills;

import Characters.Status.Bleeding;
import Characters.gameCharacter;
import Characters.playerCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class soldierRend extends Skill{
    public soldierRend(){
        super("Rend", "Rends the target with a bladed weapon.", 10);
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

    @Override //deal str + right weapon damage and apply a ticking DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempStr() +
                ((playerCharacter)Caster).getWeaponDamage(true)), "Neutral");

        Random Rand = new Random();
        if(Rand.nextInt(2) == 0)
            Defender.addStatus(new Bleeding(((playerCharacter)Caster).getWeaponDamage(true), 5));
        //50% of the time, the defender will also bleed for the attacker's weapon damage for 5 turns.
    }
}
