package Characters.Monsters;

import Characters.Monster;
import Characters.Properties.Fire;
import Characters.Properties.Organic;
import Characters.Properties.Water;
import Characters.Skills.Monster.genericElementalAoE;
import Characters.Skills.Monster.genericElementalAttack;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Stats;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
//homunculus minion. Is of a random property determined at creation--fire, water or organic.
public class basalHomunculus extends Monster{
    public basalHomunculus(Stats toCopy){
        super("Basal Homunculus", toCopy);
        Random Rand = new Random();
        int Roll = Rand.nextInt(3); //roll between 0-2 inclusive
        if(Roll == 0) {
            charProperty = new Fire();
            monsterSkills[1] = new genericElementalAttack("Fire");
            monsterSkills[2] = new genericElementalAoE("Fire");
        }
        else if(Roll == 1){
            charProperty = new Organic();
            monsterSkills[1] = new genericElementalAttack("Organic");
            monsterSkills[2] = new genericElementalAoE("Organic");
        }
        else{
            charProperty = new Water();
            monsterSkills[1] = new genericElementalAttack("Water");
            monsterSkills[2] = new genericElementalAoE("Water");
        }
        buildSkills(); //build the monster's skills.
        scaleDifficulty(.7f);
    }

    @Override
    public void Loot() {
        //this is a homunculus minion and does not drop loot
    }

    @Override
    public int getExp() {
        return 0;
    }

    @Override
    public int getJexp() {
        return 0;
    }

    @Override
    public void buildSkills() {
        monsterSkills[0] = new genericMonsterAttack();
        skillProbabilities[0] = 60; //60% chance of a generic attack
        skillProbabilities[1] = 25; //25% chance of a stronger elemental attack
        skillProbabilities[2] = 15; //15% chance of an aoe attack
    }

}
