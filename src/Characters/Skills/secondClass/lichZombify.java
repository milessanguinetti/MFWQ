package Characters.Skills.secondClass;

import Characters.Properties.Undead;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

/**
 * Created by Miles Sanguinetti on 5/14/15.
 */
public class lichZombify extends Skill{
    public lichZombify(){
        super("Zombify",
                "Transforms a dead foe into a mindless zombie, forcing them to fight for the caster.", 50);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(50);
    }

    @Override
    public boolean notUsableOnDead(){
        return false; //not usable on dead characters
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
        return toCheck.getSP() >= 50;
    }

    @Override //half the time, confuses the target into joining your side for a turn (plus the turn this was cast on)
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Defender.printName();
        System.out.println(" was zombified!");
        Defender.setTempProperty(new Undead());
        Defender.takeAbsoluteDamage(Defender.getHPCap() / -2);
        //heal to 50% health
        Game.battle.nullCombatant(Defender); //remove defender from the battlefield
        Game.battle.addMinion(true, Defender);
    }
}
