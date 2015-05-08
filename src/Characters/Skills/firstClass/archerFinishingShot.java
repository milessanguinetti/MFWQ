package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class archerFinishingShot extends Skill{
    public archerFinishingShot(){
        super("Finishing Shot",
                "Tries to take down the target with one last shot. Effective against targets below half health.", 30);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(30);
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
        if(toCheck.hasWeaponType("Bow", true))
            return false;
        return toCheck.getSP() >= 30;
    }

    @Override //deals 200% dex + weapon damage if foe has less than half health,
    //otherwise only deals 25% of dex + weapon damage
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if((Defender.getHP() * 2) <= Defender.getHPCap())
            Defender.takeDamage(2 * (Caster.getWeaponDamage(true) +
                    Caster.getTempDex()), Caster.getWeaponProperty(true));
        //deal 200% dex + weapon damage if defender is below half health
        else
            Defender.takeDamage(Math.round(.25f * (Caster.getWeaponDamage(true)
                    + Caster.getTempDex())), Caster.getWeaponProperty(true));
        //otherwise, deal 25% weapon damage + dex
    }
}
