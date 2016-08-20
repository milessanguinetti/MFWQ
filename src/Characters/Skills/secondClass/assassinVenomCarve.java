package Characters.Skills.secondClass;

import Characters.Skills.Skill;
import Characters.gameCharacter;
import Characters.statusEffects.Bleeding;
import Characters.statusEffects.Poisoned;
import Profile.Game;

import java.util.Random;

/**
 * Created by Spaghetti on 8/16/2016.
 */
public class assassinVenomCarve extends Skill{
    public assassinVenomCarve(){
        super("Venom Carve", "Coats weapons in potent toxins and attacks, leaving the target bleeding and poisoned.", 25);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(25);
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
        if(toCheck.getSP() < 25)
            return false; //doesn't have SP for this
        if(!toCheck.hasWeaponType("2h Edged", true) && !toCheck.hasWeaponType("1h Edged", true))
            return false; //this spell can only be used with bladed weapons.
        return true;
    }

    @Override //deal right weapon damage and apply a bleeding and poison DoT.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.takeDamage((Caster.getWeaponDamage(true)), Caster.getWeaponProperty(true));

        Defender.addStatus(new Poisoned(Caster.getWeaponDamage(true)/2 + Caster.getTempDex(), 5));
        Defender.addStatus(new Bleeding(Caster.getWeaponDamage(true)/2 + Caster.getTempStr(), 5));
        Game.battle.getInterface().printLeftAtNextAvailable(Defender.getName() +
                    " was afflicted with poison and bleeding!");
        //50% of the time, the defender will also bleed for the attacker's weapon damage for 5 turns.
    }
}
