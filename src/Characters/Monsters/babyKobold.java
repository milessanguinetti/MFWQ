package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Inventory.Item;
import Characters.Inventory.Weapons.Nodachi;
import Characters.Inventory.Weapons.generic2hEdged;
import Characters.Monster;
import Characters.Properties.Neutral;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Skills.Monster.koboldStrike;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/27/15.
 */
public class babyKobold extends Monster{
    public babyKobold(){
        super("Baby Kobold", 20, 30, 3, 2, 12, 3, 1, 1, 0);
        charProperty = new Neutral();
        buildSkills(); //build the monster's skills.
    }

    @Override
    public Item Loot() {
        Random Rand = new Random();
        //if(Rand.nextInt(5) == 0) {
        if(true){
            //return new Potion();
            return new generic2hEdged(5);
        }
        return null;
    }

    @Override
    public int getExp() {
        return 250;
    }

    @Override
    public int getJexp() {
        return 250;
    }

    @Override
    public void buildSkills() {
        monsterSkills[0] = new genericMonsterAttack();
        skillProbabilities[0] = 80;
        monsterSkills[1] = new koboldStrike();
        skillProbabilities[1] = 20;
    }
}
