package Characters.Skills.firstClass;

import Characters.Inventory.Item;
import Characters.Monster;
import Characters.Skills.Skill;
import Characters.gameCharacter;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles on 5/1/15.
 */
public class rogueSteal extends Skill {
    public rogueSteal(){
        super("Steal",
                "Picks the target's pockets, stealing gold or loot.", 5);
    }

    @Override
    public void spLoss(gameCharacter Caster) {
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
        return toCheck.getSP() >= 5;
    }

    @Override //70% chance to steal gold, 10% for loot, 20% to fail
    public void takeAction(gameCharacter Caster, gameCharacter Defender) {
        Random Rand = new Random();
        Caster.printName(); //preemptively print name for other statements
        int Roll = Rand.nextInt(10);
        if (Roll < 5) { //steal gold case
            int Stolen = ((Monster)Defender).getExp();
            Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() + " stole " + Stolen + " gold!");
            Game.Player.addCoins(Stolen);
        }
        else{ //steal item case
            Item temp = Defender.Loot();
            if(temp != null && !((Monster)Defender).hasBeenStolenFrom()) {
                Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() +
                        " stole a " + temp.returnKey() + "!");
                Game.Player.Insert(temp);
                ((Monster)Defender).setHasBeenStolenFrom(true);
            }
            else{
                Game.battle.getInterface().printLeftAtNextAvailable(Caster.getName() +
                        " didn't manage to steal anything.");
            }

        }
        //otherwise the skill fails to steal anything.
    }
}
