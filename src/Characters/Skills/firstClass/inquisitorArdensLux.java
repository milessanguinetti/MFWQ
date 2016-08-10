package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.statusEffects.Burning;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/2015.
 */
//offensive, holy based inquisitor skill
public class inquisitorArdensLux extends Skill {
    public inquisitorArdensLux(){
        super("Ardens Lux", "Burns the target with searing light from the heavens.", 10);
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
        if((toCheck.getSP() >= 10))
            return true; //has sp for this skill
        return false;
    }

    @Override //deal str + right weapon damage and apply a ticking DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getTempFth() * 3), "Holy");
        Random Rand = new Random();
        if(Rand.nextInt(2) == 0){
            Defender.addStatus(new Burning(Caster.getTempFth(), 3));
            Defender.printName();
            System.out.println(" was set on fire!");
        }
        //50% of the time, the defender will also burn for the caster's faith over 5 turns
    }
}
