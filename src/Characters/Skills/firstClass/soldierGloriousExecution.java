package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Battle;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/7/15.
 */
public class soldierGloriousExecution extends Skill{
    public soldierGloriousExecution(){
        super("Glorious Execution",
                "Attempts to execute a foe, healing the user if the target is killed.", 10);
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
        if(toCheck.hasWeaponType("Bow", true)) //can only be used with right handed melee weapons
            return false;
        if(toCheck.hasWeaponType("2h Staff", true))
            return false;
        return toCheck.getSP() >= 10;
    }

    @Override //deals 75% weapon damage + str. Heals for caster's strength if target dies.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.takeDamage(Math.round(.75f * (Caster.getWeaponDamage(true) +
                    Caster.getTempStr())), Caster.getWeaponProperty(true)) == 0) {
            Caster.takeAbsoluteDamage(-Caster.getTempStr());
            //if the target des, heal for the caster's temp strength.
            Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() + " was reinvigorated by "
            + Defender.getName() + "'s gruesome death!");
        }

    }
}
