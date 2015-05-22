package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.damageAdditive;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerExposingArrow extends Skill{
    public archerExposingArrow(){
        super("Exposing Arrow",
                "Reveals the target's weak points with a well-placed arrow.", 10);
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
        return toCheck.hasWeaponType("Bow", true); //requires a bow to use.
    }

    @Override //Deal a fairly standard attack and make the target take additional damage
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round(.5f * (Caster.getTempDex()
                + Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
        //deal half of weapon damage + dex damage.
        Defender.addStatus(new damageAdditive(
                Math.round(.2f * Caster.getTempDex()), 4));
        //defender takes 1/5 of caster's dex extra damage from every attack.
    }
}
