package Characters.Skills;

import Characters.Status.Bleeding;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class koboldStrike extends Skill{
    public koboldStrike(){
        super("Kobold Strike", "", 15);
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
        return true;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempStr() +
                (Caster).getWeaponDamage(true)), "Neutral");

        Random Rand = new Random();
        if(Rand.nextInt(4) == 0)
            Defender.addStatus(new Bleeding((Caster).getWeaponDamage(true), 5));
        //25% of the time, the defender will also bleed for the attacker's weapon damage for 3 turns.
    }
}
