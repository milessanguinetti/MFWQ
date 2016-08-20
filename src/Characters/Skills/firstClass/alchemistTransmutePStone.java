package Characters.Skills.firstClass;

import Characters.Boss;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
public class alchemistTransmutePStone extends Skill {
    public alchemistTransmutePStone(){
        super("Transmute: Philosopher's Stone",
                "A weak attack that transmutes a boss monster's essence into a philosopher's stone if they die.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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
        if(toCheck.getSP() <= 50)
            return false; //SP requirements
        return true;
    }

    @Override //deal 50% damage calculated by dex and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.takeDamage(Math.round(.5f*(Caster.getWeaponDamage(true)
                + Caster.getTempDex())), Caster.getWeaponProperty(true)) == 0 &&
                Defender instanceof Boss) {
            Game.Player.addStones(1);
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " transmuted " + Defender.getName() +
            "'s essence into a philosopher's stone!");
        }
    }
}
