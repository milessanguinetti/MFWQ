package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterElementalStorm extends Skill{


    public enchanterElementalStorm(){
        super("Elemental Storm",
                "Conjures a storm of raging elements; deals highly random damage of random properties.", 40);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(40);
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
        if(toCheck.getSP() >= 20)
            return true;
        return false;
    }

    @Override //deals damage equal to the caster's int of organic property
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(9); //roll a value between 0 and 8, inclusive.
        int Element = Roll % 3; //randomize an element
        Roll /= 3; //roll is divided by 3, resulting in a damage multiplier between 0 and 3.
        if(Element == 0)
            Defender.takeDamage(Caster.getTempInt() * Roll, "Organic");
        if(Element == 1)
            Defender.takeDamage(Caster.getTempInt() * Roll, "Fire");
        else
            Defender.takeDamage(Caster.getTempInt() * Roll, "Water");

    }
}
