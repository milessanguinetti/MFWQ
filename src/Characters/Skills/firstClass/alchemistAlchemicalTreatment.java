package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class alchemistAlchemicalTreatment extends Skill{
    public alchemistAlchemicalTreatment(){
        super("Alchemical Treatment",
                "Uses alchemy to treat an ally of poison, bleeding and burning.", 10);
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
        return false;
    }

    @Override
    public int getAoE() {
        return 0;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!(toCheck.getSP() <= 10))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //cure basic DoT effects
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.statusRemove("Bleeding")){
            Defender.printName();
            System.out.println(" was cured of bleeding!");
        }
        if(Defender.statusRemove("Burning")){
            Defender.printName();
            System.out.println(" was cured of burning!");
        }
        if(Defender.statusRemove("Poisoned")){
            Defender.printName();
            System.out.println(" was cured of poison!");
        }
    }
}
