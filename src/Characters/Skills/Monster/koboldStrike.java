package Characters.Skills.Monster;

import Characters.Skills.Skill;
import Characters.statusEffects.Bleeding;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class koboldStrike extends Skill {
    public koboldStrike(){
        super("Kobold Strike", "", 15);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(15);
    }

    @Override
    public boolean notUsableOnDead() {
        return true;
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
        if(toCheck.getSP() >= 15)
            return true;
        return false;
    }

    @Override //deal 125% damage calculated by strength and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempStr() +
                (Caster).getWeaponDamage(true)), "Neutral");

        Random Rand = new Random();
        if(Rand.nextInt(4) == 0) {
            Defender.addStatus(new Bleeding((Caster).getWeaponDamage(true), 5));
            Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " was afflicted with bleeding!");
        }
        //25% of the time, the defender will also bleed for the attacker's weapon damage for 3 turns.
    }
}
