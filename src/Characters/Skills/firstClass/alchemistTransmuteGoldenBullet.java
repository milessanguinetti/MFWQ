package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/1/2015.
 */
public class alchemistTransmuteGoldenBullet extends Skill {
    public alchemistTransmuteGoldenBullet(){
        super("Transmute: Golden Bullet",
                "Transmutes coins into a powerful bullet. Requires a gun to cast.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Game.Player.addCoins(-1*Caster.getWeaponDamage(false));
        //player loses coins equal to the caster's weapon damage
        Caster.subtractSP(5);
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
        if(toCheck.getSP() <= 5)
            return false; //SP requirements
        if(!Game.Player.hasCoins(toCheck.getWeaponDamage(false)))
            return false; //this spell cannot be used if the player does not have at
                          //least as many coins as this character has weapon damage
        if(!toCheck.hasWeaponType("Gun", false))
            return false; //skill can only be used with a gun.
        return true; //otherwise, the skill can be used
    }

    @Override //deal (dex + 2 * weapon damage) * 1.5 damage
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage(Math.round((Caster.getWeaponDamage(false) * 2 +
                Caster.getTempDex()) * 1.5f), Caster.getWeaponProperty(false));
    }
}
