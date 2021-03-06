package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/12/15.
 */
public class assassinLightningStrikes extends Skill{
    public assassinLightningStrikes(){
        super("Lightning Strikes",
                "Strikes with the speed of lightning, repeatedly attacking the target with both weapons.", 60);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(60);
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
        if(toCheck.hasWeaponType("Shield", false))
            return false;
        return toCheck.getSP() >= 60;
    }

    @Override //strike with both weapons, a number of times based on speed
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        int Strikes = (Caster.getTempSpd() + 10) / 8; //strike once per 8 speed
        Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " struck " + Strikes + " times!");
        for (int i = Strikes; i > 0; --i) {
            Defender.takeDamage(Math.round(.4f * (Caster.getTempStr() +
                    Caster.getWeaponDamage(true))), Caster.getWeaponProperty(true));
            if (!Caster.hasTwoHandedWeapon()) { //only attack with both weapons if the weapons
                                                //are both one-handed.
                Defender.takeDamage(Math.round(.4f * (Caster.getTempDex() +
                        Caster.getWeaponDamage(false))), Caster.getWeaponProperty(false));
            }
        }
    }
}
