package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Inventory.Item;
import Characters.Monster;
import Characters.Properties.Undead;
import Characters.Skills.Monster.genericElementalAttack;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Stats;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class Zombie extends Monster {
    public Zombie(){
        super("Zombie", 100, 80, 12, 6, 2, 5, 1, 1, 2);
        charProperty = new Undead();
        buildSkills(); //build the monster's skills.
    }

    //constructor for a player-created zombie, copies the player's stats, but then halves them.
    public Zombie(Stats toCopy){
        super("Zombie", toCopy);
        charProperty = new Undead();
        buildSkills();
        scaleDifficulty(.5f);
    }

    @Override
    public Item Loot() {
        Random Rand = new Random();
        if(Rand.nextInt(5) == 0) {
            return new Potion();
        }
        return null;
    }

    @Override
    public int getExp() {
        return 500;
    }

    @Override
    public int getJexp() {
        return 500;
    }

    @Override
    public void buildSkills() {
        monsterSkills[0] = new genericMonsterAttack();
        skillProbabilities[0] = 70;
        monsterSkills[1] = new genericElementalAttack("Undead");
        skillProbabilities[1] = 30;
    }
}
