package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.additivePhysicalBuff;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/5/15.
 */
public class enchanterImbueWeapon extends Skill {
    public enchanterImbueWeapon(){
        super("Imbue Weapon", "Imbues the caster's physical weaponry with magical energy.", 20);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(20);
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
        if(!(toCheck.getSP() >= 20))
            return false; //doesn't have SP for this
        return false;
    }

    @Override //increases str and dex by half of int
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Caster.addStatus(new additivePhysicalBuff(10, Caster.getTempInt() / 2));
        Caster.printName();
        System.out.println(" imbued their weapon with sorcerous energies!");
    }
}
