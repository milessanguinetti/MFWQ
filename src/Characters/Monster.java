package Characters;

import Characters.Skills.Skill;
import Characters.Stats;
import Characters.gameCharacter;

import java.util.Random;

/**
 * Created by Miles Sanguinetti on 4/6/15.
 */
public abstract class Monster extends gameCharacter{
    protected Skill Combo; //skill reference to let us chain skills with the AI
    protected Skill [] monsterSkills = new Skill[6]; //skill array
    protected int [] skillProbabilities = new int[6]; //chance of each skill

    //default constructor
    public Monster(){}

    //constructor with name
    public Monster(String toName){
        Name = toName;
    }

    //constructor with name and stats
    public Monster(String toName, Stats toAdd){
        super(toName, toAdd);
    }

    //sets the monster's combo to the passed skill index
    public void setCombo(int toSet){
        if(toSet < 0 || toSet > 5)
            return; //error
        Combo = monsterSkills[toSet];
    }

    //basically the AI for choosing a skill to use.
    public Skill skillAI(){
        if(Combo != null)
            return Combo; //return combo if we're in the midst of a combo
        //int i = Math.random();
        Random Rand = new Random();
        int roll = Rand.nextInt(100) + 1;
        for(int j = 0; j < 6; ++j){ //for each skill
            if(roll <= skillProbabilities[j]) { //if the roll is less than the probability...
                if(!monsterSkills[j].canUse(this))
                    return skillAI(); //reroll if we don't have mana for this skill or something
                return monsterSkills[j]; //otherwise return the corresponding skill
            }
            else
                roll -= skillProbabilities[j]; //otherwise decrement the roll and continue looping
        }
        return monsterSkills[0]; //return index 0 as default to avoid bugs
    }

    //build the monster's skillset.
    public abstract void buildSkills();

    @Override //apply the monster's passive buff
    public void applyAutoBuffs() {
        tempProperty = charProperty;
        if(currentPassive != null)
            currentPassive.passiveEffect(this);
    }
}
