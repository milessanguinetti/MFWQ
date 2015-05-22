package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.additivePhysicalBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerOverdraw extends Skill {
    public archerOverdraw(){
        super("Overdraw",
                "The user overdraws their bow, dramatically increasing their damage for a single turn.", 10);
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
        return -1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        if(!(toCheck.getSP() >= 10))
            return false; //doesn't have SP for this
        return toCheck.hasWeaponType("Bow", true); //requires a bow to use
    }

    @Override //increases dex by dex + weapon damage
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new additivePhysicalBuff
                (2, Caster.getTempDex() + Caster.getWeaponDamage(true)));
        Caster.printName();
        System.out.println(" overdrew their bow!");
    }
}
