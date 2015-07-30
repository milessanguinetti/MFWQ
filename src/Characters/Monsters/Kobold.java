package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Inventory.Item;
import Characters.Monster;
import Characters.Properties.Neutral;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Skills.Monster.koboldStrike;
import Characters.Skills.Monster.poopOutaBaby;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class Kobold extends Monster{
    public Kobold(){
        super("Kobold", 50, 50, 5, 4, 2, 5, 1, 1, 1);
        charProperty = new Neutral();
        buildSkills(); //build the monster's skills.
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
        skillProbabilities[0] = 60;
        monsterSkills[1] = new koboldStrike();
        skillProbabilities[1] = 30;
        monsterSkills[2] = new poopOutaBaby();
        skillProbabilities[2] = 10;
    }
}
