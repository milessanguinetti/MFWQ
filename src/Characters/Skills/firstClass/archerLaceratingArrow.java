package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.Bleeding;
import Characters.gameCharacter;
import Characters.playerCharacter;

import java.util.Random;

/**
 * Created by Miles on 5/5/2015.
 */
public class archerLaceratingArrow extends Skill {
    public archerLaceratingArrow(){
        super("Lacerating Arrow",
                "Shoots a barbed arrow at the target that cuts deep into the flesh and can leave them bleeding.", 10);
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
        if(toCheck.getSP() < 10)
            return false; //doesn't have SP for this
        if(!toCheck.hasWeaponType("Bow", true))
            return false; //lacerating arrow can only be used with bow
        return true;
    }

    @Override //deal dex + right weapon damage and apply a ticking DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempDex() +
                ((playerCharacter)Caster).getWeaponDamage(true)), Caster.getWeaponProperty(true));

        Random Rand = new Random();
        if(Rand.nextInt(2) == 0) {
            Defender.addStatus(new Bleeding(((playerCharacter) Caster).getWeaponDamage(true), 5));
            Defender.printName();
            System.out.println(" was afflicted with bleeding!");
        }
        //50% of the time, the defender will also bleed for the attacker's weapon damage for 5 turns.
    }
}
