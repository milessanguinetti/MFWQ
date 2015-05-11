package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Monster;
import Characters.Properties.Undead;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Skills.firstClass.soldierRend;
import Characters.Skills.firstClass.soldierSeverHamstring;
import Characters.Skills.firstClass.soldierWideSlash;
import Characters.Stats;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class skeletalSoldier extends Monster{
    public skeletalSoldier(){
        super("Skeletal Soldier", 120, 80, 10, 4, 5, 5, 3, 2, 4);
        charProperty = new Undead();
        buildSkills(); //build the monster's skills.
    }

    //constructor for player-controlled, lich-spawned skeletons
    public skeletalSoldier(Stats toCopy, int casterInt){
        super("Skeletal Soldier", toCopy);
        scaleDifficulty(.5f);
        Str = Math.round(casterInt * .75f);
        charProperty = new Undead();
        buildSkills();
    }

    @Override
    public void Loot() {
        Random Rand = new Random();
        if(Rand.nextInt(4) == 0) {
            System.out.println("Skeletal Soldier dropped a potion!");
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
        monsterSkills[1] = new soldierRend();
        skillProbabilities[1] = 30;
        monsterSkills[2] = new soldierSeverHamstring();
        skillProbabilities[2] = 20;
    }
}
