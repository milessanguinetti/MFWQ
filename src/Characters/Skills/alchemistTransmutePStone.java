package Characters.Skills;

import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/4/15.
 */
public class alchemistTransmutePStone extends Skill{
    public alchemistTransmutePStone(){
        super("Transmute: Philosopher's Stone",
                "A weak attack that transmutes the victim's essence into a philosopher's stone if they die.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
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
        if(toCheck.getSP() >= 50)
            return false; //SP requirements
        return true;
    }

    @Override //deal 50% damage calculated by dex and right weapon damage.
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if(Defender.takeDamage(Math.round(.5f*(Caster.getWeaponDamage(true)
                + Caster.getTempDex())), Caster.getWeaponProperty(true)) == 0) {
            Game.Player.addStones(1);
            Caster.printName();
            System.out.print(" transmuted ");
            Defender.printName();
            System.out.println("'s essence into a philosopher's stone.");
        }
    }
}
