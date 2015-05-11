package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Monster;
import Characters.Properties.Undead;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Skills.firstClass.enchanterInvokeFlame;
import Characters.Skills.firstClass.enchanterInvokeStone;
import Characters.Stats;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class skeletalMagus extends Monster{
    public skeletalMagus(){
        super("Skeletal Magus", 80, 80, 2, 4, 6, 5, 3, 12, 2);
        charProperty = new Undead();
        buildSkills(); //build the monster's skills.
    }

    //constructor for player-controlled, lich-spawned skeletons
    public skeletalMagus(Stats toCopy, int casterInt){
        super("Skeletal Magus", toCopy);
        scaleDifficulty(.5f);
        Int = Math.round(casterInt * .75f);
        charProperty = new Undead();
        buildSkills();
    }

    @Override
    public void Loot() {
        Random Rand = new Random();
        if(Rand.nextInt(4) == 0) {
            System.out.println("Skeletal Magus dropped a potion!");
            Game.Player.Insert(new Potion());
        }
    }

    @Override
    public int getExp() {
        return 1000;
    }

    @Override
    public int getJexp() {
        return 800;
    }

    @Override
    public void buildSkills() {
        monsterSkills[0] = new genericMonsterAttack();
        skillProbabilities[0] = 50;
        monsterSkills[1] = new enchanterInvokeFlame();
        skillProbabilities[1] = 30;
        monsterSkills[2] = new enchanterInvokeStone();
        skillProbabilities[2] = 20;
    }
}
