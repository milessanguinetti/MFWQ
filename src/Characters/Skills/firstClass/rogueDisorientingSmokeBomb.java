package Characters.Skills.firstClass;

import Characters.Skills.Skill;
import Characters.Status.delayedSwitchSides;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/8/15.
 */
public class rogueDisorientingSmokeBomb extends Skill{
    public rogueDisorientingSmokeBomb(){
        super("Disorienting Smoke Bomb",
                "Bombards several enemies with a smoke bomb," +
                        " potentially confusing them into attacking their allies for a turn.", 40);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
        Caster.subtractSP(40);
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
        return 1;
    }

    @Override
    public boolean canUse(gameCharacter toCheck) {
        return toCheck.getSP() >= 40;
    }

    @Override //half the time, confuses the target into joining your side for a turn (plus the turn this was cast on)
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        if (Defender.getHPCap() > Caster.getHPCap()){
            Defender.printName();
            System.out.println(" resisted the attack.");
        return; //can't switch the sides of targets with more HP than the caster.
    }
        Random Rand = new Random();
        if(Rand.nextInt(2) == 0){ //the skill fails 50% of the time.
            Defender.printName();
            System.out.println(" was confused into attacking their allies!");
            Game.Player.getCurrentBattle().nullCombatant(Defender); //remove defender from the battlefield
            Defender.addStatus(new delayedSwitchSides(2, false)); //they rejoin their allies after the next turn
            Game.Player.getCurrentBattle().addMinion(true, Defender);
        }
    }
}
