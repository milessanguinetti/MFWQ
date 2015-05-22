package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class occultistInvokeAether extends Skill{
    public occultistInvokeAether(){
        super("Invoke Aether",
                "Invokes an explosion of quintessence, dealing holy, unholy or ghost property damage in relation to" +
                        " the caster's and defender's intelligence.", 35);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(35);
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
        return toCheck.getSP() >= 35;
    }

    @Override //random property damage relational to a random value and the ratio of
              //the caster's int and the defender's
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        int Roll = Rand.nextInt(5);
        int property = Roll % 3; //get a property value between 0 and 2 inclusive.
        Roll /= 2;
        if(Caster.hasWeaponType("1h Staff", false) || Caster.hasWeaponType("2h Staff", false))
            ++Roll;
        if(property == 0) //ghost case
            Defender.takeDamage((Caster.getTempInt() * Roll) / Defender.getTempInt(), "Ghost");
        else if(property == 1) //holy case
            Defender.takeDamage((Caster.getTempInt() * Roll) / Defender.getTempInt(), "Holy");
        else //unholy case
            Defender.takeDamage((Caster.getTempInt() * Roll) / Defender.getTempInt(), "Unholy");

    }
}
