package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class crusaderCallArtillery extends Skill{
    public crusaderCallArtillery(){
        super("Call Artillery",
                "Commands reinforcements to bombard the entire enemy party from afar with catapults.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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
        return 4;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 50;
    }

    @Override //deals variable damage of neutral or fire property or misses 1/3 of the time
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(3);
        if(Roll == 2) {
            Defender.takeDamage(2 * (Caster.getTempStr()
                    + Caster.getWeaponDamage(true)), "Fire");
        }
        if(Roll == 1){
            Defender.takeDamage(Math.round(1.5f * (Caster.getTempStr()
                    + Caster.getWeaponDamage(true))), "Neutral");
        }
        //otherwise the skill misses
    }
}
