package Characters.Monsters;

import Characters.Monster;
import Characters.Properties.Neutral;
import Characters.Skills.Monster.selfDestruct;

/**
 * Created by Miles Sanguinetti on 5/11/15.
 */
//gene splicer minion; a sphere that self-destructs
public class sphaeraeEversioSui extends Monster{
    public sphaeraeEversioSui(){
        super("Caper Emissarius", 50, 20, 1, 1, 10, 1, 1, 1, 0);
        charProperty = new Neutral();
        buildSkills(); //build the monster's skills.
    }

    public sphaeraeEversioSui(int hp){
        super("Caper Emissarius", hp, 20, 1, 1, 10, 1, 1, 1, 0);
        charProperty = new Neutral();
        buildSkills(); //build the monster's skills.
    }

    @Override
    public void Loot() {
        //this is a gene-splicer minion and does not drop loot
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
        monsterSkills[0] = new selfDestruct();
        skillProbabilities[0] = 100;
    }
}
