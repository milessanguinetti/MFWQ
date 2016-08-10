package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Bleeding;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class soldierRend extends Skill {
    public soldierRend(){
        super("Rend", "Rends the target with a bladed weapon.", 10);
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
        if(toCheck.getSP() < 10)
            return false; //doesn't have SP for this
        if(!toCheck.hasWeaponType("2h Edged", true) && !toCheck.hasWeaponType("1h Edged", true))
            return false; //rend can only be used with bladed weapons.
        return true;
    }

    @Override //deal str + right weapon damage and apply a ticking DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempStr() +
                Caster.getWeaponDamage(true)), Caster.getWeaponProperty(true));

        Random Rand = new Random();
        if(Rand.nextInt(2) == 0) {
            Defender.addStatus(new Bleeding(Caster.getWeaponDamage(true), 5));
            Defender.printName();
            System.out.println(" was afflicted with bleeding!");
        }
        //50% of the time, the defender will also bleed for the attacker's weapon damage for 5 turns.
    }
}
