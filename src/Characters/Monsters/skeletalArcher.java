package Characters.Monsters;

import Characters.Inventory.Consumables.Potion;
import Characters.Monster;
import Characters.Properties.Undead;
import Characters.Skills.Monster.genericMonsterAttack;
import Characters.Skills.firstClass.archerArcingArrow;
import Characters.Skills.firstClass.archerArrowStorm;
import Characters.Stats;
import Profile.Game;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
public class skeletalArcher extends Monster {
    public skeletalArcher(){
        super("Skeletal Archer", 80, 80, 2, 12, 6, 5, 3, 3, 2);
        charProperty = new Undead();
        buildSkills(); //build the monster's skills.
    }

    //constructor for player-controlled, lich-spawned skeletons
    public skeletalArcher(Stats toCopy, int casterInt){
        super("Skeletal Archer", toCopy);
        scaleDifficulty(.5f);
        Dex = Math.round(casterInt * .75f);
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
        monsterSkills[1] = new archerArrowStorm();
        skillProbabilities[1] = 30;
        monsterSkills[2] = new archerArcingArrow();
        skillProbabilities[2] = 20;
    }
}
